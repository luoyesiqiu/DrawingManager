<%@page import="java.util.ArrayList"%>
<%@page import="dao.StudentDao"%>
<%@page import="util.JavascriptUtils"%>
<%@page import="dao.DrawingDao"%>
<%@page import="java.util.List"%>
<%@page import="bean.Drawing"%>
<%@page import="bean.Student"%>
<%@page import="bean.PrizeLevel"%>
<%@page import="dao.PrizeLevelDao"%>
<%@page import="bean.DrawingType"%>
<%@page import="dao.DrawingTypeDao"%>
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

if(which==null||!which.equals("1"))
	return;
DrawingDao drawingDao=new DrawingDao();
StudentDao studentDao=new StudentDao();
List<Drawing> drawings=null;
String type=null;
boolean blur=false;
//是否模糊搜索
if(keyword!=null){
	
	//按学号在查看作品时有用
	if(submitBy.equals("按学号")){
		type=DrawingDao.StudentNumber;
		blur=false;
		drawings=drawingDao.searchLimit(type, keyword, pageIdx, pageSize, !blur);
	}
	else if(submitBy.equals("按作品名")){
		type=DrawingDao.DrawingName;
		blur=true;
		drawings=drawingDao.searchLimit(type, keyword, pageIdx, pageSize, !blur);
	}
	else if(submitBy.equals("按类别"))
	{
		blur=false;
		type=DrawingDao.DrawingCategory;
		drawings=drawingDao.searchLimit(type, keyword, pageIdx, pageSize, !blur);
	}
}
if(drawings==null)
{
	out.print(JavascriptUtils.genAlertMsg("查询时出错"));
	return;
}
if(drawings.size()==0){
	out.print(JavascriptUtils.genAlertMsg("没有匹配的结果"));
	return;
}
application.removeAttribute("list");
application.setAttribute("list", drawings);
%>
<div id="query-drawing-body">
<br>
<table class="table table-striped table-bordered">
<tr>
<th>作品名称</th>
<th>创作人</th>
<th>作品类型</th>
<th>创作日期</th>
<th>最高获奖名称</th>
<th>最高获奖等级</th>
<th>获奖日期</th>
<th>操作</th>
<tr>
<%
for(int i=0;i<drawings.size();i++)
{
	Drawing drawing=drawings.get(i);
	
	Student student=studentDao.getStudent(drawing.getStudentNumber());
	out.print("<tr id='item-"+drawing.getId()+"'>");
	out.print("<td>");
	out.print(drawing.getDrawingName());
	out.print("</td>");
	
	out.print("<td>");
	out.print(student.getStudentName());
	out.print("</td>");
	
	out.print("<td>");
	out.print(drawing.getDrawingCategory());
	out.print("</td>");
	
	out.print("<td>");
	out.print(drawing.getCreateDate());
	out.print("</td>");
	
	out.print("<td>");
	out.print(drawing.getPrizeName().equals("")?"无":drawing.getPrizeName());
	out.print("</td>");
	
	out.print("<td>");
	out.print(drawing.getPrizeLevel().equals("")?"无":drawing.getPrizeLevel());
	out.print("</td>");
	
	out.print("<td>");
	out.print(drawing.getPrizeDate().equals("")?"无":drawing.getPrizeDate());
	out.print("</td>");
	
	
	out.print("<td>");
	out.print(" <a class='btn btn-primary' href='./DrawingViewer.jsp?img="+drawing.getId()+"&idx="+i+"'>查看</a>");
	
	out.print(" <a class='btn btn-warning' data-toggle='modal' data-drawing-id='"+drawing.getId()
	+"' data-drawing-name='"+drawing.getDrawingName()+"' data-prize-name='"+drawing.getPrizeName()
	+"' data-create-date='"+drawing.getCreateDate()+"' data-prize-date='"+drawing.getPrizeDate()
	//+"' data-drawing-category='"+drawing.getDrawingCategory()+"' data-prize-level='"+drawing.getPrizeLevel()
	+"' data-prize-desc='"+drawing.getDrawingDesc()+"' data-target='#modify-modal'>修改</a>");
	out.print(" <a class='btn btn-danger' onclick='deleteDrawing(\""+drawing.getId()+"\");'>删除</a>");
	out.print("</td>");
	
	out.print("</tr>");
	
}
%>
</table>
<nav>
  <ul class="pagination">
  <%
  int n=drawingDao.totalPages(drawingDao.totalItem(type, keyword, true), pageSize);
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
<div class="modal fade" id="modify-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
		<form action="UpdateDrawingInfo" method="post" enctype="multipart/form-data" 
		      role="form" class="form-horizontal">
		    <input type="hidden" name="drawing_id"></input>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">修改作品（留空项则不修改）</h4>
            </div>
            <div class="modal-body">
	            <div class="row">
				<div class="col-md-12">

					<table>
					<tr>
					<td><label class="control-label" >作品名称：</label></td>
					<td>
					<input type="text" class="form-control" name="drawing_name"></input>
					</td>
					</tr>
					
					<tr>
					<td><label class="control-label" >作品类型：</label></td>
					<td>
					<select class="form-control" name="drawing_category" >
						 <option></option>
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
		             <input class="form-control" name="create_date" size="25" type="text" value="" >
		             <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					 <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		           </div>
		           </td>
					</tr>
					
					<tr>
					<td><label class="control-label" >最高获奖名称：</label></td>
					<td>
					<input type="text" class="form-control" name="prize_name"></input>
					</td>
					</tr>
					
					<tr>
					<td><label class="control-label" >最高获奖等级：</label></td>
					<td>
					<select class="form-control" name="prize_level" >
					  <%
					  out.print("<option></option>");
					  out.print("<option>未获奖</option>");
				      PrizeLevelDao prizeLevelDao=new PrizeLevelDao();
				      List<PrizeLevel> prizeLevelList=prizeLevelDao.getAll();
				      for(int i=0;i<prizeLevelList.size();i++)
				    	out.print("<option>"+prizeLevelList.get(i).getPrizeLevelName()+"</option>");
					  %>
					  </select>
					</td>
					</tr>
					
					<tr>
					<td><label class="control-label" >获奖日期：</label></td>
					<td><div class="input-group date form_date col-md-12" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
		             <input class="form-control" name="prize_date" size="25" type="text" value="">
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
					<input type="file" class="form-control" name="drawing_photo"></input>
					</td>
					</tr>
					</table>

				</div>
				</div>
				<div class="row">
					<div class="col-md-9">
					<label class="control-label" >作品描述：</label>
					<textarea class="form-control" name="drawing_desc" rows="3"></textarea>
					</div>
				</div>
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

