package com.seller.controller;

import com.seller.dao.model.ProductCategory;
import com.seller.dao.model.ProductInfo;
import com.seller.service.ProductCategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @create: 2018-08-02 10:23
 **/
@Controller
@RequestMapping("/seller/category")
public class SellCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation("【后台管理】商品类型列表")
    @GetMapping("list")
    public ModelAndView list(Map<String, Object> map){
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list",map);
    }

    @ApiOperation("【后台管理】商品类型详情")
    @GetMapping("detail")
    public ModelAndView detail(@RequestParam(value = "categoryId", required = false)Integer categoryId,
                              Map<String, Object> map) {
        if(categoryId != null){
            ProductCategory productCategory = productCategoryService.selectByPrimaryKey(categoryId);
            map.put("productCategory",productCategory);
        }
        return new ModelAndView("category/detail",map);
    }

    @ApiOperation("【后台管理】商品类型更新")
    @PostMapping("save")
    public ModelAndView save(@Valid ProductCategory productCategory,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        try {
            if(productCategory.getCategoryId() != null){
                productCategoryService.updateByPrimaryKey(productCategory);
            }else {
                productCategoryService.insert(productCategory);
            }
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        map.put("msg","保存成功");
        map.put("url","/seller/category/list");
        return new ModelAndView("common/success",map);
    }
}
