<%@page import="dao.LikeCountDao"%>
<%@page import="dao.LikeDao"%>
<%@page import="servlet.Like"%>
<%@page import="dao.StudentDao"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.awt.Image"%>
<%@page import="dao.DrawingDao"%>
<%@page import="bean.Drawing"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./front/css/footer.css" rel="stylesheet">
<link href="./front/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="./front/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="./front/js/bootstrap.min.js"></script>
<link type="text/css" href="./front/css/magiczoomplus.css" rel="stylesheet"/>
<script type="text/javascript" src="./front/js/mzp-packed.js"></script>
<link href="./front/css/footer.css" rel="stylesheet">
<script type="text/javascript" src="./front/js/validate-input.js"></script>
<link href="./front/css/bg.css" rel="stylesheet">
<style>
	.center-box{
	display:-webkit-box;
	display:-moz-box;
	display:-o-box;
	display:-ms-box;
	display:box;
	margin:0;
	padding:10px;
	}
	.center-box{
	-webkit-box-orient:vertical;
	-moz-box-orient:vertical;
	-o-box-orient:vertical;
	-ms-box-orient:vertical;
	box-orient:vertical;
	}
	.center-box{
	-webkit-box-align:center;
	-moz-box-align:center;
	-o-box-align:center;
	-ms-box-align:center;
	box-align:center;
	}
	.right-box{
	float:left top;
	}
	.box{
	max-width:600px;
	}
</style>

<%
Drawing drawing=null;
StudentDao userDao=new StudentDao();
String imageId=null;
List<Drawing> list=null;
int index=0;
if(request.getMethod().equals("GET")){
    imageId=request.getParameter("img");
    String idx=request.getParameter("idx");
    list=(List<Drawing>)application.getAttribute("list");//这个变量是为了实现翻页

    if(idx!=null){
    	index=Integer.parseInt(idx);
    }
	if(imageId==null||idx==null||list==null){
		response.sendError(500,"参数错误");
		return;
	}
	DrawingDao drawingDao=new DrawingDao();
	drawing=drawingDao.searchById(imageId);
	if(drawing==null){
		response.sendError(500,"该作品找不到了哦~");
		return;
	}
}
String[] size=drawing.getDrawingSize().split("x");
int width=Integer.parseInt(size[0]);
int height=Integer.parseInt(size[1]);
LikeDao likeDao=new LikeDao();
LikeCountDao likeCountDao=new LikeCountDao();
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
request.setAttribute(Like.LIKE_COUNT_KEY,likeCountDao.getLikeCount(imageId));
Cookie[] cookies=request.getCookies();
//cookie是否存在
boolean exists=false;
for(Cookie cookie:cookies)
{
	if(cookie.getName().equals(Like.USER_ID_KEY))
	{
		request.setAttribute(Like.USER_ID_KEY, cookie.getValue());
		exists=true;
	}
}
//不存在
if(!exists)
{
	String id= request.getSession().getId();
	request.setAttribute(Like.USER_ID_KEY,id);
	Cookie cookie=new Cookie(Like.USER_ID_KEY,id);
	cookie.setMaxAge(Integer.MAX_VALUE);
	response.addCookie(cookie);
}
%>

<title>查看作品 - <%=drawing.getDrawingName() %></title>
</head>
<body>

<!-- 必须放在此处 -->
<script type="text/javascript" color="0,0,255" opacity='0.8' zIndex="-2" count="100" src="./front/js/canvas-nest.min.js"></script>
<script type="text/javascript">
function like(userId,imageId)
{
	$.post("./Like",{user:userId,image:imageId},function(data){
		if(data=="点赞成功"){
			var count=parseInt($("#likeCount").text())+1;
			$("#likeCount").text(count);
			$("#like-img").attr("src","./front/icon/like-clicked.png")
		}
		else
		{
			alert(data);
		} 
	});
}

function showPrizePhoto(){
	$('#myModal').modal('show');
}
</script>
<br>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel"><%=drawing.getPrizeName()%>(<%=drawing.getPrizeLevel()%>)</h4>
            </div>
            <div class="modal-body">
            <img src="./drawing/<%=drawing.getPrizePhoto()%>" style="width:100%;"></img>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="container theme-showcase" role="main">
