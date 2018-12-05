package com.seller.service;

import com.seller.dao.model.SellerInfo;

public interface SellerInfoService {

    int insert(SellerInfo sellerInfo);

    SellerInfo findByOpenid(String id);
}
