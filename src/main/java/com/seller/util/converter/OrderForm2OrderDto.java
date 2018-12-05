package com.seller.util.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seller.dao.model.OrderDetail;
import com.seller.dto.OrderDto;
import com.seller.util.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-07-24 11:00
 **/
@Slf4j
public class OrderForm2OrderDto {

    public static OrderDto convert(OrderForm orderForm){

        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = JSONObject.parseArray(orderForm.getItems(), OrderDetail.class);
        }catch (Exception e){
            log.error("【数据转化】json转化失败，orderForm={}",orderForm.getItems());
            e.printStackTrace();
        }
         orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

}
