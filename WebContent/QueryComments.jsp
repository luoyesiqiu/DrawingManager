<%@page import="dao.CommentDao"%>
<%@page import="bean.Comment"%>
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
final int pageSize=10;
String drawing=request.getParameter("drawing");
int pageIdx=Integer.parseInt(request.getParameter("page"));
CommentDao commentDao=new CommentDao();
List<Comment> list=commentDao.getComments(drawing, pageIdx, pageSize);
SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("MM月dd日 HH:mm:ss");
SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
if(list.size()==0){
	out.print("<div class=\"alert alert-warning\" role=\"alert\">该作品还没有评论哦~ 赶快去抢个沙发吧</div>");
	return;
}
%>
<div class="panel panel-default">
<div class="panel-body">
<h4><strong>评论列表</strong></h4>

<%
for(Comment comment:list){
%>
<div class="media">
  <div class="media-left">
    <a href="#">
      <img class="media-object" width="64px" src="./front/icon/anonymous.png">
    </a>
  </div>
  <div class="media-body">
    <h4 class="media-heading text-primary"><%=comment.getNickname()%></h4>
    <p><strong><%=comment.getContent()%></strong></p>
    <p><%=simpleDateFormat1.format(simpleDateFormat2.parse(comment.getCreateDate()))%></p>
  </div>
</div><!-- media -->
  <hr>
<%} %>
<nav>
  <ul class="pagination">
  <%
  int n=commentDao.totalPages(commentDao.totalItem(drawing), pageSize);
  for(int i=1;i<=n;i++){ 
	  if(pageIdx==i)
		out.print("<li class=\"active\"><a href='javascript:void(0);'>"+i+"</a></li>");
	  else
		 out.print("<li><a onclick=\""+String.format("jump('%s','%s');",i,drawing)+"\">"+i+"</a></li>");
  }
 %>
  </ul>
</nav>
</div><!-- panel-body -->
</div><!-- panel panel-default -->
<script>
function jump(pageTemp,drawingTemp)
{
	//user-result为父界面的div id
	try{
		$("#comment-container").load("QueryComments.jsp",{page:pageTemp,drawing:drawingTemp},function(){
			scrollTo(0,0);
		});
	}
	catch(e){
		alert(e);
	}
}
</script>
