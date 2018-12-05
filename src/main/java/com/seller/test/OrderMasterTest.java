package com.seller.test;

import com.seller.dao.model.OrderMaster;
import com.seller.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @program: newsell
 * @description:
 * @author: fbl
 * @create: 2018-07-18 23:17
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderMasterTest {

    private final String OPENID = "110110";

    @Autowired
    private OrderMasterService orderMasterService;

    @Test
    public void insert(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("102");
        orderMaster.setBuyerName("阿福");
        orderMaster.setBuyerPhone("123456789");
        orderMaster.setBuyerAddress("武汉市");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.3));
        orderMasterService.insert(orderMaster);
    }

    public void findByBuyerOpenId() throws Exception{

    }
}
