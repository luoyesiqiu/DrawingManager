 <%@page import="servlet.AdminLogin"%>
<%@page import="servlet.StudentLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <footer class="bs-docs-footer">
  <div class="container">
  	<h4><a href="index.jsp">首页</a>
		<%
		if(session.getAttribute(StudentLogin.STUDENT_NUMBER_KEY)==null&&session.getAttribute(AdminLogin.ADMIN_NAME_KEY)==null){
			out.print("<a href=\"login.jsp\">登录</a></h4>");
		}
		else if(session.getAttribute(StudentLogin.STUDENT_NUMBER_KEY)!=null){
			
			out.print("<a href=\"StudentCenter.jsp\">个人中心</a></h4>");
		}
		else if(session.getAttribute(AdminLogin.ADMIN_NAME_KEY)!=null){
			
			out.print("<a href=\"AdminCenter.jsp\">管理员后台</a></h4>");
		}
		%>
     <p>学生作品展示与管理系统</p>
    <p>源码：https://github.com/luoyesiqiu/DrawingManager</p>
  </div>
</footer>