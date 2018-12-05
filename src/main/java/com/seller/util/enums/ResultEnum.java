package com.seller.util.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(1,true,"请求成功"),
    FAILD(0,false,"请求失败"),
    ;

    private int code;
    private Boolean success;
    private String msg;

    ResultEnum(int code,Boolean success,String msg){
        this.code = code;
        this.success = success;
        this.msg = msg;
    }
}
