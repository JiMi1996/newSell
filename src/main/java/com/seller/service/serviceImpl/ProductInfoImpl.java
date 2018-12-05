package com.seller.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seller.dao.ProductInfoMapper;
import com.seller.dao.model.ProductInfo;
import com.seller.dao.model.ProductInfoExample;
import com.seller.dto.CartDto;
import com.seller.exception.SellException;
import com.seller.util.enums.ExceptionEnum;
import com.seller.util.enums.ProductStatusEnum;
import com.seller.service.ProductInfoService;
import com.seller.util.pagehelper.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.Name;
import java.util.List;

/**
 * @program: sell
 * @description:
 * @author: fbl
 * @create: 2018-07-02 15:22
 **/
@Service
@CacheConfig(cacheNames = "productInfo")
public class ProductInfoImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public PageInfo<ProductInfo> findAll(Pagination condition) {
        PageHelper.startPage(condition.getCurrentPage(), condition.getPageSize());
        ProductInfoExample  example = new ProductInfoExample();
        List<ProductInfo> result = productInfoMapper.selectByExample(example);
        PageInfo page = new PageInfo(result);
        return page;
    }

    @Override
    public ProductInfo selectByPrimaryKey(String id) {
        return productInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        ProductInfoExample example= new ProductInfoExample();
        ProductInfoExample.Criteria criteria = example.createCriteria().andProductStatusEqualTo(ProductStatusEnum.UP.getCode());
        List<ProductInfo> productInfoList= productInfoMapper.selectByExample(example);
        return productInfoList;
    }

    @Override
    public Integer insert(ProductInfo productInfo) {
        return productInfoMapper.insert(productInfo);
    }

    @Override
    public Integer updateByPrimaryKey(ProductInfo productInfo) {
        return productInfoMapper.updateByPrimaryKey(productInfo);
    }

    @Override
    public Integer deleteByPrimaryKey(String id) {
        return productInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDto> cartDtoList) {
        for(CartDto cartDto : cartDtoList){
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(cartDto.getProductId());
            if(productInfo == null){
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock()+cartDto.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoMapper.updateByPrimaryKey(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList){
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(cartDto.getProductId());
            if(productInfo == null){
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDto.getProductQuantity();
            if(result < 0){
                throw new SellException(ExceptionEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);

            productInfoMapper.updateByPrimaryKey(productInfo);
        }
    }

    @Override
    public int onSale(String productId) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
        if(productInfo == null){
            throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode())){
            throw new SellException(ExceptionEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoMapper.updateByPrimaryKey(productInfo);
    }

    @Override
    public int offSale(String productId) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
        if(productInfo == null){
            throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus().equals(ProductStatusEnum.DOWN.getCode())){
            throw new SellException(ExceptionEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoMapper.updateByPrimaryKey(productInfo);
    }
}
