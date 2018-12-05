package com.seller.service;

import com.seller.dto.OrderDto;

public interface BuyerService {

    //查询一个订单
    OrderDto findOrderOne(String orderId,String openid);

    //取消订单
    OrderDto cancelOrderOne(String orderId,String openid);

}
