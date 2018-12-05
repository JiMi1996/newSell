package com.seller.service.serviceImpl;

import com.seller.dao.OrderMasterMapper;
import com.seller.dao.model.OrderMaster;
import com.seller.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: newsell
 * @description:
 * @author: fbl
 * @create: 2018-07-18 23:23
 **/
@Service
public class OrderMasterImpl implements OrderMasterService {

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Override
    public Integer insert(OrderMaster orderMaster) {
        return orderMasterMapper.insert(orderMaster);
    }
}
