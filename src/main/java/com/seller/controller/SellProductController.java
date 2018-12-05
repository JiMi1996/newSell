package com.seller.controller;

import com.github.pagehelper.PageInfo;
import com.seller.dao.model.ProductCategory;
import com.seller.dao.model.ProductInfo;
import com.seller.service.ProductCategoryService;
import com.seller.service.ProductInfoService;
import com.seller.util.enums.ProductStatusEnum;
import com.seller.util.pagehelper.Pagination;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-08-01 15:35
 **/
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation("【后台管理】商品列表")
    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "currentPage",defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize",defaultValue = "10") Integer size,
                             Map<String, Object> map){

        Pagination pagination = new Pagination(size,page);
        PageInfo<ProductInfo> productInfoPage = productInfoService.findAll(pagination);
        map.put("productInfoPage",productInfoPage);
        return new ModelAndView("product/list",map);
    }

    @ApiOperation("【后台管理】商品详情")
    @GetMapping("detail")
    public ModelAndView detail(@RequestParam(value = "productId", required = false)String productId,
                              Map<String, Object> map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productInfoService.selectByPrimaryKey(productId);
            map.put("productInfo",productInfo);
        }
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("product/detail",map);
    }

    @ApiOperation("【后台管理】商品更新")
    @PostMapping("save")
    //清除缓存
//    @CacheEvict(cacheNames = "product",key = "123")
    public ModelAndView save(@Valid ProductInfo productInfo,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        try {
            if(productInfo.getProductId() != ""){
                productInfoService.updateByPrimaryKey(productInfo);
            }else {
                productInfo.setProductId(String.valueOf(new Date().getTime()));
                productInfoService.insert(productInfo);
            }
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        map.put("msg","保存成功");
        map.put("url","/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @ApiOperation("【后台管理】商品上架")
    @GetMapping("onSale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                             Map<String, Object> map){
        try {
            productInfoService.onSale(productId);
        }catch (Exception e){
            log.error("【卖家商品】发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg",ProductStatusEnum.UP.getMessage()+"成功");
        map.put("url","/seller/product/list");
        return new ModelAndView("common/success",map);

    }

    @ApiOperation("【后台管理】商品下架")
    @GetMapping("offSale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String, Object> map){
        try {
            productInfoService.offSale(productId);
        }catch (Exception e){
            log.error("【卖家商品】发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ProductStatusEnum.DOWN.getMessage()+"成功");
        map.put("url","/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}
