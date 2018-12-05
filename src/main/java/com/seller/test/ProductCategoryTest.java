package com.seller.test;

import com.seller.dao.ProductCategoryMapper;
import com.seller.dao.model.ProductCategory;
import com.seller.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: sell
 * @description:
 * @author: Mr.Tang
 * @create: 2018-07-02 12:16
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void select(){
        productCategoryService.selectByPrimaryKey(new Integer(1));

    }

    @Test
    public void insert(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("美味煲仔");
        productCategory.setCategoryType(new Integer(5));
        productCategoryService.insert(productCategory);
    }

    @Test
    public void updateByEntity(){
        ProductCategory productCategory = productCategoryService.selectByPrimaryKey(new Integer(7));
        productCategory.setCategoryName("美味麻辣");
        productCategory.setCategoryType(new Integer(8));
        productCategoryService.updateByPrimaryKey(productCategory);
    }

    @Test
    public void delete(){
        productCategoryService.deleteByPrimaryKey(new Integer(1));
    }
}
