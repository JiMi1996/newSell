package com.seller.test;

import com.github.pagehelper.PageInfo;
import com.seller.dao.model.ProductInfo;
import com.seller.dao.model.SellerInfo;
import com.seller.service.ProductInfoService;
import com.seller.service.SellerInfoService;
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
public class SellerInfoText {

    private final static String openid = "110110";

    @Autowired
    private SellerInfoService sellerInfoService;

    @Test
    public void select(){
        sellerInfoService.findByOpenid(openid);
    }

    @Test
    public void insert(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId("2");
        sellerInfo.setOpenid("112112");
        sellerInfo.setUsername("mm");
        sellerInfo.setPassword("123456");
        sellerInfoService.insert(sellerInfo);
    }
}
