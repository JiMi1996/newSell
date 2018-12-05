package com.seller.util.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements EnumCode{
    NEW(new Byte("0"),"新订单"),
    FINISHED(new Byte("1"),"已支付"),
    CANCEL(new Byte("2"),"取消"),
    ;
    private Byte code;
    private String message;

    OrderStatusEnum(Byte code,String message){
        this.code = code;
        this.message = message;
    }
}
