<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

<#--边栏sidebar-->
<#include "../common/nav.ftl">
<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>商品ID</th>
                            <th>名称</th>
                            <th>单价</th>
                            <th>图片</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                    <#list productInfoPage.list as productInfo>
                    <tr>
                        <td>
                            ${productInfo.productId}
                        </td>
                        <td>
                            ${productInfo.productName}
                        </td>
                        <td>
                            ${productInfo.productPrice}
                        </td>
                        <td>
                            ${productInfo.productIcon}
                        </td>
                        <td>
                            ${productInfo.productStock}
                        </td>
                        <td>
                            ${productInfo.categoryType}
                        </td>
                        <td>
                            ${productInfo.productStock}
                        </td>
                        <td>
                            ${productInfo.createTime?string('yyyy-MM-dd HH:mm:ss')}
                        </td>
                        <td>
                            ${productInfo.updateTime?string('yyyy-MM-dd HH:mm:ss')}
                        </td>
                        <td><button><a href="/seller/product/detail?productId=${productInfo.productId}">修改</a></button></td>
                        <td>
                            <#if productInfo.productStatus == 0>
                                <button><a href="/seller/product/offSale?productId=${productInfo.productId}">下架</a></button>
                            <#else><button><a href="/seller/product/onSale?productId=${productInfo.productId}">上架</a></button>
                            </#if>
                        </td>

                    </tr>
                    </#list>
                        </tbody>
                    </table>
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                        <#if productInfoPage.getPageNum() lte 1>
                            <li class="disabled">
                                <a href="/seller/product/list?currentPage=${productInfoPage.getPageNum()-1}&pageSize=${productInfoPage.getPageSize()}">上一页</a>
                            </li>
                        <#else>
                             <li>
                                 <a href="/seller/product/list?currentPage=${productInfoPage.getPageNum()-1}&pageSize=${productInfoPage.getPageSize()}">上一页</a>
                             </li>
                        </#if>
                        <#list 1..productInfoPage.getPages() as index>
                            <#if productInfoPage.getPageNum() == index>
                             <li class="disabled">
                                 <a href="/seller/product/list?currentPage=${index}&pageSize=${productInfoPage.getPageSize()}">${index}</a>
                             </li>
                            <#else>
                            <li>
                                <a href="/seller/product/list?currentPage=${index}&pageSize=${productInfoPage.getPageSize()}">${index}</a>
                            </li>
                            </#if>
                        </#list>
                           <#if productInfoPage.getPageNum() gte productInfoPage.getPages()>
                            <li class="disabled">
                                <a href="/seller/product/list?currentPage=${productInfoPage.getPageNum()+1}&pageSize=${productInfoPage.getPageSize()}">下一页</a>
                            </li>
                           <#else>
                             <li>
                                 <a href="/seller/product/list?currentPage=${productInfoPage.getPageNum()+1}&pageSize=${productInfoPage.getPageSize()}">下一页</a>
                             </li>
                           </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>