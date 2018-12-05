package com.seller.util.enums;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-07-31 10:18
 **/
public class EnumUtil{

    public static <T extends EnumCode>T getCode(Byte code,Class<T> enumClass){
        for(T each:enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
