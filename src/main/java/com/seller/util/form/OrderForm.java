package com.seller.util.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-07-24 10:50
 **/
@Data
public class OrderForm {

    @NotNull(message = "姓名必填")
    private String name;

    @NotNull(message = "手机号必填")
    private String phone;

    @NotNull(message = "地址必填")
    private String address;

    @NotNull(message = "微信openid必填")
    private String openid;

    @NotNull(message = "购物车不能为空")
    private String items;
}
