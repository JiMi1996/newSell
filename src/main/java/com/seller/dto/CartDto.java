package com.seller.dto;

import lombok.Data;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-07-19 22:06
 **/
@Data
public class CartDto {
    //商品id
    private String productId;
    //商品数量
    private Integer productQuantity;

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}