package com.seller.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.seller.dto.OrderDto;
import com.seller.util.pagehelper.Pagination;

public interface OrderService {

    /*创建订单*/
    OrderDto create(OrderDto orderDto);

    /*查询单个订单*/
    OrderDto findOne(String orderId);

    /*查询订单列表*/
    PageInfo<OrderDto> findList(String buyerOpenId, Pagination condition);

    /*查询订单列表*/
    PageInfo<OrderDto> findList(Pagination condition);

    /*取消订单*/
    OrderDto cancel(OrderDto orderDto);

    /*完结订单*/
    OrderDto finish(OrderDto orderDto);

    /*支付订单*/
    OrderDto paid(OrderDto orderDto);
}
