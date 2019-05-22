<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<c:set var="fullPath" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<%--引入 jQuery 核心库--%>
<script type="text/javascript" src="${basePath}/resources/jquery-easyui-1.7.1/jquery.min.js"></script>
<%--引入 jQuery EasyUI 核心库--%>
<script type="text/javascript" src="${basePath}/resources/jquery-easyui-1.7.1/jquery.easyui.min.js"></script>
<%--引入 EasyUI 中文提示信息--%>
<script type="text/javascript" src="${basePath}/resources/jquery-easyui-1.7.1/locale/easyui-lang-zh_CN.js"></script>
<%--引入 EasyUI 核心 UI 文件 CSS--%>
<link rel="stylesheet" type="text/css" href="${basePath}/resources/jquery-easyui-1.7.1/themes/default/easyui.css" />
<%--引入 EasyUI 图标文件--%>
<link rel="stylesheet" type="text/css" href="${basePath}/resources/jquery-easyui-1.7.1/themes/icon.css" />

<link href="${basePath}/resources/orange/css/style.css" rel="stylesheet" type="text/css"/>


