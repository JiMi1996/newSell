package com.seller.util.aspect;

import com.seller.exception.SellerAuthorizeException;
import com.seller.util.constant.CookieConstant;
import com.seller.util.constant.RedisConstant;
import com.seller.util.cookie.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-08-03 10:37
 **/
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.seller.controller.Sell*.*(..))" +
            "&&!execution(public * com.seller.controller.SellerController.*(..))")
    public void verify(){
    }

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie == null){
            log.warn("【登陆校验】cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        String tokenValue = redisTemplate.opsForValue()
                .get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("【登陆校验】Redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }

}
