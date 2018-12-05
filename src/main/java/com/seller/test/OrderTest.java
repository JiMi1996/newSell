package com.seller.test;

import com.alibaba.fastjson.JSONObject;
import com.seller.dao.model.OrderDetail;
import com.seller.dto.OrderDto;
import com.seller.service.OrderService;
import com.seller.util.enums.OrderStatusEnum;
import com.seller.util.enums.PayStatusEnum;
import com.seller.util.pagehelper.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-07-19 22:45
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderTest {

    private final static String OPPENID = "110110";

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrder() throws Exception{
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName("李晶敏");
        orderDto.setBuyerAddress("湖北");
        orderDto.setBuyerPhone("15272540014");
        orderDto.setBuyerOpenid(OPPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail1 = new OrderDetail();
        OrderDetail orderDetail2 = new OrderDetail();

        orderDetail1.setProductId("102");
        orderDetail2.setProductId("103");
        orderDetail2.setProductQuantity(2);
        orderDetail1.setProductQuantity(5);

        orderDetailList.add(orderDetail1);
        orderDetailList.add(orderDetail2);

        orderDto.setOrderDetailList(orderDetailList);

        OrderDto result = orderService.create(orderDto);

        log.info("【创建订单】result = {}",result);
    }

    @Test
    public void findOne() throws Exception{
        log.info("【查询单个订单】result = {}",orderService.findOne("1532012469623925715"));
    }
    @Test
    public void findList() throws Exception{
        Pagination condition = new Pagination(5,1);
        log.info("【查询订单列表】result = {}",orderService.findList("110110",condition));
    }

    @Test
    public void cancel() throws Exception{
        OrderDto orderDto = orderService.findOne("1532016348485916642");
        OrderDto result = orderService.cancel(orderDto);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() throws Exception{
        OrderDto orderDto = orderService.findOne("1532016348485916642");
        OrderDto result = orderService.finish(orderDto);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() throws Exception{
        OrderDto orderDto = orderService.findOne("1532016348485916642");
        OrderDto result = orderService.paid(orderDto);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void reserve() throws Exception {
        String str = "[" +
                "{detailId:'123456',orderId:'101',productId:'101',productName:'皮蛋瘦肉粥',productPrice:'2.20',productQuantity:'3',productIcon:'img/01.jpg'}," +
                "{detailId:'123456',orderId:'101',productId:'102',productName:'农家小炒肉',productPrice:'2.20',productQuantity:'3',productIcon:'img/02.jpg'}," +
                "{detailId:'123456',orderId:'101',productId:'103',productName:'千张肉丝',productPrice:'2.20',productQuantity:'3',productIcon:'img/03.jpg'}]";
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = JSONObject.parseArray(str, OrderDetail.class);
        } catch (Exception e) {
            log.error("【数据转化】json转化失败，orderForm={}", str);
            e.printStackTrace();
        }
        log.info("orderDetailList={}",orderDetailList);
    }

}
