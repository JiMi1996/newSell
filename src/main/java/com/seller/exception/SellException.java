package com.seller.exception;

import com.seller.util.enums.ExceptionEnum;
import lombok.Getter;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-07-19 01:00
 **/
@Getter
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }
    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
