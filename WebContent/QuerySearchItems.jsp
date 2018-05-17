<%@page import="dao.LikeCountDao"%>
<%@page import="dao.CommentDao"%>
<%@page import="dao.LikeDao"%>
<%@page import="servlet.Like"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.StudentDao"%>
<%@page import="util.JavascriptUtils"%>
<%@page import="dao.DrawingDao"%>
<%@page import="java.util.List"%>
<%@page import="bean.Drawing"%>
<%@page import="bean.Student"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%
if(request.getMethod().equals("GET")){
	return;
}
final int pageSize=20;
int pageIdx=Integer.parseInt(request.getParameter("page"));
String keyword=request.getParameter("keyword");

DrawingDao drawingDao=new DrawingDao();
StudentDao studentDao=new StudentDao();
//多字段模糊‘或’查询
List<Drawing> list=drawingDao.searchByMultiFieldOr(pageIdx, pageSize
		,DrawingDao.DrawingName, keyword
		,DrawingDao.DrawingDesc,keyword
		,DrawingDao.StudentNumber,keyword
		,DrawingDao.DrawingCategory,keyword
		,DrawingDao.PrizeLevel,keyword
		,DrawingDao.PublishDate,keyword
		,DrawingDao.CreateDate,keyword
		,DrawingDao.PrizeName,keyword
		);
application.removeAttribute("list");
application.setAttribute("list", list);
if(list.size()==0)
{
	out.print("<div class=\"alert alert-warning\" role=\"alert\">什么都没找到，换个关键词试试？</div>");
	return;
}
SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("MM月dd日 HH:mm");
SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
LikeCountDao likeDao=new LikeCountDao();
CommentDao commentDao=new CommentDao();
for(int i=0;i<list.size();i++)
{
	Drawing drawing=list.get(i);
	//太长的描述 减掉
	String desc=(drawing.getDrawingDesc().length()>100)?drawing.getDrawingDesc().substring(0, 100)+"......":drawing.getDrawingDesc();
%>
<div class="panel panel-default">
<div class="panel-body">
<div class="media">
  <div class="media-left">
    <a href="./DrawingViewer.jsp?img=<%=drawing.getId() %>&idx=<%=i %>" style="display:block;width:150px;height:150px;overflow:hidden;">
      <img style="width:150px;min-height:150px;" src="./drawing/<%=drawing.getDrawingPhoto()%>">
    </a>
  </div>
  <div class="media-body">
    <h3 class="media-heading"><a href="./DrawingViewer.jsp?img=<%=drawing.getId() %>&idx=<%=i %>"><%=drawing.getDrawingName()%></a></h3>
    <br>
    <p>描述：<%=desc%></p><br>
    <p><img class="img-rounded" width="24px" src="./photos/<%=studentDao.getStudent(drawing.getStudentNumber()).getStudentPhoto()%>"/> <strong><%=studentDao.getStudent(drawing.getStudentNumber()).getStudentName() %></strong> · <%=simpleDateFormat1.format(simpleDateFormat2.parse(drawing.getPublishDate()))%></p>
  </div>
  <div class="media-right media-bottom">
  <span class="badge">获赞 <%=likeDao.getLikeCount(drawing.getId()) %></span>
  </div>
    <div class="media-right media-bottom">
   <span class="badge">评论 <%=commentDao.getCommentCount(drawing.getId()) %></span>
  </div>
</div><!-- media -->

</div><!-- panel-body -->
</div><!-- panel panel-default -->
<hr>
<%} %>

<nav>
  <ul class="pagination">
  <%
  int n=drawingDao.totalPages(
		  drawingDao.totalItemOr(DrawingDao.DrawingName, keyword
					,DrawingDao.DrawingDesc,keyword
					,DrawingDao.StudentNumber,keyword
					,DrawingDao.DrawingCategory,keyword
					,DrawingDao.PrizeLevel,keyword
					,DrawingDao.PublishDate,keyword
					,DrawingDao.CreateDate,keyword
					,DrawingDao.PrizeName,keyword)
		  , pageSize);
  for(int i=1;i<=n;i++){ 
	  if(pageIdx==i)
		out.print("<li class=\"active\"><a href='javascript:void(0);'>"+i+"</a></li>");
	  else
		 out.print("<li><a onclick=\""+String.format("jump('%s','%s');",i,keyword)+"\">"+i+"</a></li>");
  }
 %>
  </ul>
</nav>
<script>
function jump(pageTemp,keywordTemp)
{
	//user-result为父界面的div id
	try{
		$("#item-body").load("QuerySearchItems.jsp",{keyword:keywordTemp,page:pageTemp},function(){
			scrollTo(0,0);
		});
	}
	catch(e){
		alert(e);
	}
}
</script>