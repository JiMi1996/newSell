package com.seller.controller;

import com.seller.dao.ProductCategoryMapper;
import com.seller.dao.model.ProductCategory;
import com.seller.exception.SellException;
import com.seller.service.ProductCategoryService;
import com.seller.util.VO.Result;
import com.seller.util.enums.ExceptionEnum;
import com.seller.util.enums.ResultEnum;
import com.seller.util.form.CategoryForm;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: sell
 * @description:
 * @author: Mr.Tang
 * @create: 2018-07-02 12:16
 **/
@RestController
@RequestMapping("/sell/product")
@Slf4j
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation("查询商品类目")
    @GetMapping("select")
    public Result select(@RequestParam("categoryId")Integer categoryId){
        ProductCategory productCategory = productCategoryService.selectByPrimaryKey(categoryId);
        if(productCategory == null){
            throw new SellException(ExceptionEnum.CATEGORY_NOT_EXIST);
        }
        Result result = Result.success(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
        result.setData(productCategory);
        return result;
    }

    @ApiOperation("新增商品类目")
    @PostMapping("insert")
    public Result insert(@Valid CategoryForm CategoryForm,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【新增商品类目】参数不正确，orderForm ",CategoryForm);
            throw new SellException(ExceptionEnum.PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }
        ProductCategory productCategory= new ProductCategory();
        productCategory.setCategoryName(CategoryForm.getCategoryName());
        productCategory.setCategoryType(CategoryForm.getCategoryType());
        if(productCategoryService.insert(productCategory) != 1){
            return Result.success(ResultEnum.FAILD.getCode(),ExceptionEnum.CATEGORY_HAS_EXIST.getMessage());
        }
        return Result.success(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }

    @ApiOperation("修改商品类目")
    @PostMapping("update")
    public Result update(@Valid ProductCategory productCategory,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new SellException(ExceptionEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        productCategoryService.updateByPrimaryKey(productCategory);
        return Result.success(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }

    @ApiOperation("删除商品类目")
    @PostMapping("delete")
    public Result delete(@ApiParam(value = "商品类目id")@RequestParam("categoryId")Integer categoryId){
        productCategoryService.deleteByPrimaryKey(categoryId);
        return Result.success(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }
}
