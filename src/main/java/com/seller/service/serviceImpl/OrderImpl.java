package com.seller.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seller.dao.OrderDetailMapper;
import com.seller.dao.OrderMasterMapper;
import com.seller.dao.model.*;
import com.seller.dto.CartDto;
import com.seller.dto.OrderDto;
import com.seller.exception.SellException;
import com.seller.service.OrderService;
import com.seller.service.ProductInfoService;
import com.seller.util.converter.OrderMaster2OrderDtoConverter;
import com.seller.util.enums.ExceptionEnum;
import com.seller.util.enums.OrderStatusEnum;
import com.seller.util.enums.PayStatusEnum;
import com.seller.util.enums.ResultEnum;
import com.seller.util.key.KeyUtil;
import com.seller.util.pagehelper.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: newsell
 * @description:
 * @author: fbl
 * @create: 2018-07-19 00:45
 **/
@Service
@Slf4j
public class OrderImpl implements OrderService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderMasterMapper orderMasterMapper;
    @Autowired
    private ProductInfoService productInfoService;

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {

        String orderId = KeyUtil.qenUniqueKey();

        BigDecimal orderAmount =new BigDecimal(BigInteger.ZERO);
        //1.查询商品（数量，价格）
        for(OrderDetail orderDetail : orderDto.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.selectByPrimaryKey(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算单价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //3.订单详情入库
            orderDetail.setDetailId(KeyUtil.qenUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailMapper.insert(orderDetail);
        }
        //4.写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterMapper.insert(orderMaster);

        //5.扣库存

        List<CartDto> cartDtoList =orderDto.getOrderDetailList().stream()
                .map(e->new CartDto(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());

        productInfoService.decreaseStock(cartDtoList);

        return orderDto;
    }

    @Override
    public OrderDto findOne(String orderId) {
        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
        if(orderMaster == null) {
            throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
        }
        OrderDetailExample example = new OrderDetailExample();
        OrderDetailExample.Criteria criteria = example.createCriteria().andOrderIdEqualTo(orderId);
        List<OrderDetail> orderDetailList = orderDetailMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ExceptionEnum.PRODUCT_STOCK_ERROR);
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    @Override
    public PageInfo<OrderDto> findList(String buyerOpenId, Pagination condition) {
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        OrderMasterExample example = new OrderMasterExample();
        OrderMasterExample.Criteria criteria = example.createCriteria().andBuyerOpenidEqualTo(buyerOpenId);
        List<OrderMaster> orderMasters = orderMasterMapper.selectByExample(example);
        List<OrderDto> dtoList =  OrderMaster2OrderDtoConverter.convert(orderMasters);
        PageInfo orderDtoPageInfo = new PageInfo(orderMasters);
        orderDtoPageInfo.setList(dtoList);
        return orderDtoPageInfo;
    }

    @Override
    public PageInfo<OrderDto> findList(Pagination condition) {
        OrderDetailExample detailExample = new OrderDetailExample();
        List<OrderDetail> orderDetails = orderDetailMapper.selectByExample(detailExample);
        List<String> detailString = orderDetails.stream().map(e->String.valueOf(e.getOrderId())).collect(Collectors.toList());
        OrderMasterExample masterExample = new OrderMasterExample();
        OrderMasterExample.Criteria criteria = masterExample.createCriteria().andOrderIdIn(detailString);
        PageHelper.startPage(condition.getCurrentPage(),condition.getPageSize());
        List<OrderMaster> orderMasters = orderMasterMapper.selectByExample(masterExample);
        List<OrderDto> dtoList =  OrderMaster2OrderDtoConverter.convert(orderMasters);
        PageInfo orderDtoPageInfo = new PageInfo(orderMasters);
        orderDtoPageInfo.setList(dtoList);
        return orderDtoPageInfo;
    }

    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态

        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto,orderMaster);
        int success = orderMasterMapper.updateByPrimaryKey(orderMaster);
        if(success == 0){
            log.error("【取消订单】更新状态失败，orderMaster={}",orderMaster);
            throw new SellException(ExceptionEnum.ORDER_UPDATE_ERROR);
        }
        //返回库存
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情，orderDto={}",orderDto);
            throw new SellException(ExceptionEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDto> cartDtoList =orderDto.getOrderDetailList()
                .stream().map(e -> new CartDto(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDtoList);

        //如果已支付，需退款
        if(orderDto.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())){
            //TODO
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
//        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        int success = orderMasterMapper.updateByPrimaryKey(orderMaster);
        if(success == 0){
            log.error("【完结订单】更新状态失败，orderMaster={}",orderMaster);
            throw new SellException(ExceptionEnum.ORDER_UPDATE_ERROR);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】支付状态不正确，orderId={},payStatus={}",orderDto.getOrderId(),orderDto.getPayStatus());
            throw new SellException(ExceptionEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        int success = orderMasterMapper.updateByPrimaryKey(orderMaster);
        if(success == 0){
            log.error("【支付订单】更新状态失败，orderMaster={}",orderMaster);
            throw new SellException(ExceptionEnum.ORDER_PAY_UPDATE_ERROR);
        }
        return orderDto;
    }
}
