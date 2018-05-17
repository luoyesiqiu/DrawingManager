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
 * ɾ����Ʒ
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
		 * ɾ����Ʒ���裺
		 * 0. ɾ�����ݱ��е���
		 * 1. ɾ����ƷͼƬ
		 * 2. ɾ������ƷͼƬ
		 * 3. ɾ����Ʒ����
		 * 4. ɾ����Ʒ��
		 */
		String ROOT=request.getServletContext().getRealPath("/");
		String drawingId=request.getParameter("drawing_id");
		if(drawingId==null){
			response.getWriter().print("ɾ��ʧ��");
			return;
		}
		DrawingDao drawingDao=new DrawingDao();
		CommentDao commentDao=new CommentDao();
		LikeDao likeDao=new LikeDao();
		LikeCountDao likeCountDao=new LikeCountDao();
		Drawing drawing=drawingDao.searchById(drawingId);
		if(drawing==null){
			response.getWriter().print("ɾ��ʧ��");
			return;
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
			response.getWriter().print("ɾ���ɹ�");
		}
		else{
			response.getWriter().print("ɾ��ʧ��");
		}
	}

}
