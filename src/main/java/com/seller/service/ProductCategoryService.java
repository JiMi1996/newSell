package com.seller.service;

import com.seller.dao.model.ProductCategory;

import java.util.List;


/**
 * @program: sell
 * @description:
 * @author: fbl
 * @create: 2018-07-02 15:22
 **/
public interface ProductCategoryService {

    ProductCategory selectByPrimaryKey(Integer id);

    Integer insert(ProductCategory productCategory);

    Integer updateByPrimaryKey(ProductCategory productCategory);

    Integer deleteByPrimaryKey(Integer id);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    List<ProductCategory> findAll();

}
