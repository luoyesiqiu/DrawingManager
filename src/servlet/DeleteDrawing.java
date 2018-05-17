package servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Drawing;
import dao.CommentDao;
import dao.DrawingDao;
import dao.LikeCountDao;
import dao.LikeDao;

/**
 * 删除作品
 */
//@WebServlet("/DeleteDrawing")
public class DeleteDrawing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		/**
		 * 删除作品步骤：
		 * 0. 删除数据表中的项
		 * 1. 删除作品图片
		 * 2. 删除获奖作品图片
		 * 3. 删除作品评论
		 * 4. 删除作品赞
		 */
		String ROOT=request.getServletContext().getRealPath("/");
		String drawingId=request.getParameter("drawing_id");
		if(drawingId==null){
			response.getWriter().print("删除失败");
			return;
		}
		DrawingDao drawingDao=new DrawingDao();
		CommentDao commentDao=new CommentDao();
		LikeDao likeDao=new LikeDao();
		LikeCountDao likeCountDao=new LikeCountDao();
		Drawing drawing=drawingDao.searchById(drawingId);
		if(drawing==null){
			response.getWriter().print("删除失败");
			return;
		}
		File drawingFile=new File(ROOT+"/drawing"+File.separator+drawing.getDrawingPhoto());
		File prizeFile=new File(ROOT+"/drawing"+File.separator+drawing.getPrizePhoto());
		//删除成功的前提是数据库中有这一项
		//0. 删除数据表中的项
		if(drawingDao.deleteItem(drawingId))
		{
			//1. 删除作品图片
			if(drawingFile.exists()){
				drawingFile.delete();
			}
			//2. 删除获奖作品图片
			if(prizeFile.exists()){
				prizeFile.delete();
			}
			//3. 删除作品评论
			commentDao.deleteItem(drawingId);
			//4. 删除作品赞
			likeDao.deleteItem(drawingId);
			likeCountDao.deleteItem(drawingId);
			response.getWriter().print("删除成功");
		}
		else{
			response.getWriter().print("删除失败");
		}
	}

}
