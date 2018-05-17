<%@page import="util.JavascriptUtils"%>
<%@page import="dao.StudentDao"%>
<%@page import="servlet.StudentLogin"%>
<%@page import="servlet.AdminLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./front/css/footer.css" rel="stylesheet">
<link href="./front/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="./front/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="./front/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./front/js/validate-input.js"></script>
<link href="./front/css/bg.css" rel="stylesheet">
<title>用户登录</title>
</head>
<body>
<!-- 必须放在此处 -->
<script type="text/javascript" color="0,0,255" opacity='0.8' zIndex="-2" count="200" src="./front/js/canvas-nest.min.js"></script>
<br>
<div class="container theme-showcase" role="main">
<!--<div class="jumbotron">-->
<div class="panel panel-default">
<div class="panel-body">
<div class="panel-title">用户登录</div>
<br>
<!-- 标签卡导航栏 -->
<ul class="nav nav-tabs" id="myTab">
<li class="nav-item active">
<a href="#student-login" class="nav-link" data-toggle="tab" aria-label="Left Align">
<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
学生登录
</a>
</li>

<li class="nav-item">
<a href="#admin-login" class="nav-link" data-toggle="tab" aria-label="Left Align">
<span class="glyphicon glyphicon-console" aria-hidden="true"></span>
管理员登录
</a>
</li>

</ul>
<br>
<div class="tab-content">
<!-- 学生登录 -->
<div class="tab-pane fade in active" id="student-login">
<form action="student-login" method="post" role="form" class="form-horizontal">
<table>
<tr>
<td><label class="control-label" >学号：</label></td>
<td><input type="text" class="form-control" name="<%=StudentLogin.STUDENT_NUMBER_KEY %>" id="student_id" placeholder="学号"/></td>
</tr>
<tr>
<td><label class="control-label" >密码：</label></td>
<td><input type="password" class="form-control" name="<%=StudentLogin.STUDENT_PASSWORD_KEY %>" id="student_password" placeholder="密码"/></td>
</tr>
</table>
<br>
<input type="submit" class="col-md-1 btn btn-primary" value="登录"/> 
<input type="reset" class="col-md-1 btn btn-danger" value="重置"/> 
</form>
<br>
<br>
<br>
<small>还没注册？<a href="./register.jsp">去注册</a></small>
</div><!-- student-login -->


<!-- 管理员登录 -->
<div class="tab-pane fade" id="admin-login">
<form action="admin-login" method="post" role="form" class="form-horizontal">
<table>

<tr>
<td><label class="control-label" >用户名：</label></td>
<td><input type="text" class="form-control" name="<%=AdminLogin.ADMIN_NAME_KEY %>" id="admin_name" placeholder="用户名"/></td>
</tr>
<tr>
<td><label class="control-label" >密码：</label></td>
<td><input type="password" class="form-control" name="<%=AdminLogin.ADMIN_PASSWORD_KEY %>" id="admin_password" placeholder="密码"/></td>
</tr>
</table>
<br>
<input type="submit" class="col-md-1 btn btn-primary" value="登录"/> 
<input type="reset" class="col-md-1 btn btn-danger" value="重置"/> 
</form>
</div>
<br>
</div><!-- tab-content -->
</div>
</div>
</div>

<script type="text/javascript">	
    //看情况阻止表单提交
    $("#student-login").submit(function(){
    	var studentId=$("#student_id").get(0).value;
    	var studentPassword=$("#student_password").get(0).value;
		if(isEmpty(studentId)||isEmpty(studentPassword))
		{
			alert("学号和密码都不能为空噢~");
			return false;
		}
		if(!validateStudentId(studentId)){
			alert("学号不合理，学号应全为数字，并且长度为6~20位");
			return false;
		}
		return true;
    });
    
    $("#admin-login").submit(function(){
    	var adminName=$("#admin_name").get(0).value;
    	var adminPwd=$("#admin_password").get(0).value;
		if(isEmpty(adminName)||isEmpty(adminPwd))
		{
			alert("用户名和密码都不能为空噢~");
			return false;
		}
		
		if(!validateAdminName(adminName)){
			alert("用户名不合理，用户名应只由英文字母和数字组成，并且长度为4~15位");
			return false;
		}
		return true;
    });
</script>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>