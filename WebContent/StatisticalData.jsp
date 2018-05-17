<%@page import="bean.DrawingTypeCount"%>
<%@page import="dao.DrawingTypeDao"%>
<%@page import="bean.DrawingType"%>
<%@page import="bean.PrizeLevel"%>
<%@page import="dao.PrizeLevelDao"%>
<%@page import="bean.Grade"%>
<%@page import="dao.GradeDao"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="bean.Major"%>
<%@page import="bean.Student"%>
<%@page import="bean.Drawing"%>
<%@page import="java.util.Iterator"%>
<%@page import="dao.MajorDao"%>
<%@page import="dao.StudentDao"%>
<%@page import="dao.DrawingDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
if(!request.getMethod().equals("POST"))
{
	return;
}
String major=request.getParameter("major");
String grade=request.getParameter("grade");
String pages=request.getParameter("page");
int pageIdx=Integer.parseInt(pages);
String detail=request.getParameter("check");

final int pageSize=20;
if(major==null||grade==null)
	return;
if(major.equals("")&&grade.equals(""))
{
	return;
}
//System.out.println(major+","+grade);
StudentDao studentDao=new StudentDao();
DrawingDao drawingDao=new DrawingDao();
String[] map=null;
if(major.equals("")&&!grade.equals("")){
	map=new String[2];
	map[0]=StudentDao.StudentGrade;
	map[1]=grade;
}
else if(!major.equals("")&&grade.equals(""))
{
	map=new String[2];
	map[0]=StudentDao.StudentMajor;
	map[1]=major;
}
else if(!major.equals("")&&!grade.equals(""))
{
	map=new String[4];
	map[0]=StudentDao.StudentGrade;
	map[1]=grade;
	map[2]=StudentDao.StudentMajor;
	map[3]=major;
}
List<Student> list=studentDao.searchByMultiField(pageIdx, pageSize,map);
if(list.size()==0){
	out.print("<hr><br><div class=\"alert alert-warning\" role=\"alert\">没有查询到符合条件的内容</div>");
	return;
}
out.print("<hr><br><table class=\"table table-striped table-bordered\">");
//是否显示作品类别
if(detail.equals("false")){
	out.print("<th>学生姓名</th><th>专业</th><th>年级</th><th>作品数</th><th>获奖作品数</th>");
	for(int i=0;i<list.size();i++){
		Student student=list.get(i);
		List<Drawing> drawings=drawingDao.searchByStudentNumber(student.getStudentNumber());
		int prizeSum=0;
		for(int k=0;k<drawings.size();k++)
		{
			if(!drawings.get(k).getPrizeName().equals("")){
				prizeSum++;
			}
		}
		out.print(String.format("<tr valign=\"middle\"><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>"
				,student.getStudentName()
				,student.getStudentMajor()
				,student.getStudentGrade()
				,drawings.size()
				,prizeSum
				));
	}
}
else{
	out.print("<th>学生姓名</th><th>专业</th><th>年级</th><th>作品类别</th><th>作品数</th><th>获奖作品数</th>");
	for(int i=0;i<list.size();i++){
		Student student=list.get(i);
		Map<String,DrawingTypeCount> tables=new HashMap<>();//创建哈希表
		List<Drawing> drawings=drawingDao.searchByStudentNumber(student.getStudentNumber());
		int prizeSum=0;
		for(int k=0;k<drawings.size();k++)
		{
			String type=drawings.get(k).getDrawingCategory();
			//存在这个键
			if(tables.containsKey(type)){
				DrawingTypeCount drawingTypeCount=tables.get(type);
				tables.put(type, new DrawingTypeCount(drawingTypeCount.getDrawingSum()+1
						,drawings.get(k).getPrizeName().equals("")?drawingTypeCount.getPrizeSum():drawingTypeCount.getPrizeSum()+1));
			}
			//不存在这个键
			else{
				tables.put(type
						,new DrawingTypeCount(1,drawings.get(k).getPrizeName().equals("")?0:1)
						);
			}
		}
		if(drawings.size()==0)
		{
			//如果一个用户没有发布任何作品
			tables.put("未发布作品",new DrawingTypeCount(0,0));
		}
		int size=tables.size();
		out.print(String.format("<tr><td rowspan=\"%d\">%s</td><td rowspan=\"%d\">%s</td><td rowspan=\"%d\">%s</td>"
				,size
				,student.getStudentName()
				,size
				,student.getStudentMajor()
				,size
				,student.getStudentGrade()
				));
		int idx=0;
		for(Map.Entry<String,DrawingTypeCount> entry:tables.entrySet()){
			if(idx==0){
				out.print(String.format("<td>%s</td><td>%s</td><td>%s</td></tr>", entry.getKey(),entry.getValue().getDrawingSum(),entry.getValue().getPrizeSum()));
			}
			else{
				out.print(String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>", entry.getKey(),entry.getValue().getDrawingSum(),entry.getValue().getPrizeSum()));
			}
			idx++;
		}
	}
}
out.print("</table>");

%>
<nav>
  <ul class="pagination">
  <%
  int n=0;
  if(!major.equals("")&&!grade.equals("")){
  	n=studentDao.totalPages(studentDao.totalItem(StudentDao.StudentGrade,grade,StudentDao.StudentMajor,major), pageSize);
  }
  else if(major.equals("")&&!grade.equals("")){
	  n=studentDao.totalPages(studentDao.totalItem(StudentDao.StudentGrade,grade), pageSize);
  }
  else if(!major.equals("")&&grade.equals("")){
	  n=studentDao.totalPages(studentDao.totalItem(StudentDao.StudentMajor,major), pageSize);
  }
  int start=1;
  for(int i=start;i<=n;i++){ 
	  if(pageIdx==i){
		out.print("<li class=\"active\"><a href='javascript:void(0);'>"+i+"</a></li>");
	  }
	  else{
		 out.print("<li><a onclick=\""+String.format("statisticalData('%s','%s','%s','%s')",major,grade,detail,i)+"\">"+i+"</a></li>");
	  }
  }
 %>
 </ul>
</nav>