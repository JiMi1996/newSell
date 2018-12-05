package com.seller.service.serviceImpl;

import com.seller.dao.SellerInfoMapper;
import com.seller.dao.model.SellerInfo;
import com.seller.dao.model.SellerInfoExample;
import com.seller.exception.SellException;
import com.seller.service.SellerInfoService;
import com.seller.util.enums.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-08-02 14:53
 **/
@Service
public class SellerInfoImpl implements SellerInfoService {

    @Autowired
    private SellerInfoMapper sellerInfoMapper;

    @Override
    public int insert(SellerInfo sellerInfo) {
        return sellerInfoMapper.insert(sellerInfo);
    }

    @Override
    public SellerInfo findByOpenid(String id) {
        SellerInfoExample example = new SellerInfoExample();
        SellerInfoExample.Criteria criteria = example.createCriteria().andOpenidEqualTo(id);
        List<SellerInfo> sellerInfoList = sellerInfoMapper.selectByExample(example);
        if(sellerInfoList.size() != 1){
            throw new SellException(ExceptionEnum.SELLER_OPENID_ERROR);
        }
        return sellerInfoList.get(0);
    }
}
