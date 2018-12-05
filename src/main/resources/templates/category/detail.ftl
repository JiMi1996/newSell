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
                    <form role="form" method="post" action="/seller/category/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input  type="text" name="categoryName" class="form-control" value="${(productCategory.categoryName)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>type</label>
                            <input type="number" name="categoryType" min="0" class="form-control" value="${(productCategory.categoryType)!""}"/>
                        </div>
                        <input  type="text" hidden name="categoryId" value="${(productCategory.categoryId)!""}"/>

                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>