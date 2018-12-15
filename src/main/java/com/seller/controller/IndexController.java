package com.seller.controller;/**
 * created by sheting on 2018/12/10
 */

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: sell
 * @description:
 * @author: ljm
 * @create: 2018-12-10 16:38
 **/
@Controller
public class IndexController {

//    @ApiOperation("【后台管理】商家首页")
//    @GetMapping("/index")
//    public ModelAndView loginPage(){
//        return new ModelAndView("login");
//    }


    @GetMapping("/index")
    @ResponseBody
    public String loginPage(){
        return "success";
    }
}
