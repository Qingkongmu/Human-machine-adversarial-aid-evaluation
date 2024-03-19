<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Perceived walkability evaluation@LiJiaxuan</title>
	<link href="css/index3.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="header">
        <img id="logo" src="images/LOGO.png">
        <h1>Perceived Walkability Evaluation</h1>
    </div>
	<div id="login">
		<form id="form1" action="<%=basePath%>login" method="post">
			<input type="text" name="username" id="uname" placeholder="请输入用户名">
			<br>
			<input type="password" name="password"  id="upass" placeholder="请输入密码">
			<br>
			<input type="image" src="images/125105.jpg">
			<img id="register" src="images/125106.jpg" onclick="window.location.href='<%=basePath%>register.jsp'">
        </form>
        <br>
	</div>
	<footer>Copyright @ 2023 [<span class="txt">LiJiaXuan</span>] Developer<br>Knowledge Driven Intelligent Software Engineering Laboratory, <span class="txt">YNU</span></footer>
</body>
</html>