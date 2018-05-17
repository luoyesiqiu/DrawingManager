package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LikeCountDao;
import dao.LikeDao;

/**
 * Servlet implementation class Like
 */
//@WebServlet("/Like")
public class Like extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String USER_ID_KEY = "userId";
	public static final String LIKE_COUNT_KEY = "likeCount";
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String user=request.getParameter("user");
		String image=request.getParameter("image");
		if(user==null||image==null)
		{
			response.getWriter().print("点赞失败");
			return;
		}
		LikeDao likeDao=new LikeDao();
		LikeCountDao likeCountDao=new LikeCountDao();
		if(!likeDao.exists(user, image)){
			likeDao.insert(user, image);
			likeCountDao.like(image);
			response.getWriter().print("点赞成功");
		}
		else
		{
			response.getWriter().print("该作品你已经点过赞了");
		}
	}

}
