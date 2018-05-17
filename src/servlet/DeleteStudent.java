package servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Drawing;
import bean.Student;
import dao.CommentDao;
import dao.DrawingDao;
import dao.LikeCountDao;
import dao.LikeDao;
import dao.StudentDao;

/**
 * Servlet implementation class DeleteStudent
 */
//@WebServlet("/DeleteStudent")
public class DeleteStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ROOT=request.getServletContext().getRealPath("/photos");
		String studentNumber=request.getParameter("student_id");
		if(studentNumber==null||studentNumber.equals("")){
			response.getWriter().print("删除失败");
			return;
		}
		StudentDao studentDao=new StudentDao();
		DrawingDao drawingDao=new DrawingDao();
		Student student=studentDao.getStudent(studentNumber);
		List<Drawing> drawings=drawingDao.searchByStudentNumber(studentNumber);
		//0.删除她的全部作品
		for (int i = 0; i < drawings.size(); i++) {
			deleteDrawing(request, response, drawings.get(i).getId());
		}
		//1.删除她的照片
		File file=new File(ROOT+File.separator+student.getStudentPhoto());
		file.delete();
		//2.删除她的数据
		studentDao.deleteItem(studentNumber);
		response.getWriter().print("删除成功");
	}

	/**
	 * 删除作品
	 * @param request
	 * @param response
	 * @param drawingId
	 * @return
	 */
	private boolean deleteDrawing(HttpServletRequest request, HttpServletResponse response,String drawingId){
		/**
		 * 删除作品步骤：
		 * 0. 删除数据表中的项
		 * 1. 删除作品图片
		 * 2. 删除获奖作品图片
		 * 3. 删除作品评论
		 * 4. 删除作品赞
		 */
		String ROOT=request.getServletContext().getRealPath("/");
		if(drawingId==null){
			return false;
		}
		DrawingDao drawingDao=new DrawingDao();
		CommentDao commentDao=new CommentDao();
		LikeDao likeDao=new LikeDao();
		LikeCountDao likeCountDao=new LikeCountDao();
		Drawing drawing=drawingDao.searchById(drawingId);
		if(drawing==null){
			return false;
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
			return true;
		}
		else{
			return false;
		}
		
	}
}