$('#modify-modal').on('show.bs.modal', function (event) {
	var button = $(event.relatedTarget);
	var drawingId = button.data('drawing-id');
	var drawingName = button.data('drawing-name');
	var  prizeName = button.data('prize-name');
	var drawingDesc = button.data('prize-desc');
	var createDate = button.data('create-date');
	var prizeDate = button.data('prize-date');
	 var modal = $(this);
	 if(drawingId!=null){
		  modal.find('.modal-content input[name="drawing_id"]').val(""+drawingId);
	}
	 if(drawingName!=null){
	  modal.find('.modal-content input[name="drawing_name"]').val(""+drawingName);
	 }
	 if(prizeName!=null){
	  modal.find('.modal-content input[name="prize_name"]').val(""+prizeName);
	 }
	 if(drawingDesc!=null){
	  modal.find('.modal-content textarea[name="drawing_desc"]').val(""+drawingDesc);
	 }
	 
	 if(createDate!=null){
		  modal.find('.modal-content input[name="create_date"]').val(""+createDate);
	}
	 if(prizeDate!=null){
		  modal.find('.modal-content input[name="prize_date"]').val(""+prizeDate);
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
function jump(pageTemp,whichTemp,keywordTemp,typeTemp)
{
	//user-result为父界面的div id
	try{
		$("#drawing-result").load("QueryDrawing.jsp",{which:whichTemp,submitBy:typeTemp,keyword:keywordTemp,page:pageTemp},function(){
			scrollTo(0,0);
		});
	}
	catch(e){
		alert(e);
	}
}
</script>