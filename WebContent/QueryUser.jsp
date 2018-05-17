<%@page import="java.util.ArrayList"%>
<%@page import="dao.StudentDao"%>
<%@page import="util.JavascriptUtils"%>
<%@page import="dao.DrawingDao"%>
<%@page import="java.util.List"%>
<%@page import="bean.Drawing"%>
<%@page import="bean.Student"%>
<%@page import="bean.Grade"%>
<%@page import="bean.Major"%>
<%@page import="dao.MajorDao"%>
<%@page import="dao.GradeDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
if(request.getMethod().equals("GET")){
	return;
}
final int pageSize=10;
int pageIdx=Integer.parseInt(request.getParameter("page"));
String which=request.getParameter("which");
String keyword=request.getParameter("keyword");
String submitBy=request.getParameter("submitBy");

if(which==null||!which.equals("2"))
	return;
StudentDao studentDao=new StudentDao();
List<Student> students=null;
String type=null;
if(keyword!=null){
	if(submitBy.equals("按学号")){
		type=StudentDao.StudentNumber;
		students=studentDao.getStudents(type, keyword,pageIdx,pageSize);
	}
	else if(submitBy.equals("按姓名"))
	{
		type=StudentDao.StudentName;
		students=studentDao.getStudents(type, keyword,pageIdx,pageSize);

	}
	else if(submitBy.equals("按专业"))
	{
		type=StudentDao.StudentMajor;
		students=studentDao.getStudents(type, keyword,pageIdx,pageSize);
	}
	else if(submitBy.equals("按年级"))
	{
		type=StudentDao.StudentGrade;
		students=studentDao.getStudents(type, keyword,pageIdx,pageSize);
	}
}
if(students==null)
{
	out.print(JavascriptUtils.genAlertMsg("查询时出错"));
	return;
}
if(students.size()==0){
	out.print(JavascriptUtils.genAlertMsg("没有匹配的结果"));
	return;
}
%>
<div id="query-user-body">
<br>
<table  class="table table-striped table-bordered">
<tr>
<th>照片</th>
<th>学号</th>
<th>姓名</th>
<th>性别</th>
<th>年级</th>
<th>专业</th>
<th>生日</th>
<th>操作</th>
<tr>
<%
for(int i=0;i<students.size();i++)
{
	Student student=students.get(i);
	
	out.print("<tr id='item-"+student.getStudentNumber()+"'>");
	out.print("<td>");
	out.print("<img src='./photos/"+student.getStudentPhoto()+"' width='100px'/>");
	out.print("</td>");
	
	out.print("<td>");
	out.print(student.getStudentNumber());
	out.print("</td>");
	
	out.print("<td>");
	out.print(student.getStudentName());
	out.print("</td>");
	
	out.print("<td>");
	out.print(student.isStudentGender()?"女":"男");
	out.print("</td>");
	
	out.print("<td>");
	out.print(student.getStudentGrade());
	out.print("</td>");
	
	out.print("<td>");
	out.print(student.getStudentMajor());
	out.print("</td>");
	
	out.print("<td>");
	out.print(student.getStudentBirth());
	out.print("</td>");
	
	
	out.print("<td>");
	out.print(String.format("<a class='btn btn-primary' onclick='queryDrawing(\"%s\")'>查看Ta的作品</a>",student.getStudentNumber()));
	
	
	out.print(" <a class='btn btn-warning' data-toggle='modal' data-student-id='"+student.getStudentNumber()
	+"' data-student-name='"+student.getStudentName()+"' data-student-old-password='"+student.getPassword()
	+"' data-student-grade='"+student.getStudentGrade()+"' data-student-major='"+student.getStudentMajor()
	//+"' data-drawing-category='"+drawing.getDrawingCategory()+"' data-prize-level='"+drawing.getPrizeLevel()
	+"' data-student-birth='"+student.getStudentBirth()+"' data-student-photo='"+student.getStudentPhoto()+"' data-target='#modify-student-modal'>修改</a>");
	
	
	
	out.print(String.format("<a class='btn btn-danger' onclick='deleteStudent(\"%s\")'>删除账户</a>",student.getStudentNumber()));
	out.print("</td>");
	
	out.print("</tr>");
}
%>
</table>
<nav>
  <ul class="pagination">
  <%
  int n=studentDao.totalPages(studentDao.totalItem(type, keyword, true), pageSize);
  for(int i=1;i<=n;i++){ 
	  if(pageIdx==i)
		out.print("<li class=\"active\"><a href='javascript:void(0);'>"+i+"</a></li>");
	  else
		 out.print("<li><a onclick=\""+String.format("jump('%s','%s','%s','%s');",i,which,keyword,submitBy)+"\">"+i+"</a></li>");
  }
 %>
  </ul>
