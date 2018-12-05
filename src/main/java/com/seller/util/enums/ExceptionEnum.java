package com.seller.util.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    PARAM_ERROR(1,"参数不正确"),
    CATEGORY_HAS_EXIST(8,"商品类目已存在"),
    CATEGORY_NOT_EXIST(9,"商品类目不存在"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDER_UPDATE_ERROR(15,"订单状态更新失败"),
    ORDER_DETAIL_EMPTY(16,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(17,"支付状态不正确"),
    ORDER_PAY_UPDATE_ERROR(17,"支付状态更新失败"),
    CAR_EMPTY(18,"购物车不能为空"),
    ORDER_OWNER_ERROR(19,"该订单不属于当前用户"),
    PRODUCT_STATUS_ERROR(20,"商品状态错误"),
    SELLER_OPENID_ERROR(21,"用户openid信息错误"),
    LOGIN_FAIL(22,"用户登陆失败"),
    LOGOUT_SUCCESS(23,"用户登出成功"),
    ;

    private Integer code;
    private String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
