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
                            <th>订单ID</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                    <#list orderDtoPage.list as orderDto>
                    <tr>
                        <td>
                            ${orderDto.orderId}
                        </td>
                        <td>
                            ${orderDto.buyerName}
                        </td>
                        <td>
                            ${orderDto.buyerPhone}
                        </td>
                        <td>
                            ${orderDto.buyerAddress}
                        </td>
                        <td>
                            ${orderDto.orderAmount}
                        </td>
                        <td>
                            ${orderDto.getOrderStatusEnum().message}
                        </td>
                        <td>
                            ${orderDto.getPayStatusEnum().message}
                        </td>
                        <td>
                            ${orderDto.createTime?string('yyyy-MM-dd HH:mm:ss')}
                        </td>
                        <td><button><a href="/seller/order/detail?orderId=${orderDto.getOrderId()}">详情</a></button></td>
                        <td>
                                <#if orderDto.getOrderStatus() == 0>
                                    <button><a href="/seller/order/cancel?orderId=${orderDto.getOrderId()}">取消</a></button>
                                </#if>
                        </td>

                    </tr>
                    </#list>
                        </tbody>
                    </table>
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                        <#if orderDtoPage.getPageNum() lte 1>
                            <li class="disabled">
                                <a href="/seller/order/list?currentPage=${orderDtoPage.getPageNum()-1}&pageSize=${orderDtoPage.getPageSize()}">上一页</a>
                            </li>
                        <#else>
                             <li>
                                 <a href="/seller/order/list?currentPage=${orderDtoPage.getPageNum()-1}&pageSize=${orderDtoPage.getPageSize()}">上一页</a>
                             </li>
                        </#if>
                        <#list 1..orderDtoPage.getPages() as index>
                            <#if orderDtoPage.getPageNum() == index>
                             <li class="disabled">
                                 <a href="/seller/order/list?currentPage=${index}&pageSize=${orderDtoPage.getPageSize()}">${index}</a>
                             </li>
                            <#else>
                            <li>
                                <a href="/seller/order/list?currentPage=${index}&pageSize=${orderDtoPage.getPageSize()}">${index}</a>
                            </li>
                            </#if>
                        </#list>
                           <#if orderDtoPage.getPageNum() gte orderDtoPage.getPages()>
                            <li class="disabled">
                                <a href="/seller/order/list?currentPage=${orderDtoPage.getPageNum()+1}&pageSize=${orderDtoPage.getPageSize()}">下一页</a>
                            </li>
                           <#else>
                             <li>
                                 <a href="/seller/order/list?currentPage=${orderDtoPage.getPageNum()+1}&pageSize=${orderDtoPage.getPageSize()}">下一页</a>
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