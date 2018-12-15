package com.seller.service.serviceImpl;

import com.seller.dao.SellerInfoMapper;
import com.seller.dao.model.SellerInfo;
import com.seller.dao.model.SellerInfoExample;
import com.seller.exception.SellException;
import com.seller.service.SellerInfoService;
import com.seller.util.VO.Result;
import com.seller.util.constant.CookieConstant;
import com.seller.util.constant.RedisConstant;
import com.seller.util.cookie.CookieUtil;
import com.seller.util.enums.ExceptionEnum;
import com.seller.util.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate redisTemplate;

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

    @Override
    public Result login(String openid, String password, HttpServletResponse response) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<>();
        SellerInfo sellerInfo= findByOpenid(openid);
        if(sellerInfo == null || !sellerInfo.getPassword().equals(password)){
            map.put("msg",ExceptionEnum.LOGIN_FAIL.getMessage());
            map.put("url","/seller/login");
            result.setCode(ResultEnum.FAILD.getCode());
            result.setSuccess(false);
            result.setData(map);
            return result;
        }
        //设置token到redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire,TimeUnit.SECONDS);

        //设置cookie
        CookieUtil.set(response,CookieConstant.TOKEN,token,CookieConstant.EXPIRE);
        map.put("token",token);
        map.put("sellInfo",sellerInfo);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setSuccess(true);
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(map);
        return result;
    }
}
