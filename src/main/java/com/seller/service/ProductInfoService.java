package com.seller.service;

import com.github.pagehelper.PageInfo;
import com.seller.dao.model.ProductInfo;
import com.seller.dto.CartDto;
import com.seller.util.pagehelper.Pagination;

import java.util.List;

/**
 * @program: sell
 * @description:
 * @author: fbl
 * @create: 2018-07-02 15:22
 **/
public interface ProductInfoService {

    PageInfo<ProductInfo> findAll(Pagination condition);

    List<ProductInfo> findUpAll();

    ProductInfo selectByPrimaryKey(String id);

    Integer insert(ProductInfo productInfo);

    Integer updateByPrimaryKey(ProductInfo productInfo);

    Integer deleteByPrimaryKey(String id);

    //加库存
    void increaseStock(List<CartDto> cartDtoList);
    //减库存
    void decreaseStock(List<CartDto> cartDtoList);

    //上架
    int onSale(String productId);

    //下架
    int offSale(String productId);
}
