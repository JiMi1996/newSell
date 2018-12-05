package com.seller.service.serviceImpl;

import com.seller.dao.OrderDetailMapper;
import com.seller.dao.OrderMasterMapper;
import com.seller.dao.model.OrderDetail;
import com.seller.dao.model.OrderDetailExample;
import com.seller.dao.model.OrderMaster;
import com.seller.service.OrderDetailService;
import com.seller.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: newsell
 * @description:
 * @author: fbl
 * @create: 2018-07-18 23:23
 **/
@Service
public class OrderDetailImpl implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Integer insert(OrderDetail orderDetail) {
        return orderDetailMapper.insert(orderDetail);
    }

    @Override
    public List<OrderDetail> findByOrderId(String id) {
        OrderDetailExample example = new OrderDetailExample();
        OrderDetailExample.Criteria criteria = example.createCriteria().andOrderIdEqualTo(id);
        return orderDetailMapper.selectByExample(example);
    }
}
