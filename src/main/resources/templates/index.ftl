<html>
<#include "./common/header.ftl">
<#--<link href="../static/bootstrap/css/bootstrap.min.css" rel="stylesheet">-->
<#--<script src="../static/bootstrap/js/bootstrap.min.js"></script>-->
<body>
    <div id="wrapper" class="toggled">
        <div class="container">
            <div class="row clearfix">
                <div class="column">
                    <form role="form" class="form-horizontal text-center" method="post" action="/seller/login">
                        <div class="form-group col-md-10">
                            <label class="col-md-2 ">openid</label>
                            <div class="col-md-6">
                                <input type="text" name="openid" class="form-control" value=""/>
                            </div>
                        </div>
                        <div class="form-group col-md-10">
                            <label class="col-md-2">密码</label>
                            <div class="col-md-6">
                                <input type="text" name="password" class="form-control" value=""/>
                            </div>
                        </div>
                        <div class="form-group col-md-10 ">
                            <div class="col-md-1 col-md-push-1 text-right">
                            <input type="checkbox">
                            </div>
                            <div class="col-md-2 col-md-push-1 text-left">记住密码</div>

                        </div>
                        <div class="form-group col-md-10 ">
                            <button type="submit" class="col-md-4 col-md-offset-2 btn btn-primary">登录</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>