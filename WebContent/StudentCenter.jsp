 <%@page import="bean.PrizeLevel"%>
<%@page import="dao.PrizeLevelDao"%>
<%@page import="test.PrizeLevelDaoTest"%>
<%@page import="bean.DrawingType"%>
<%@page import="dao.DrawingTypeDao"%>
<%@page import="servlet.StudentLogin"%>
<%@page import="dao.DrawingDao"%>
<%@page import="dao.StudentDao"%>
<%@page import="dao.GradeDao"%>
<%@page import="bean.Student"%>
<%@page import="java.util.List"%>
<%@page import="bean.Drawing"%>
<%@page import="bean.Grade"%>
<%@page import="bean.Major"%>
<%@page import="dao.MajorDao"%>
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
<title>个人中心</title>
</head>
<body>
<!-- 必须放在此处 -->
<script type="text/javascript" color="0,0,255" opacity='0.8' zIndex="-2" count="100" src="./front/js/canvas-nest.min.js"></script>
<br>
<%
StudentDao studentDao=new StudentDao();
Student student=studentDao.getStudent((String)session.getAttribute(StudentLogin.STUDENT_NUMBER_KEY));
%>
<div class="container theme-showcase" role="main">
<div class="panel panel-default">
<div class="panel-body">
<h5 class="text-right">欢迎您，<%=student.getStudentName()%> <a href="./StudentLoginOut">退出</a></h5>
<div class="panel-title">个人中心</div>
<br>
<!-- 标签卡导航栏 -->
<ul class="nav nav-tabs" id="myTab">
<li class="nav-item active" >

<a href="#drawing_list" class="nav-link" data-toggle="tab" aria-label="Left Align">
<span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>
已发布的作品
</a>
</li>
 
<li class="nav-item">
<a href="#publish" class="nav-link" data-toggle="tab" aria-label="Left Align">
<span class="glyphicon glyphicon-send" aria-hidden="true"></span>
发布作品
</a>
</li>

<li class="nav-item" >
<a href="#info" class="nav-link" data-toggle="tab" aria-label="Left Align">
<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
个人信息
</a>
</li>

</ul>
<br>
<div class="tab-content">

<!-- 第1个选项卡,作品列表 -->
<div class="tab-pane fade in active" id="drawing_list">
<div id="query_drawing_result">

</div>
</div>
<!-- 第1个选项卡结束 -->

 
<!-- 第2个选项卡,发布作品 -->
<div class="tab-pane fade" id="publish">
<form method="post" action="PublishDrawing" enctype="multipart/form-data">
<input type="hidden" name="student_number" value=<%=session.getAttribute("student_id")%>></input>
<div class="row">
<div class="col-md-5">
<table>
<tr>
<td><label class="control-label" >作品名称：</label></td>
<td>
<input type="text" class="form-control need-input" name="drawing_name"></input>
</td>
</tr>

<tr>
<td><label class="control-label" >作品类型：</label></td>
<td>
<select class="form-control need-input" name="drawing_category" >
	  <%
      DrawingTypeDao drawingTypeDao=new DrawingTypeDao();
      List<DrawingType> drawingList=drawingTypeDao.getAll();
      for(int i=0;i<drawingList.size();i++)
    	out.print("<option>"+drawingList.get(i).getDrawingTypeName()+"</option>");
	  %>
  </select>
</td>
</tr>

<tr>
<td><label class="control-label" >创作日期：</label></td>
<td><div class="input-group date form_date col-md-12" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
          <input class="form-control need-input" name="create_date" size="25" type="text" value="" readonly>
          <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
 <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
        </div>
        </td>
</tr>

<tr>
<td><label class="control-label" >最高获奖等级：</label></td>
<td>
<select class="form-control" name="prize_level" >
<option>未获奖</option>
	  <%
      PrizeLevelDao prizeLevelDao=new PrizeLevelDao();
      List<PrizeLevel> prizeLevelList=prizeLevelDao.getAll();
      for(int i=0;i<prizeLevelList.size();i++)
    	out.print("<option>"+prizeLevelList.get(i).getPrizeLevelName()+"</option>");
	  %>
  </select>
