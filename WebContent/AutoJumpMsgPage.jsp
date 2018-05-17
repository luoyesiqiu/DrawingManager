<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>正在跳转...</title>
<link href="./front/css/bootstrap.min.css" rel="stylesheet">
<link href="./front/css/bootstrap-theme.min.css" rel="stylesheet">
<script type="text/javascript" src="./front/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="./front/js/bootstrap.min.js"></script>
<link href="./front/css/bg.css" rel="stylesheet">
</head>
<body>
<!-- 必须放在此处 -->
<script type="text/javascript" color="0,0,255" opacity='0.8' zIndex="-2" count="99" src="./front/js/canvas-nest.min.js"></script>
<br>
<div class="container">
<div class="panel panel-default">
<div class="panel-body">
<div class="alert alert-${requestScope.type}" role="alert">
  <strong>${requestScope.message}</strong><br><br><a href="${requestScope.page}">${requestScope.time}秒后自动跳转...点击此处立即跳转</a>
</div>
</div><!-- panel-body -->
</div><!-- panel -->
</div><!-- container -->
<script type="text/javascript">
function jump(){
	location='${requestScope.page}';
}
setTimeout('jump()',${requestScope.time}*1000);
</script>
</body>
</html>