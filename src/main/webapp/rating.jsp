<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<link href="css/rating12.css" rel="stylesheet" type="text/css">
    <script src="lib/jquery-1.11.2.min.js"></script>
    <link href="lib/jquery-easyui-1.5.1/themes/material/easyui.css" rel="stylesheet" >
    <link href="lib/jquery-easyui-1.5.1/themes/icon.css" rel="stylesheet" >
    <link href="lib/jquery-easyui-1.5.1/themes/color.css" rel="stylesheet" >
    <script src="lib/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
</head>
<body>
	<div id="header">
		<img id="logo" src="images/LOGO.png">
        <h1>Perceived Walkability Evaluation</h1>
        <p id="usr">@username:${user.username}</p>
        <p id="msg">You have beening rated ${number} images!</p>
     </div>
	<img id="img" src="LjPass/${imgUrl}">
	<img id="illustrate" src="images/123.png">
    <!-- 
     <p id="location">@Geo-Location:${location}</p>
     -->
    <form action="<%=basePath%>rating" method="post">
        <span style="top:62%" class="category">Safety:</span><div id="safetyScore" class="score"><input name="safetyScore" class="easyui-slider" value="${psafetyValue}"
            data-options="showTip:true,rule:[0,'|',25,'|',50,'|',75,'|',100]"></div>
        <span style="top:70%" class="category">Convenience:</span><div id="comfortScore" class="score"><input name="comfortScore" class="easyui-slider" value="${pcomfortValue}"
            data-options="showTip:true,rule:[0,'|',25,'|',50,'|',75,'|',100]"></div>
        <span style="top:62%" class="category2">Comfort:</span><div id="convenienceScore" class="score2"><input name="convenienceScore" class="easyui-slider" value="${pconvenienceValue}"
            data-options="showTip:true,rule:[0,'|',25,'|',50,'|',75,'|',100]"></div>
        <span style="top:70%" class="category2">Attractiveness:</span><div id="attractivenessScore" class="score2"><input name="attractivenessScore" class="easyui-slider" value="${pattractivenessValue}"
            data-options="showTip:true,rule:[0,'|',25,'|',50,'|',75,'|',100]"></div>
        <span style="top:77%" class="category">Operation:</span><div class="score" style="top:77%">
            <input type="submit" value="Sure" style="width: 100%;border-radius: 4px;"/>
	    </div>
    </form>
    <footer>Copyright @ 2023 [<span class="txt">LiJiaXuan</span>] Developer<br>Knowledge Driven Intelligent Software Engineering Laboratory, <span class="txt">YNU</span></footer>
	<!-- 
	StreetViewRatingApp_HPSCIL@:${user.username}<br/>
	<img src="/StreetViews/${imgUrl}"><br/>
	${imgUrl}<br/>
	reference value:safety:${psafetyValue} comfort:${pcomfortValue} convenience:${pconvenienceValue} attractiveness:${pattractivenessValue}<br/>
	<form action="<%=basePath%>rating" method="post">
		safety:
		<input type="text" name="safetyScore" /><br/> 
		comfort:
		<input type="text" name="comfortScore" /><br/> 
		convenience:
		<input type="text" name="convenienceScore" /><br/> 
		attractiveness:
		<input type="text" name="attractivenessScore" /><br/>
		<input type="submit" value="确认" /><br/>
	</form> 
	-->
</body>
</html>