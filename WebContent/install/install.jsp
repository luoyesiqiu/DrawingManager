<%@page import="dao.StudentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../front/css/footer.css" rel="stylesheet">
<link href="../front/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="../front/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="../front/js/bootstrap.min.js"></script>
<title>学生作品管理系统安装</title>
</head>
<body>

<div class="container">
<h3>学生作品管理系统安装</h3>
<hr>
<h4>安装须知</h4>
<ul>
<li>本网站只支持Mysql数据库</li>
<li>安装过程中将创建数据库和数据表</li>
<li>无需重复安装</li>
<li>安装完成后可将install目录删除</li>
</ul>
<a class="btn btn-primary btn-lg" href="./InitDatabase.jsp">现在安装>>></a>
</div>
</body>
</html> 