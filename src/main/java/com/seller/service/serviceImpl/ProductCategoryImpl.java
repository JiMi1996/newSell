package com.seller.service.serviceImpl;

import com.seller.dao.ProductCategoryMapper;
import com.seller.dao.model.ProductCategory;
import com.seller.dao.model.ProductCategoryExample;
import com.seller.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: sell
 * @description:
 * @author: fbl
 * @create: 2018-07-02 15:22
 **/
@Service
@Slf4j
public class ProductCategoryImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public ProductCategory selectByPrimaryKey(Integer id) {
        return productCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer insert(ProductCategory productCategory) {
        int flag = CategoryTypeHas(productCategory.getCategoryType());
        if( flag == 0)
        {
            log.error("【商品类型】新增失败，该商品类型存在【CategoryType】={}",productCategory.getCategoryType());
            return flag;
        }
        return productCategoryMapper.insert(productCategory);
    }

    @Override
    public Integer updateByPrimaryKey(ProductCategory productCategory) {
        return productCategoryMapper.updateByPrimaryKey(productCategory);
    }

    @Override
    public Integer deleteByPrimaryKey(Integer id) {
        return productCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        ProductCategoryExample example = new ProductCategoryExample();
        ProductCategoryExample.Criteria criteria = example.createCriteria().andCategoryTypeIn(categoryTypeList);
        List<ProductCategory> list = productCategoryMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<ProductCategory> findAll() {
        ProductCategoryExample example = new ProductCategoryExample();
        List<ProductCategory> list = productCategoryMapper.selectByExample(example);
        return list;
    }

    //判断类型是否存在 1为空
    public Integer CategoryTypeHas(Integer categoryType){
        ProductCategoryExample example = new ProductCategoryExample();
        ProductCategoryExample.Criteria criteria= example.createCriteria().andCategoryTypeEqualTo(categoryType);
        List<ProductCategory> list = productCategoryMapper.selectByExample(example);
        return list.isEmpty()?1:0;
    }
}