</td>
</tr>

<tr>
<td><label class="control-label">最高获奖名称：</label></td>
<td>
<input type="text" class="form-control" name="prize_name"></input>
</td>
</tr>



<tr>
<td><label class="control-label" >获奖日期：</label></td>
<td><div class="input-group date form_date col-md-12" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
          <input class="form-control" name="prize_date" size="25" type="text" value="" readonly>
          <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
 <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
        </div>
</td>
</tr>

<tr>
<td><label class="control-label" >获奖证书照片：</label></td>
<td>
<input type="file" class="form-control" name="prize_photo"></input>
</td>
</tr>

<tr>
<td><label class="control-label" >作品照片：</label></td>
<td>
<input type="file" class="form-control need-input" name="drawing_photo"></input>
</td>
</tr>
</table>
</div>
</div>
<div class="row">
<div class="col-md-5">
<label class="control-label" >作品描述：</label>
<textarea class="form-control need-input" name="drawing_desc" rows="3"></textarea>
</div>
</div>
<br>
<input type="submit" class="btn btn-primary col-md-1" value="发布"/>
</form>
</div>
<!-- 第2个选项卡结束 -->

<!-- 第3个选项卡，个人信息 -->
<div class="tab-pane fade" id="info">

<form action="UpdateStudentInfo" method="post" enctype="multipart/form-data">
<input type="hidden" name="student_number" value="<%=student.getStudentNumber() %>">
<table >
<tr>
<td></td>
<td>
<div class="col-md-3"></div>
<img src="<%=("./photos/"+student.getStudentPhoto()) %>" width="120px" height="120px" class="img-circle">
</td>
</tr>
<tr><td><br></td><td><br></td></tr>

<tr>
<td><label class="control-label" >新照片：</label></td>
<td><input type="file" class="form-control" name="new_student_photo"/></td>
</tr>
<tr>
<td><label class="control-label" >姓名：</label></td>
<td>
<input type="text" class="form-control modify-field" name="student_name" value=<%=student.getStudentName() %>></input>
</td>
</tr>

<tr>
<td><label class="control-label" >原密码：</label></td>
<td>
<input type="password" class="form-control modify-field" name="old_password" placeholder="必填项"></input>
</td>
</tr>

<tr>
<td><label class="control-label" >新密码：</label></td>
<td>
<input type="password" class="form-control" name="new_password" placeholder="可留空"></input>
</td>
</tr>

<tr>
<td><label class="control-label" >性别：</label></td>
<td>
		<input type="radio" checked="checked" name="student_gender" value="男"/>男
		<input type="radio" name="student_gender" value="女"/>女
</td>
</tr>

<tr>
<td><label class="control-label" >年级：</label></td>
 <td>
 <select class="form-control modify-field" name="student_grade" >
      <%
      	out.print("<option>"+student.getStudentGrade()+"</option>");
	      GradeDao gradeDao=new GradeDao();
	      List<Grade> gradeList=gradeDao.getAll();
	      for(int i=0;i<gradeList.size();i++){
	    	out.print("<option>"+gradeList.get(i).getGradeName()+"</option>");
	      }
       %>
   </select>
</td>
</tr>

<tr>
<td><label  class="control-label">专业：</label></td>
		<!--<input type="text" name="student_major" class="reg-field"/>  -->
			<td><select class="form-control" name="student_major" >
		      <%
		      		out.print("<option>"+student.getStudentMajor()+"</option>");
			      MajorDao majorDao=new MajorDao();
			      List<Major> majorList=majorDao.getAll();
			      for(int i=0;i<majorList.size();i++)
			    	out.print("<option>"+majorList.get(i).getMajorName()+"</option>");
		       %>
 </select></td>
</tr>

<tr>
<td>
<label class="control-label" >生日：</label></td>
<td>
<div class="input-group date form_date col-md-12" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
             <input class="form-control" name="student_birth" size="25" type="text" value="<%=student.getStudentBirth() %>">
             <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
			 <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
           </div>
</td>
</tr>

