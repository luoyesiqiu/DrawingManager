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
			response.getWriter().print("ɾ��ʧ��");
			return;
		}
		StudentDao studentDao=new StudentDao();
		DrawingDao drawingDao=new DrawingDao();
		Student student=studentDao.getStudent(studentNumber);
		List<Drawing> drawings=drawingDao.searchByStudentNumber(studentNumber);
		//0.ɾ������ȫ����Ʒ
		for (int i = 0; i < drawings.size(); i++) {
			deleteDrawing(request, response, drawings.get(i).getId());
		}
		//1.ɾ��������Ƭ
		File file=new File(ROOT+File.separator+student.getStudentPhoto());
		file.delete();
		//2.ɾ����������
		studentDao.deleteItem(studentNumber);
		response.getWriter().print("ɾ���ɹ�");
	}

	/**
	 * ɾ����Ʒ
	 * @param request
	 * @param response
	 * @param drawingId
	 * @return
	 */
	private boolean deleteDrawing(HttpServletRequest request, HttpServletResponse response,String drawingId){
		/**
		 * ɾ����Ʒ���裺
		 * 0. ɾ�����ݱ��е���
		 * 1. ɾ����ƷͼƬ
		 * 2. ɾ������ƷͼƬ
		 * 3. ɾ����Ʒ����
		 * 4. ɾ����Ʒ��
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
		//ɾ���ɹ���ǰ�������ݿ�������һ��
		//0. ɾ�����ݱ��е���
		if(drawingDao.deleteItem(drawingId))
		{
			//1. ɾ����ƷͼƬ
			if(drawingFile.exists()){
				drawingFile.delete();
			}
			//2. ɾ������ƷͼƬ
			if(prizeFile.exists()){
				prizeFile.delete();
			}
			//3. ɾ����Ʒ����
			commentDao.deleteItem(drawingId);
			//4. ɾ����Ʒ��
			likeDao.deleteItem(drawingId);
			likeCountDao.deleteItem(drawingId);
			return true;
		}
		else{
			return false;
		}
		
	}
}
