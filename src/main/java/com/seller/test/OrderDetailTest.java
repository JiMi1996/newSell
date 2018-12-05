package com.seller.test;

import com.seller.dao.model.OrderDetail;
import com.seller.dao.model.OrderMaster;
import com.seller.service.OrderDetailService;
import com.seller.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: newsell
 * @description:
 * @author: fbl
 * @create: 2018-07-18 23:17
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderDetailTest {

    @Autowired
    private OrderDetailService orderDetailService;

    @Test
    public void insert(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123457");
        orderDetail.setOrderId("102");
        orderDetail.setProductIcon("img/01.jpg");
        orderDetail.setProductId("102");
        orderDetail.setProductName("农家小炒肉");
        orderDetail.setProductPrice(new BigDecimal(2.2));
        orderDetail.setProductQuantity(3);
        orderDetailService.insert(orderDetail);
    }

    @Test
    public void findByOrderId() throws  Exception{
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId("101");
        Assert.assertNotEquals(0,orderDetails.size());

    }

}
