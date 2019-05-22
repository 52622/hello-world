<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="../../common/common.jsp"%>
<link rel="shortcut icon" href="${basePath}/resources/orange/images/orange.ico" type="image/x-icon" />
<title>登陆</title>
<script type="text/javascript">
    var basePath = "${basePath}";
</script>
</head>
<body>
<div class="login">
    <h2>Acced Form</h2>
    <div class="login-top">
        <h1>LOGIN FORM</h1>
        <form id="loginForm" action="${basePath}/login/doLogin">
            <%--<input type="text" value="User Id" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'User Id';}">--%>
            <input type="text" name="account" value="User Id">
            <input type="password" name="password" value="password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'password';}">
        </form>
        <div class="forgot">
            <a href="#" target="_blank">forgot Password</a>
            <input type="submit" value="Login" id="loginButton"/>
        </div>
    </div>
    <div class="login-bottom">
        <h3>New User &nbsp;<a href="#">Register</a>&nbsp Here</h3>
    </div>
</div>
<div class="copyright">
    <p>Copyright &copy; 2015.Company name All rights reserved.<a target="_blank" href="http://sc.chinaz.com/moban/">&#x7F51;&#x9875;&#x6A21;&#x677F;</a></p>
</div>

<script type="text/javascript" src="${basePath}/resources/script/login/index.js"></script>
</body>
</html>