</nav>
</div>

<!-- 修改/模态框（Modal） -->
<div class="modal fade" id="modify-student-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
           <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">修改作品（留空项则不修改）</h4>
            </div>
        <form action="UpdateStudentInfo" method="post" enctype="multipart/form-data">
        	<div class="modal-body">
				<input type="hidden" name="student_number">
				<table >
				<tr>
				<td></td>
				<td>
				<div class="col-md-3"></div>
				<img name="old_student_photo" width="120px" height="120px" class="img-circle">
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
				<input type="text" class="form-control modify-field" name="student_name"/>
				</td>
				</tr>
				

				<input type="hidden" name="old_password"></input>
				
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
				             <input class="form-control" name="student_birth" size="25" type="text">
				             <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
							 <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				           </div>
				</td>
				</tr>
				
				</table>
				<input type="hidden" name="modify_role" value="admin"></input>
            </div><!-- modal-body -->
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" class="btn btn-primary">提交更改</button>

            </div>
          </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script>

function jump(pageTemp,whichTemp,keywordTemp,typeTemp)
{
	//user-result为父界面的div id
	try{
		$("#user-result").load("QueryUser.jsp",{which:whichTemp,submitBy:typeTemp,keyword:keywordTemp,page:pageTemp},function(){
			scrollTo(0,0);
		});
	}
	catch(e){
		alert(e);
	}
}

function queryDrawing(studentId){
	$("#drawing-result").load("QueryDrawing.jsp",{which:"1",submitBy:'按学号',keyword:studentId,page:"1"},function(){
		$('#myTab li:eq(0) a').tab('show');
	});
}
$('#modify-student-modal').on('show.bs.modal', function (event) {
	var button = $(event.relatedTarget);
	var studentId = button.data('student-id');
	var studentName = button.data('student-name');
	var  studentGrade = button.data('studnet-grade');
	var studentOldPassword = button.data('student-old-password');
	var studentBirth = button.data('student-birth');
	var studentPhoto = button.data('student-photo');
	 var modal = $(this);
	 if(studentId!=null){
		  modal.find('.modal-content input[name="student_number"]').val(""+studentId);
	}
	 if(studentName!=null){
	  modal.find('.modal-content input[name="student_name"]').val(""+studentName);
	 }
	 if(studentGrade!=null){
	  modal.find('.modal-content input[name="student_grade"]').val(""+studentGrade);
	 }
	 if(studentOldPassword!=null){
	  modal.find('.modal-content input[name="old_password"]').val(""+studentOldPassword);
	 }
	 
	 if(studentBirth!=null){
		  modal.find('.modal-content input[name="student_birth"]').val(""+studentBirth);
	}
	 
	 if(studentPhoto!=null){
		  modal.find('.modal-content img[name="old_student_photo"]').attr("src","./photos/"+studentPhoto);
	}
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
function deleteStudent(studentId){
	var isOK=confirm("确定要删除该用户?！该用户的作品也会被删除，并且此操作不可撤销！"); //在页面上弹出对话框
	if(isOK){
		$.post("DeleteStudent",{student_id:studentId},function(data){
			$("#item-"+studentId).remove();
			alert(data);
		});
	}
}
</script>