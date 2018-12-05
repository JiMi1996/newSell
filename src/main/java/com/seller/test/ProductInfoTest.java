package com.seller.test;

import com.github.pagehelper.PageInfo;
import com.seller.dao.model.ProductCategory;
import com.seller.dao.model.ProductInfo;
import com.seller.service.ProductCategoryService;
import com.seller.service.ProductInfoService;
import com.seller.util.pagehelper.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: sell
 * @description:
 * @author: Mr.Tang
 * @create: 2018-07-02 12:16
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void select(){

        productInfoService.selectByPrimaryKey("101");
    }

    @Test
    public void selectList(){
        Pagination pagination = new Pagination(2,1);
        PageInfo<ProductInfo> list = productInfoService.findAll(pagination);

    }

    @Test
    public void selectUpList(){
        List<ProductInfo> list = productInfoService.findUpAll();
        for(ProductInfo productInfo : list){
            log.info(productInfo.toString());
        }
    }

    @Test
    public void insert(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("102");
        productInfo.setProductName("农家小炒肉");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("营养有健康");
        productInfo.setCategoryType(new Integer(4));
        productInfo.setProductStatus(new Byte("0"));
        productInfo.setProductIcon("image/02.jpg");
        productInfoService.insert(productInfo);
    }

    @Test
    public void update(){
        ProductInfo productInfo = productInfoService.selectByPrimaryKey("101");
        productInfo.setProductName("千张肉丝");
        productInfo.setCategoryType(new Integer(7));
        productInfoService.updateByPrimaryKey(productInfo);
    }

    @Test
    public void delete(){
        productInfoService.deleteByPrimaryKey("101");
    }
}
