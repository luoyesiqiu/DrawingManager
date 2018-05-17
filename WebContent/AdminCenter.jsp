<%@page import="dao.GradeDao"%>
<%@page import="dao.MajorDao"%>
<%@page import="dao.DrawingDao"%>
<%@page import="java.util.List"%>
<%@page import="bean.Drawing"%>
<%@page import="bean.Major"%>
<%@page import="bean.Grade"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员后台</title>
<link href="./front/css/footer.css" rel="stylesheet">
<link href="./front/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="./front/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="./front/js/bootstrap.min.js"></script>
<link href="./front/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="./front/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="./front/js/locales/bootstrap-datetimepicker.zh-CN.js"  charset="UTF-8"></script>
<link href="./front/css/bg.css" rel="stylesheet">
<style>
.table>tbody>tr>td{
	vertical-align: middle;
}
</style>
</head>
<body>
<!-- 必须放在此处 -->
<script type="text/javascript" color="0,0,255" opacity='0.8' zIndex="-2" count="100" src="./front/js/canvas-nest.min.js"></script>
<br>
<div class="container theme-showcase" role="main">
<div class="panel panel-default">
<div class="panel-body">
<h5 class="text-right">欢迎您，管理员 <a href="./AdminLoginOut">退出</a></h5>
<h3 class="panel-title">管理员后台</h3>
<br>

<!-- 标签卡导航栏 -->
<ul class="nav nav-tabs" id="myTab">
<li class="nav-item active" >

<a href="#manage-drawing" class="nav-link" data-toggle="tab" aria-label="Left Align">
<span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>
管理作品
</a>
</li>
 
<li class="nav-item">
<a href="#manage-user" class="nav-link" data-toggle="tab" aria-label="Left Align">
<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
管理用户
</a>
</li>

<li class="nav-item">
<a href="#drawing-review" class="nav-link" data-toggle="tab" aria-label="Left Align">
<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
作品审核
</a>
</li>

<li class="nav-item">
<a href="#manage-data" class="nav-link" data-toggle="tab" aria-label="Left Align">
<span class="glyphicon glyphicon-stats" aria-hidden="true"></span>
数据统计
</a>
</li>

</ul>
<br>


<div class="tab-content">
<!-- 管理作品 -->
<div class="tab-pane fade in active" id="manage-drawing">
<div class="row">
 <div class="col-lg-6">
<div class="input-group">
<input type="text" id="drawing-keyword" name="drawing-keyword" class="form-control" placeholder="输入要查找的内容"/>
      <div class="input-group-btn">
        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">搜索类型<span class="caret"></span></button>
        <ul class="dropdown-menu dropdown-menu-right">
          <li><a onclick="queryDrawing('按作品名');">按作品名</a></li>
          <li><a onclick="queryDrawing('按类别');">按类别</a></li>
        </ul>
      </div><!-- /btn-group -->
</div><!-- input-group -->

</div><!-- col-lg-6 -->
</div>
<div id="drawing-result">

</div>
</div><!-- tab-pane -->


<!-- 管理用户 -->
<div class="tab-pane fade" id="manage-user">
<div class="row">
 <div class="col-lg-6">
<div class="input-group">
<input type="text" name="user-keyword" id="user-keyword"  class="form-control" placeholder="输入要查找的内容"/>
      <div class="input-group-btn">
        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">搜索类型 <span class="caret"></span></button>
        <ul class="dropdown-menu dropdown-menu-right">
          <li><a onclick="queryUser('按学号');">按学号</a></li>
          <li><a onclick="queryUser('按姓名');">按姓名</a></li>
          <li><a onclick="queryUser('按专业');">按专业</a></li>
          <li><a onclick="queryUser('按年级');">按年级</a></li>
        </ul>
      </div><!-- /btn-group -->
</div><!-- input-group -->

</div><!-- col-lg-6 -->
</div>

<div id="user-result">

</div>
</div><!-- tab-pane -->

<!-- 作品审核 -->
<div class="tab-pane fade" id="drawing-review">
<div class="row">
 <div class="col-lg-6">

</div><!-- col-lg-6 -->
</div>

<div id="review-result">

</div>
</div><!-- tab-pane -->


<!-- 统计 -->
<div class="tab-pane fade" id="manage-data">
<div id="statistical">
<div class="col-md-3">
<select class="form-control" name="student-major">
<option>选择专业...</option>
<%
	 MajorDao majorDao=new MajorDao();
	 List<Major> majorList=majorDao.getAll();
	 for(int i=0;i<majorList.size();i++)
		out.print("<option>"+majorList.get(i).getMajorName()+"</option>");
 %>
</select>
</div>

<div class="col-md-3">
<select class="form-control" name="student-grade">
<option>选择年级...</option>
 <%
  GradeDao gradeDao=new GradeDao();
  List<Grade> gradeList=gradeDao.getAll();
  for(int i=0;i<gradeList.size();i++)
		out.print("<option>"+gradeList.get(i).getGradeName()+"</option>");
%>
</select>
</div>

 <label class="col-md-2"><input type="checkbox"  name="show-type" vlaue="0" checked="checked"/>显示作品类别</label>
 
 <button class="btn btn-primary col-md-2" onclick="statisticalOnclick()">查看数据统计</button>
<br>
</div><!-- id=="statistical" -->
<!-- 统计结果 -->
<div id="statistical-result">

</div>

</div><!-- tab-pane -->

</div><!-- tab-content -->
</div><!-- panel body-->
</div><!-- panel -->
</div><!-- container -->


<script language="javascript">

	function queryDrawing(type){
		if($("#drawing-keyword").val()=="")
		{
			alert("请先输入要搜索的内容");
			return;
		}
		var val=$("#drawing-keyword").val();
		$("#drawing-result").load("QueryDrawing.jsp",{which:"1",submitBy:type,keyword:val,page:"1"});
	}
	
	function queryUser(type){
		if($("#user-keyword").val()=="")
		{
			alert("请先输入要搜索的内容");
			return;
		}
		var val=$("#user-keyword").val();
		$("#user-result").load("QueryUser.jsp",{which:"2",submitBy:type,keyword:val,page:"1"});
	}
	function statisticalData(majorTemp,gradeTemp,checkTemp,pageTemp){
		$("#statistical-result").load("StatisticalData.jsp",{major:majorTemp,grade:gradeTemp,page:pageTemp,check:checkTemp});
	}
	function statisticalOnclick()
	{
		var major=$("#statistical").find("select[name='student-major']").val();
		var grade=$("#statistical").find("select[name='student-grade']").val();
		var isCheck=$("#statistical").find("input[name='show-type']").is(':checked');
		if(major=="选择专业..."){
			major='';
		}
		if(grade=="选择年级..."){
			grade='';
		}
		statisticalData(major,grade,isCheck,1);
	}
	

	
	$(document).ready(function(){
		$("#review-result").load("QueryReview.jsp",{page:1});
	});
	
</script>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>