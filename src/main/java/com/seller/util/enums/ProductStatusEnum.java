package com.seller.util.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements EnumCode{
    UP(new Byte("0"), "在架"),
    DOWN(new Byte("1"), "下架");

    private Byte code;
    private String message;


    ProductStatusEnum(Byte code,String message){
        this.code = code;
        this.message = message;
    }
}
