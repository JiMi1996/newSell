package com.seller.controller;

import com.github.pagehelper.PageInfo;
import com.seller.util.VO.ProductInfoResult;
import com.seller.util.VO.ProductListResult;
import com.seller.util.VO.Result;
import com.seller.dao.model.ProductCategory;
import com.seller.dao.model.ProductInfo;
import com.seller.service.ProductCategoryService;
import com.seller.service.ProductInfoService;
import com.seller.util.enums.ResultEnum;
import com.seller.util.pagehelper.Pagination;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: newsell
 * @description:
 * @author: fbl
 * @create: 2018-07-15 00:04
 **/
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;


    @ApiOperation(value = "用户订单全部列表")
    @GetMapping("/allList")
    public Result selectList(
            @ApiParam(required = true,name = "pageSize",value = "个数")@RequestParam("pageSize")int pageSize,
            @ApiParam(required = true,name = "currentPage",value = "当前页")@RequestParam("currentPage")int currentPage
                             ){
        Pagination pagination = new Pagination(pageSize,currentPage);
        PageInfo<ProductInfo> list = productInfoService.findAll(pagination);
        Result result = new Result();
        result.setCode(1);
        result.setSuccess(ResultEnum.SUCCESS.getSuccess());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(list);
        return result;
    }

    @ApiOperation(value = "获取菜谱",httpMethod = "GET",response = Result.class)
    @GetMapping("/list")
//    @Cacheable(cacheNames = "product",key = "123")
    public Result list(){
        //获取上架的全部商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        System.out.println(productInfoList);
        List<Integer> productCategoryTypeList = productInfoList.stream().map(e->e.getCategoryType()).collect(Collectors.toList());

        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(productCategoryTypeList);
        List<ProductListResult> resultList = new ArrayList();

        for(ProductCategory productCategory : productCategoryList){
            ProductListResult productListResult = new ProductListResult();
            productListResult.setName(productCategory.getCategoryName());
            productListResult.setType(productCategory.getCategoryType());
            List<ProductInfoResult> productInfoResultList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoResult productInfoResult = new ProductInfoResult();
                    BeanUtils.copyProperties(productInfo,productInfoResult);
                    productInfoResultList.add(productInfoResult);
                }
            }
            productListResult.setProductInfoResultsList(productInfoResultList);
            resultList.add(productListResult);
        }

        Result result = new Result();
        result.setCode(1);
        result.setMsg("success");
        result.setData(resultList);
        return result;
    }
}
