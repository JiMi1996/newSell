package com.seller.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.seller.dto.OrderDto;
import com.seller.service.OrderService;
import com.seller.util.enums.ExceptionEnum;
import com.seller.util.enums.OrderStatusEnum;
import com.seller.util.enums.ResultEnum;
import com.seller.util.pagehelper.Pagination;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-07-31 09:17
 **/

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellOrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("【后台管理】订单列表")
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "currentPage",defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize",defaultValue = "10") Integer size,
                             Map<String, Object> map){
        Pagination pagination = new Pagination(size,page);
        PageInfo<OrderDto> orderDtoPage = orderService.findList(pagination);
//        System.out.println(orderDtoPage);
        map.put("orderDtoPage",orderDtoPage);
        return new ModelAndView("order/orderList",map);
    }

    @ApiOperation("【后台管理】取消订单")
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                             Map<String, Object> map){
        OrderDto orderDto = new OrderDto();
        try {
            orderDto = orderService.findOne(orderId);
            orderService.cancel(orderDto);
        }catch (Exception e){
            log.error("【卖家端取消订单】发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg",OrderStatusEnum.CANCEL.getMessage());
        map.put("url","/seller/order/list");
        return new ModelAndView("common/success",map);
    }

    @ApiOperation("【后台管理】订单详情")
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map){
        OrderDto orderDto = new OrderDto();
        try {
            orderDto = orderService.findOne(orderId);
        }catch (Exception e){
            log.error("【卖家端详情订单】发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderDto",orderDto);
        return new ModelAndView("order/detail",map);
    }

    @ApiOperation("【后台管理】订单完成")
    @GetMapping("finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        OrderDto orderDto = new OrderDto();
        try {
            orderDto = orderService.findOne(orderId);
            orderService.finish(orderDto);
        }catch (Exception e){
            log.error("【卖家端完结订单】发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",OrderStatusEnum.FINISHED.getMessage());
        map.put("url","/seller/order/list");
        return new ModelAndView("common/success");
    }
}