</table>
<input type="hidden" name="modify_role" value="student"></input>
<br>
<input type="submit" id="modify-submit" class="col-md-1 btn btn-primary" value="更改"/>

</form>
</div><!-- 第3个标签卡结束 -->
</div><!-- tab-content -->
</div><!-- panel body-->
</div><!-- panel -->
</div><!-- container -->
<script type="text/javascript">
function deleteDrawing(id)
{
	var isOK=confirm("确定要删除该作品?"); //在页面上弹出对话框
	if(isOK){
		$.post("./DeleteDrawing",{drawing_id:id},function(data){
			if(data=="删除成功"){
				$("#item-"+id).remove();
				alert(data);
			}
			else
			{
				alert("删除失败");
			}
		});
	}
}
$(document).ready(function(){

	$("#query_drawing_result").load("StudentQueryDrawing.jsp");
	
	if($("#publish").find("select[name='prize_level']").val()=='未获奖'){
		$("#publish").find("input[name='prize_name']").attr('disabled','disabled');
		$("#publish").find("input[name='prize_date']").attr('disabled','disabled');
		$("#publish").find("input[name='prize_photo']").attr('disabled','disabled');
	}
	
	//默认显示第一个标签
	//$('#myTab li:eq(0) a').tab('show');
	//阻止表单提交

	$("#publish").submit(function(){
		var desc=$("#publish").find('textarea[name="drawing_desc"]').val();
		var createDate=new Date($("#publish").find('input[name="create_date"]').val()).getTime();
		var prizeDate=new Date($("#publish").find('input[name="prize_date"]').val()).getTime();
		
		 var list=$("#publish").find(".need-input");
		  	for(var i=0;i<list.length;i++){
		  		var val=list.get(i).value;
				if(isEmpty(val))
				{
					alert("信息都不能留空噢");
					return false;
				}

		  	}//for
			//验证描述的长度
			if(!validateStringLen(desc,5,1000)){
		    	alert("描述长度为5~1000字符，请检查");
		    	return false;
		 	}
		  	
		  	//验证日期
		  	if(createDate>prizeDate){
		  		alert("创作日期不得大于获奖日期");
		  		return false;
		  	}
			return true;
	});
	$("#publish").find("select[name='prize_level']").change(function(data){
		var text=$(this).val();
		if(text=='未获奖'){
			$("#publish").find("input[name='prize_name']").attr('disabled','disabled');
			$("#publish").find("input[name='prize_date']").attr('disabled','disabled');
			$("#publish").find("input[name='prize_photo']").attr('disabled','disabled');
			
			$("#publish").find("input[name='prize_name']").val("");
			$("#publish").find("input[name='prize_date']").val("");
			$("#publish").find("input[name='prize_photo']").val("");
			
			$("#publish").find("input[name='prize_name']").attr('class','form-control');
			$("#publish").find("input[name='prize_date']").attr('class','form-control');
			$("#publish").find("input[name='prize_photo']").attr('class','form-control');
		}
		else{
			$("#publish").find("input[name='prize_name']").removeAttr('disabled');
			$("#publish").find("input[name='prize_date']").removeAttr('disabled');
			$("#publish").find("input[name='prize_photo']").removeAttr('disabled');
			
			$("#publish").find("input[name='prize_name']").attr('class','form-control need-input');
			$("#publish").find("input[name='prize_date']").attr('class','form-control need-input');
			$("#publish").find("input[name='prize_photo']").attr('class','form-control need-input');
		}
	});
	$("#info").submit(function(){
		var old_pwd=$("#info").find('input[name="old_password"]').val();
		var new_pwd=$("#info").find('input[name="new_password"]').val();

		if(isEmpty(old_pwd)){
			alert("必填项不能留空噢");
			return false;
		}

		if(!validatePwd(old_pwd)){
		    	alert("密码输入不合理，密码长度为5~20位，且不能包含空白符");
		    	return false;
		 }
		if(!isEmpty(new_pwd)){
			if(!validatePwd(new_pwd)){
		    	alert("密码输入不合理，密码长度为5~20位，且不能包含空白符");
		    	return false;
		 	}
		}
		return true;
	});
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
});
</script>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>