 <%@page import="servlet.AdminLogin"%>
<%@page import="servlet.StudentLogin"%>
<%@page import="dao.StudentDao"%>
<%@page import="java.util.List"%>
<%@page import="dao.DrawingDao"%>
<%@page import="bean.Drawing"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生作品展示与管理系统</title>
<link href="./front/css/bootstrap.min.css" rel="stylesheet">
<link href="./front/css/footer.css" rel="stylesheet">
<script type="text/javascript" src="./front/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="./front/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./front/js/validate-input.js"></script>
<link href="./front/css/bg.css" rel="stylesheet">
<script type="text/javascript" src="./front/js/jquery.goup.min.js"></script>
</head>
<body>
<!-- 必须放在此处 -->
<script type="text/javascript" color="0,0,255" opacity='0.8' zIndex="-2" count="100" src="./front/js/canvas-nest.min.js"></script>
<%
DrawingDao drawingDao=new DrawingDao();
int totalItem=drawingDao.totalItem();
request.setAttribute("totalPages", drawingDao.totalPages(totalItem, 10));
%>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">DM</a>
    </div><!-- navbar-header -->
     <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	   	<ul class="nav navbar-nav">
		     <li class="active"><a onclick="onNewest()">最新<span class="sr-only">(current)</span></a></li>
		     <li><a onclick="onHotest()">最热</a></li>
	    </ul>

		<ul class="nav navbar-nav navbar-right">
			<li>
			<%
			Cookie[] cookies=request.getCookies();
    		for (int i = 0; i < cookies.length; i++) {
				Cookie cookie=cookies[i];
				if(cookie.getName().equals(StudentLogin.STUDENT_NUMBER_KEY)){
					session.setAttribute(cookie.getName(), cookie.getValue());
				}
				if(cookie.getName().equals(StudentLogin.STUDENT_PASSWORD_KEY)){
					session.setAttribute(cookie.getName(), cookie.getValue());
				}
				if(cookie.getName().equals(AdminLogin.ADMIN_NAME_KEY)){
					session.setAttribute(cookie.getName(), cookie.getValue());
				}
				if(cookie.getName().equals(AdminLogin.ADMIN_PASSWORD_KEY)){
					session.setAttribute(cookie.getName(), cookie.getValue());
				}
			}
			if(session.getAttribute(StudentLogin.STUDENT_NUMBER_KEY)==null&&session.getAttribute(AdminLogin.ADMIN_NAME_KEY)==null){
				out.print("<a href=\"login.jsp\">登录</a>");
			}
			else if(session.getAttribute(StudentLogin.STUDENT_NUMBER_KEY)!=null){
				
				out.print("<a href=\"StudentCenter.jsp\">个人中心</a>");
			}
			else if(session.getAttribute(AdminLogin.ADMIN_NAME_KEY)!=null){
				
				out.print("<a href=\"AdminCenter.jsp\">管理员后台</a>");
			}
			%>
			</li>
		</ul>
		
		<form class="navbar-form navbar-right" role="search" id="search-form">
		<div class="input-group">

		    <input type="text" name="keyword" class="form-control" placeholder="搜索作品">
		  	<span class="input-group-btn">
			  <input type="button" onclick="onSearch()" class="btn btn-default" value="搜索"></input>
			 </span>
		  </div>
		</form>
    </div>
   </nav>
<div class="container">

    <!-- 项目主体 -->
	<div id="item-body">
		
	</div>

</div><!-- container -->
<script type="text/javascript">
    var totalPages=<%=request.getAttribute("totalPages")%>;
    //利用递归
    moreNew(1);
 	function moreNew(curPage)
	{
 		$("#more-btn").remove();
		$.post("QueryNewItems.jsp",{page:curPage},function(data){
			$("#item-body").append(data);
			if(curPage<totalPages)
			{
				$("#item-body").append("<button id='more-btn' class='btn btn-primary form-control' onclick='moreNew("+(curPage+1)+")'>查看更多</button>");
			}
		});
	}
 	function onNewest(){
 		$('#bs-example-navbar-collapse-1 li:eq(0) a').tab('show');
 		$("#item-body>*").remove();//移除所有子元素
 		moreNew(1);
 	}
 	
 	function onHotest(){
 		$('#bs-example-navbar-collapse-1 li:eq(1) a').tab('show');
 		$("#more-btn").remove();
 		$("#item-body>*").remove();//移除所有子元素
		$.post("QueryHotItems.jsp",{page:1},function(data){
			$("#item-body").append(data);
		});
 	}
 	function onSearch(){
 		var keyword=$("#search-form").find("input[name='keyword']").val();
 		if(!isEmpty(keyword))
		{
 	 		$("#more-btn").remove();
 	 		$("#item-body>*").remove();//移除所有子元素
			$.post("QuerySearchItems.jsp",{page:1,keyword:keyword},function(data){
				$("#item-body").append(data);
			});
		}
 		else{
 			alert("请先输入搜索内容");
 		}
 	}
    $(document).ready(function () {
        $.goup({
            trigger: 100,
            bottomOffset: 150,
            locationOffset: 100,
            title: '回顶部',
            titleAsText: true
        });
    });
</script>
<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>