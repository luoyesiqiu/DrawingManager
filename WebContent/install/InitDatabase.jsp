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
<title>学生作品管理系统安装/初始化</title>
</head>

<body>
<div class="container">
<h3>学生作品管理系统安装</h3>
<hr>
<form  method="post" action="../InitDatabase">
<label  class="control-label">数据库连接地址：</label>
<input type="text" name="database_url" class="form-control" placeholder="数据库连接地址" value="jdbc:mysql://localhost:3306"/>
<label  class="control-label">要创建的数据库名：</label>
<input type="text" name="database_name" class="form-control" placeholder="" value="DrawingWebsite"/>
<label  class="control-label">数据库连接用户名：</label>
<input type="text" name="database_user" class="form-control" placeholder="用户名" value="root"/>
<label  class="control-label">数据库连接密码：</label>
<input type="text" name="database_pwd" class="form-control" placeholder="密码"/>
<br>
<input type="submit" name="database_submit" class="col-md-1 btn btn-primary"  value="下一步"/>
</form>
</div>
</body>
</html> 