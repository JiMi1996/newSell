package com.seller.service;

import com.seller.dao.model.SellerInfo;
import com.seller.util.VO.Result;

import javax.servlet.http.HttpServletResponse;

public interface SellerInfoService {

    int insert(SellerInfo sellerInfo);

    SellerInfo findByOpenid(String id);

    Result login(String openid, String password, HttpServletResponse response);
}
