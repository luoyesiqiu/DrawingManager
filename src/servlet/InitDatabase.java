package servlet;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;

import dao.AdminDao;
import dao.BaseDao;
import dao.CommentDao;
import dao.DrawingDao;
import dao.DrawingReviewDao;
import dao.DrawingTypeDao;
import dao.GradeDao;
import dao.LikeCountDao;
import dao.LikeDao;
import dao.MajorDao;
import dao.PrizeLevelDao;
import dao.StudentDao;

/**
 * Servlet implementation class InitDatabase
 */
//@WebServlet("/InitDatabase")
public class InitDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String databaseName=request.getParameter("database_name");
		String url=request.getParameter("database_url");
		String databaseUser=request.getParameter("database_user");
		String databasePassword=request.getParameter("database_pwd");
		//String rootPath=request.getServletContext().getRealPath("/");
		String userPath=System.getenv("USERPROFILE")+"/DrawingManager/";
		File file=new File(userPath);
		if(!file.exists())
		{
			file.mkdirs();
		}
		//System.out.println(userPath);
		if(BaseDao.createDatabase( databaseName,url,databaseUser,databasePassword)){
			if(BaseDao.connectDatabase(url, databaseUser, databasePassword)){
				Properties properties=new Properties();
				properties.put("database_url", url+"/"+databaseName+"?useUnicode=true&characterEncoding=UTF-8");
				properties.put("database_username", databaseUser);
				properties.put("database_pwd", databasePassword);
				properties.store(new FileWriter(userPath+"preference.ini"), "网站信息");
				BaseDao[] baseDaos={
						new AdminDao(),
						new DrawingDao(),
						new LikeDao(),
						new MajorDao(),
						new StudentDao(),
						new LikeCountDao(),
						new DrawingTypeDao(),
						new PrizeLevelDao(),
						new GradeDao(),
						new DrawingReviewDao(),
						new CommentDao()
						};
				for(BaseDao baseDao:baseDaos)
				{
					baseDao.createTable();
				}
				response.sendRedirect("./install/InitSetting.jsp");
			}
			else
			{
				request.setAttribute("message", "数据库连接失败，请检查用户名和密码");
				request.setAttribute("page", "./install/InitDatabase.jsp");
				request.setAttribute("type", "danger");
				request.setAttribute("time", "3");
				request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
			}
		}
		else{
			request.setAttribute("message", "数据库创建失败，请检查连接地址");
			request.setAttribute("page", "./install/InitDatabase.jsp");
			request.setAttribute("type", "danger");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
	}

}
