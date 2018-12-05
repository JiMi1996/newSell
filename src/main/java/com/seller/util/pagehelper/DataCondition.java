package com.seller.util.pagehelper;


public class DataCondition extends Pagination{


    /**
     * 通用模糊查询
     */
    private String searchValue;

    /**
     * 商品类别,详情见枚举 CommodityType
     */
    private String commodityType;

    /**
     * 订单状态
     *
     */
    private Integer orderStatus;

    private String userId;

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(String commodityType) {
        this.commodityType = commodityType;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }




}
