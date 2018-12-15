package com.seller.controller;

import com.seller.dao.model.SellerInfo;
import com.seller.dto.LoginDto;
import com.seller.exception.SellException;
import com.seller.service.SellerInfoService;
import com.seller.util.constant.CookieConstant;
import com.seller.util.constant.RedisConstant;
import com.seller.util.cookie.CookieUtil;
import com.seller.util.enums.ExceptionEnum;
import com.sun.org.apache.xpath.internal.operations.Mod;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-08-02 15:25
 **/
@Controller
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerInfoService sellerInfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;

//    @ApiOperation("【后台管理】商家登录")
//    @PostMapping("/login")
//    public ModelAndView select(@RequestParam("openid") String openid,
//                               @RequestParam("password") String password,
//                               HttpServletResponse response, Map<String, Object> map){
//        SellerInfo sellerInfo = sellerInfoService.findByOpenid(openid);
//        if(sellerInfo == null || !sellerInfo.getPassword().equals(password)){
//            map.put("msg",ExceptionEnum.LOGIN_FAIL.getMessage());
//            map.put("url","/seller/login");
//            return new ModelAndView("/common/error",map);
//        }
//        //设置token到redis
//        String token = UUID.randomUUID().toString();
//        Integer expire = RedisConstant.EXPIRE;
//        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire,TimeUnit.SECONDS);
//
//        //设置cookie
//        CookieUtil.set(response,CookieConstant.TOKEN,token,CookieConstant.EXPIRE);
//
//        return new ModelAndView("redirect:/seller/index");
//    }

    @ApiOperation("【后台管理】商家登录")
    @PostMapping("/login")
    @ResponseBody
    public String select(@RequestBody LoginDto loginDto,
                               HttpServletResponse response, Map<String, Object> map){
//        SellerInfo sellerInfo = sellerInfoService.findByOpenid(openid);
//        if(sellerInfo == null || !sellerInfo.getPassword().equals(password)){
//            map.put("msg",ExceptionEnum.LOGIN_FAIL.getMessage());
//            map.put("url","/seller/login");
//            return "失败";
//        }
//        //设置token到redis
//        String token = UUID.randomUUID().toString();
//        Integer expire = RedisConstant.EXPIRE;
//        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire,TimeUnit.SECONDS);
//
//        //设置cookie
//        CookieUtil.set(response,CookieConstant.TOKEN,token,CookieConstant.EXPIRE);

        return "success";
    }


//    @GetMapping("index")
//    public ModelAndView index(){
//        return new ModelAndView("index");
//    }

    @ApiOperation("【后台管理】商家登出")
    @GetMapping("logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String,Object> map){
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie !=null){
            //清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ExceptionEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/seller/order/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/index")
    @ResponseBody
    public String loginPage(){
        return "success";
    }


}