<div class="panel panel-default">
<div class="panel-body">
<div class="thumbnail">
<div class="center-box">
<a href="<%="./drawing/"+(new File(drawing.getDrawingPhoto())).getName()%>" class="MagicZoom MagicThumb">
<img id="main-img" class="img-responsive" src="<%="./drawing/"+drawing.getDrawingPhoto()%>" style="width:640px;"></img>
</a>

</div><!-- center -->

<div class="center-box">
<div class="caption center-box">
<h3><%=drawing.getDrawingName() %></h3>
<p>作品作者：<%=userDao.getStudent(drawing.getStudentNumber()).getStudentName() %></p>
<p>作品尺寸：<%=drawing.getDrawingSize() %></p>
<p>发布日期：<%=simpleDateFormat.format(simpleDateFormat.parse(drawing.getPublishDate())) %></p>
<p><strong>描述：<%=drawing.getDrawingDesc() %></strong></p>
<button class="btn btn-primary" <%=drawing.getPrizeName().equals("")?"disabled=\"disabled\"":"" %> onclick="showPrizePhoto()">查看获奖证书</button>
<div class="like center-box">
<img src="./front/icon/like-normal.png" id="like-img" width="64px" height="64px" onclick="like('<%=request.getAttribute(Like.USER_ID_KEY) %>','<%=imageId %>');"/>
<p id="likeCount" class="badge"><%=request.getAttribute(Like.LIKE_COUNT_KEY) %></p>
</div>
</div><!-- caption -->
</div>
</div><!--thumbnail  -->
<nav>
  <ul class="pager">
  <%
  
  if(index==0||list.size()==1){
    out.print(String.format("<li class=\"previous disabled\"><a href=\"%s\"><span aria-hidden=\"true\">&larr;</span> 上一幅画</a></li>","#"));
  }else{
	out.print(String.format("<li class=\"previous\"><a href=\"%s\"><span aria-hidden=\"true\">&larr;</span> 上一幅画</a></li>","./DrawingViewer.jsp?img="+list.get(index-1).getId()+"&idx="+(index-1)));
  }
  if(index==list.size()-1){
  	out.print(String.format("<li class=\"next disabled\"><a href=\"%s\">下一幅画 <span aria-hidden=\"true\">&rarr;</span></a></li>","#"));
  }
  else{
	  out.print(String.format("<li class=\"next\"><a href=\"%s\">下一幅画 <span aria-hidden=\"true\">&rarr;</span></a></li>","./DrawingViewer.jsp?img="+list.get(index+1).getId()+"&idx="+(index+1)));
  }
  
    %>
    </ul>
</nav>
</div><!-- panel body-->
</div><!-- panel -->


<div class="panel panel-default">

<div class="panel-body">
<h4><strong>发表评论</strong></h4>
<form action="./Comment" method="post" id="comment-form">
<input class="form-control comment-field" name="nickname" placeholder="昵称"></input>
<textarea class="form-control comment-field" name="content" rows="4" placeholder="评论的内容"></textarea>
<input type="hidden" name="drawing" value="<%=imageId%>"></input>
<hr>
<button type="submit" class="btn btn-primary form-control">发表</button>
</form>
</div><!-- panel body -->
</div><!-- panel -->

<div id="comment-container">

</div>
</div><!-- container -->

<script type="text/javascript">

$(document).ready(function(){
	
	$("#comment-form").submit(function(){
		  var list=$(".comment-field");
		  	for(var i=0;i<list.length;i++){
		  		var val=list.get(i).value;
				if(val==null||val=="")
				{
					alert("昵称和评论内容都要填写噢");
					return false;
				}

		  	}//for
		    var comment=$("#comment-form textarea[name='content']").val();
		    if(!validateStringLen(comment,5,1000)){
		    	alert("评论内容长度必须在5~1000个字符之间");
		    	return false;
		    }
		  	return true;
	});
	
	$("#comment-container").load("QueryComments.jsp",{page:"1",drawing:"<%=imageId%>"});
});

</script>

<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>