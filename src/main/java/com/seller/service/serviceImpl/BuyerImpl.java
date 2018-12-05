package com.seller.service.serviceImpl;

import com.seller.dto.OrderDto;
import com.seller.exception.SellException;
import com.seller.service.BuyerService;
import com.seller.service.OrderService;
import com.seller.util.enums.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-07-26 17:23
 **/
@Slf4j
@Service
public class BuyerImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDto findOrderOne(String orderId, String openid) {
        return checkOrderOwner(orderId,openid);
    }

    @Override
    public OrderDto cancelOrderOne(String orderId, String openid) {
        OrderDto orderDto = checkOrderOwner(orderId,openid);
        if(orderDto == null){
            log.error("【取消订单】查不到该订单，orderId={}",orderId);
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }
        return orderDto;
    }

    public OrderDto checkOrderOwner(String orderId, String openid) {
        OrderDto orderDto = orderService.findOne(orderId);
        if(orderDto == null){
            return null;
        }
        if(orderDto.getBuyerOpenid().equals(openid)){
            log.error("【查询订单】订单与openid不一致,orderId={} openid={}",orderId,openid);
            throw new SellException(ExceptionEnum.ORDER_OWNER_ERROR);
        }
        return orderDto;
    }
}
