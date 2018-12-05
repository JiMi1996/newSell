package com.seller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.seller.dao.model.OrderDetail;
import com.seller.util.enums.EnumUtil;
import com.seller.util.enums.OrderStatusEnum;
import com.seller.util.enums.PayStatusEnum;
import com.seller.util.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: newsell
 * @description:
 * @author: fbl
 * @create: 2018-07-19 00:19
 **/
@Data
//为空不返回
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Byte orderStatus;

    private Byte payStatus;

    //将时间转化成Long类型
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;

    //防止Json转成对象
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getCode(orderStatus,OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getCode(payStatus,PayStatusEnum.class);
    }
}
