<%@page import="bean.Grade"%>
<%@page import="dao.GradeDao"%>
<%@page import="dao.MajorDao"%>
<%@page import="bean.Major"%>
<%@page import="dao.MajorDao"%>
<%@page import="util.JavascriptUtils"%>
<%@page import="dao.StudentDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta name="description" content="注册界面">
<meta name="author" content="">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./front/css/footer.css" rel="stylesheet">
<link href="./front/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="./front/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="./front/js/bootstrap.min.js"></script>
<link href="./front/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="./front/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="./front/js/locales/bootstrap-datetimepicker.zh-CN.js"  charset="UTF-8"></script>
<script type="text/javascript" src="./front/js/validate-input.js"></script>
<link href="./front/css/bg.css" rel="stylesheet">
<title>注册</title>
</head>
<body>
<!-- 必须放在此处 -->
<script type="text/javascript" color="0,0,255" opacity='0.8' zIndex="-2" count="200" src="./front/js/canvas-nest.min.js"></script>
<br>
<div class="container theme-showcase" role="main">
<div class="panel panel-default">
<div class="panel-body">
<h3 class="panel-title">学生注册</h3>
</br>
<!-- enctype="multipart/form-data" -->

<form action="student-register" method="post" enctype="multipart/form-data" 
	role="form" class="form-horizontal" id="register_form">
	<table>
		<tr>
		<td><label class="control-label" >学号：</label></td>
		<td><input type="text" name="student_number" class="reg-field form-control" placeholder="学号"/></td>
		</tr>
		
		<tr>
		<td><label class="control-label">密码：</label></td>
		<td><input type="password" name="student_password" class="reg-field form-control" placeholder="密码"/></td>
		</tr>
		
		<tr>
		<td><label  class="control-label">姓名：</label></td>
		<td><input type="text" name="student_name" class="reg-field form-control" placeholder="姓名"/></td>
		</tr>
		
		<tr>
		<td><label  class="control-label">性别：</label></td>
		<td><input type="radio" checked="checked" name="student_gender"/>男
		<input type="radio" name="student_gender"/>女</td>
		</tr>
		
		<tr>
		<td><label  class="control-label" >年级：</label></td>
		<!-- <input type="text" name="student_grade" class="reg-field"/> -->
		    <td><select class="form-control" name="student_grade" >
		      <%
			      GradeDao gradeDao=new GradeDao();
			      List<Grade> gradeList=gradeDao.getAll();
			      for(int i=0;i<gradeList.size();i++)
			    	out.print("<option>"+gradeList.get(i).getGradeName()+"</option>");
		       %>
		      </select>
		      </td>
		</tr>
		
		<tr>
		<td><label  class="control-label">专业：</label></td>
		<!--<input type="text" name="student_major" class="reg-field"/>  -->
			<td><select class="form-control" name="student_major" >
		      <%
			      MajorDao majorDao=new MajorDao();
			      List<Major> majorList=majorDao.getAll();
			      for(int i=0;i<majorList.size();i++)
			    	out.print("<option>"+majorList.get(i).getMajorName()+"</option>");
		       %>
		      </select></td>
		</tr>
		
		<tr>
		<td><label  class="control-label">出生日期：</label></td>
		<td><div class="input-group date form_date col-md-12" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
             <input class="form-control" name="student_birth" size="25" type="text" value="" readonly>
             <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
			 <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
           </div></td>
		</tr>
		
		<tr>
		<td><label  class="control-label">照片：</label></td>
		<td><input type="file" name="student_photo" class="reg-field form-control"/></td>
		</tr>

	</table>
	<br>
	<input type="submit" class="col-md-1 btn btn-primary" value="注册"/>
	<input type="reset" class="col-md-1 btn btn-danger" value="重置"/>
	</form>

</div><!-- panel body -->
</div><!-- panel -->
</div>
<script type="text/javascript">
$(document).ready(function(){
	
	$('.form_date').datetimepicker({
	    language:  'en',
	    weekStart: 1,
	    todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
	});

	//看情况阻止表单提交
	$("form").submit(function(){
	    var inputList=$(".reg-field");
	    var birthDate=new Date($("#register_form").find("input[name='student_birth']").val());
	    var nowDate=new Date();
	    for(var i=0;i<inputList.length;i++){
	       var val=inputList.get(i).value;
	       if(isEmpty(val)){
	           alert("填写完所有的信息才可以注册哦~");
	           return false;
	      }
	    }
	    
	    var studentNumber=$("#register_form input[name='student_number']").val();
	    if(!validateStudentId(studentNumber)){
	    	alert("学号输入不合理，学号应全为数字，并且长度为6~20位");
	    	return false;
	    }
	    
	    var studentPwd=$("#register_form input[name='student_password']").val();
	    if(!validatePwd(studentPwd)){
	    	alert("密码输入不合理，密码长度为5~20位，且不能包含空白符");
	    	return false;
	    }
	    
	    var studentName=$("#register_form input[name='student_name']").val();
	    if(!validateStudentName(studentName)){
	    	alert("学生姓名应由2-4个中文字符组成");
	    	return false;
	    }
	    
	    if(nowDate.getFullYear()-birthDate.getFullYear()<12){
	    	alert("出生日期不合理（12岁以下不允许注册）");
	    	return false;
	    }
	    return true;
	});
});

</script>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>