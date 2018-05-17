<%@page import="dao.DrawingReviewDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="bean.PrizeLevel"%>
<%@page import="dao.PrizeLevelDao"%>
<%@page import="test.PrizeLevelDaoTest"%>
<%@page import="bean.DrawingType"%>
<%@page import="bean.DrawingReview"%>
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
<%
DrawingDao drawingDao=new DrawingDao();
List<Drawing> drawings=drawingDao.searchByStudentNumber((String)session.getAttribute(StudentLogin.STUDENT_NUMBER_KEY));
application.removeAttribute("list");
application.setAttribute("list", drawings);
if(drawings.size()>0)
{
%>
<table class="table table-striped table-bordered">
<tr>
<th>作品名称</th>
<th>作品类型</th>
<th>创建日期</th>
<th>最高获奖名称</th>
<th>最高获奖等级</th>
<th>获奖日期</th>
<th>操作</th>
<th>状态</th>
<tr>
<%
DrawingReviewDao drawingReviewDao=new DrawingReviewDao();

for(int i=0;i<drawings.size();i++)
{
	Drawing drawing=drawings.get(i);
	DrawingReview drawingReview=drawingReviewDao.searchByDrawingId(drawing.getId());
	
	out.print("<tr>");
	out.print("<td>");
	out.print(drawing.getDrawingName());
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
	out.print("<a class='btn btn-primary' href='./DrawingViewer.jsp?img="+drawing.getId()+"&idx="+i+"'>查看</a>");
	if(drawingReview==null){
		out.print(" <a class='btn btn-warning' data-toggle='modal' data-drawing-id='"+drawing.getId()
		+"' data-drawing-name='"+drawing.getDrawingName()+"' data-prize-name='"+drawing.getPrizeName()
		+"' data-create-date='"+drawing.getCreateDate()+"' data-prize-date='"+drawing.getPrizeDate()
		//+"' data-drawing-category='"+drawing.getDrawingCategory()+"' data-prize-level='"+drawing.getPrizeLevel()
		+"' data-prize-desc='"+drawing.getDrawingDesc()+"' data-target='#modify-modal'>修改</a>");
	}else{
		out.print(" <a class='btn btn-warning disabled'>修改</a>");
	}
	if(drawingReview==null){
		out.print(" <a class='btn btn-danger' onclick='deleteDrawing(\""+drawing.getId()+"\");'>删除</a>");
	}else{
		out.print(" <a class='btn btn-danger disabled'>删除</a>");
	}
	out.print("</td>");
	
	out.print("<td>");
	if(drawingReview==null){
		out.print("<span class=\"label label-info\">已发布</span>");
	}
	else{
		out.print("<span class=\"label label-danger\">"+(drawingReview.getAction()==0?"待审核(请求发布作品)":(drawingReview.getAction()==1?"待审核(请求修改作品)":"待审核(请求删除作品)"))+"</span>");
	}
	out.print("</td>");
	out.print("</tr>");
}

%>
</table>

<%
}//if
else{
%>
<div class="alert alert-warning" role="alert">
您还没有发布作品
</div>
<%
}
%>

<!-- 修改/模态框（Modal） -->
<div class="modal fade" id="modify-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
		<form action="StudentUpdateDrawingInfo" method="post" enctype="multipart/form-data" 
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
function deleteDrawing(drawingId){
	$.post("./StudentDeleteDrawing",{drawing_id:drawingId,action:2},function(data){
		alert(data);
	});
}
</script>