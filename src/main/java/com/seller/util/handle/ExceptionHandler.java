package com.seller.util.handle;

import com.seller.exception.SellException;
import com.seller.exception.SellerAuthorizeException;
import com.seller.util.VO.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-08-03 15:09
 **/
@ControllerAdvice
public class ExceptionHandler {

    //拦截登陆异常
    @org.springframework.web.bind.annotation.ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("redirect:/seller/index");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = SellException.class)
    @ResponseBody
    public Result handlerSellerException(SellException e){
        Result result = new Result();
        result.setCode(e.getCode());
        result.setMsg(e.getMessage());
        result.setSuccess(false);
        return result;
    }
}
