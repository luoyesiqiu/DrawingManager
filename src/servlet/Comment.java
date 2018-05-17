package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.CommentDao;

/**
 * 发布评论
 */
//@WebServlet("/Comment")
public class Comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String drawingId=request.getParameter("drawing");
		String nickname=request.getParameter("nickname");
		String content=request.getParameter("content");
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=simpleDateFormat.format(new Date());
		
		if(nickname==null||content==null)
		{
			request.setAttribute("message", "发布失败");
			request.setAttribute("page", request.getHeader("Referer"));
			request.setAttribute("type", "danger");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
			return;
		}
		CommentDao commentDao=new CommentDao();
		if(commentDao.insert(drawingId, nickname, content, date))
		{
			request.setAttribute("message", "发布成功");
			request.setAttribute("page", request.getHeader("Referer"));
			request.setAttribute("type", "success");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
		else{
			request.setAttribute("message", "发布失败");
			request.setAttribute("page", request.getHeader("Referer"));
			request.setAttribute("type", "danger");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
	}

}
