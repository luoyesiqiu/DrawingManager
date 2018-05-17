<%@page import="com.sun.corba.se.spi.orbutil.fsm.Action"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.StudentDao"%>
<%@page import="util.JavascriptUtils"%>
<%@page import="dao.DrawingReviewDao"%>
<%@page import="bean.DrawingReview"%>
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

DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
StudentDao studentDao=new StudentDao();
List<DrawingReview> reviews=drawingReviewDao.getNewItems(pageIdx, 20);
if(reviews==null)
{
	out.print(JavascriptUtils.genAlertMsg("查询时出错"));
	return;
}
if(reviews.size()==0){
	out.print("<hr><br><div class=\"alert alert-warning\" role=\"alert\">没有待审核的作品</div>");
	return;
}
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
<th>状态</th>
<th>操作</th>
<tr>
<%
for(int i=0;i<reviews.size();i++)
{
	DrawingReview review=reviews.get(i);
	
	Student student=studentDao.getStudent(review.getStudentNumber());
	out.print("<tr id='review-item-"+review.getId()+"'>");
	out.print("<td>");
	out.print(review.getDrawingName());
	out.print("</td>");
	
	out.print("<td>");
	out.print(student.getStudentName());
	out.print("</td>");
	
	out.print("<td>");
	out.print(review.getDrawingCategory());
	out.print("</td>");
	
	out.print("<td>");
	out.print(review.getCreateDate());
	out.print("</td>");
	
	out.print("<td>");
	out.print(review.getPrizeName().equals("")?"未获奖":review.getPrizeName());
	out.print("</td>");
	
	out.print("<td>");
	out.print(review.getPrizeLevel().equals("")?"未获奖":review.getPrizeLevel());
	out.print("</td>");
	
	out.print("<td>");
	out.print(review.getPrizeDate().equals("")?"未获奖":review.getPrizeDate());
	out.print("</td>");
	
	out.print("<td>");
	out.print("<span class=\"label label-info\">"+(review.getAction()==0?"请求发布作品":(review.getAction()==1?"请求修改作品":"请求删除作品"))+"</span>");
	out.print("</td>");
	
	
	out.print("<td>");
	switch(review.getAction()){
		case 0:
			out.print(" <a class='btn btn-primary' data-toggle='modal' data-drawing-photo='"+review.getDrawingPhoto()+"' data-target='#viewer-modal''>查看</a>");
			
			out.print(String.format(" <a class='btn btn-success' onclick='reviewAction(\"%d\",\"%s\")'>通过审核</a>",0,review.getId()));
			out.print(String.format(" <a class='btn btn-danger' onclick='reviewAction(\"%d\",\"%s\")'>不通过审核</a>",3,review.getId()));
			out.print("</td>");			
		break;
		case 1:
			out.print(" <a class='btn btn-primary' data-toggle='modal' data-drawing-photo='"+review.getDrawingPhoto()+"' data-target='#viewer-modal''>查看</a>");
			
			out.print(String.format(" <a class='btn btn-success' onclick='reviewAction(\"%d\",\"%s\")'>通过审核</a>",1,review.getId()));
			out.print(String.format(" <a class='btn btn-danger' onclick='reviewAction(\"%d\",\"%s\")'>不通过审核</a>",4,review.getId()));
			out.print("</td>");
		break;
			
		case 2:
			out.print(" <a class='btn btn-primary' data-toggle='modal' data-drawing-photo='"+review.getDrawingPhoto()+"' data-target='#viewer-modal''>查看</a>");
			
			out.print(String.format(" <a class='btn btn-success' onclick='reviewAction(\"%d\",\"%s\")'>通过审核</a>",2,review.getId()));
			out.print(String.format(" <a class='btn btn-danger' onclick='reviewAction(\"%d\",\"%s\")'>不通过审核</a>",5,review.getId()));
			out.print("</td>");
		break;
	}
	out.print("</tr>");
	
}
%>
</table>

<!-- 查看/模态框（Modal） -->
<div class="modal fade" id="viewer-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">查看作品</h4>
            </div>
            <div class="modal-body">
            <img  style="width:100%;"></img>
            </div><!-- modal-body -->
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<nav>
  <ul class="pagination">
  <%
  int n=drawingReviewDao.totalPages(drawingReviewDao.totalItem(), pageSize);
  for(int i=1;i<=n;i++){ 
	  if(pageIdx==i)
		out.print("<li class=\"active\"><a href='javascript:void(0);'>"+i+"</a></li>");
	  else
		 out.print("<li><a onclick=\""+String.format("jump('%s');",i)+"\">"+i+"</a></li>");
  }
 %>
  </ul>
</nav>
</div>

<script>
$('#viewer-modal').on('show.bs.modal', function (event) {
	var button = $(event.relatedTarget);
	var drawingPhoto = button.data('drawing-photo');
	 var modal = $(this);
	 if(drawingPhoto!=null){
		  modal.find('.modal-content img').first().attr("src","./drawing/"+drawingPhoto);
	}
});
function reviewAction(actionTemp,reviewId){
	$.post("./ReviewAction",{action:actionTemp,review_id:reviewId},function(data){
		alert(data);
		if(data!=null){
			$("#review-item-"+reviewId).remove();
		}
	});
}

function jump(pageTemp)
{
	try{
		$("#review-result").load("QueryReview.jsp",{page:pageTemp},function(){
			scrollTo(0,0);
		});
	}
	catch(e){
		alert(e);
	}
}
</script>