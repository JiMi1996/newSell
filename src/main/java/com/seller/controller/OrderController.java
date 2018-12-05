package com.seller.controller;

import com.seller.dao.model.OrderDetail;
import com.seller.dto.OrderDto;
import com.seller.exception.SellException;
import com.seller.service.BuyerService;
import com.seller.service.OrderService;
import com.seller.util.VO.Result;
import com.seller.util.converter.OrderForm2OrderDto;
import com.seller.util.enums.ExceptionEnum;
import com.seller.util.enums.ResultEnum;
import com.seller.util.form.OrderForm;
import com.seller.util.pagehelper.Pagination;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-07-19 23:55
 **/
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final static String OPPENID = "110110";

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @PostMapping("/createOrder")
    @ApiOperation(value = "创建订单")
    public Result createOrder(@Valid OrderForm orderForm,
                              BindingResult bindingResult){
        log.info("orderForm ={}",orderForm);
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm ",orderForm);
            throw new SellException(ExceptionEnum.PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDto orderDto = OrderForm2OrderDto.convert(orderForm);

        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ExceptionEnum.CAR_EMPTY);
        }
        OrderDto createResult = orderService.create(orderDto);

        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());

        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setSuccess(ResultEnum.SUCCESS.getSuccess());
        result.setData(map);
        result.setMsg("创建订单成功");
        return result;
    }

    @ApiOperation(value = "OpenId查找订单列表")
    @GetMapping("/findListByOpenid")
    public Result findListByOpenid(@RequestParam("openid") String openid,
                           @RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) throws Exception{
        Pagination condition = new Pagination(pageSize,currentPage);
        Result result = new Result();
        System.out.println(openid);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setSuccess(ResultEnum.SUCCESS.getSuccess());
        result.setData(orderService.findList(openid,condition));
        result.setMsg("用户订单列表");
        return result;
    }

    @ApiOperation(value = "查询全部订单列表")
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) throws Exception{
        Pagination condition = new Pagination(pageSize,currentPage);
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setSuccess(ResultEnum.SUCCESS.getSuccess());
        result.setData(orderService.findList(condition));
        result.setMsg("全部订单列表");
        return result;
    }


    @ApiOperation(value = "查询单个订单")
    @GetMapping("/findOne")
    public Result findOne(@RequestParam("orderId") String orderId,
                          @RequestParam("openid") String openid) throws Exception{
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setSuccess(ResultEnum.SUCCESS.getSuccess());
        result.setData(buyerService.findOrderOne(orderId,openid));
        result.setMsg("用户单个订单");
        return result;
    }

    @ApiOperation(value = "取消订单")
    @PostMapping("/cancel")
    public Result cancel(@RequestParam("openid") String openid,
                         @RequestParam("orderId") String orderId){
        buyerService.cancelOrderOne(orderId,openid);
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setSuccess(ResultEnum.SUCCESS.getSuccess());
        result.setMsg("取消订单成功");
        return result;
    }

}
