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
                    <form role="form" method="post" action="/seller/product/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input  type="text" name="productName" class="form-control" value="${(productInfo.productName)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input type="number" name="productPrice" min="0" class="form-control" value="${(productInfo.productPrice)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input  type="number" name="productStock" min="0" class="form-control" value="${(productInfo.productStock)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input  type="text" name="productDescription"  class="form-control" value="${(productInfo.productDescription)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <#--<img src="${(productInfo.productIcon)!''}" alt="">-->
                            <input  type="text" name="productIcon"  class="form-control" value="${(productInfo.productIcon)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                        <#if (productInfo.categoryType)?? && category.categoryType == productInfo.categoryType>
                                            selected
                                        </#if>
                                    >
                                        ${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <#--<div class="form-group">-->
                            <#--<label">File input</label><input type="file"/>-->
                            <#--<p class="help-block">-->
                                <#--Example block-level help text here.-->
                            <#--</p>-->
                        <#--</div>-->
                        <input  type="text" hidden name="productId" value="${(productInfo.productId)!""}"/>
                        <input  type="text" hidden name="productStatus" value="${(productInfo.productStatus)!0}"/>

                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>