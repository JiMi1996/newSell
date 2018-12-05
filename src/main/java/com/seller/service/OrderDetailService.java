package com.seller.service;

import com.seller.dao.model.OrderDetail;
import com.seller.dao.model.OrderMaster;

import java.util.List;

public interface OrderDetailService {

    Integer insert(OrderDetail orderDetail);

    List<OrderDetail> findByOrderId(String id);
}
