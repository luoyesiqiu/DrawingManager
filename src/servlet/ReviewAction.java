package servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import bean.Drawing;
import bean.DrawingReview;
import dao.CommentDao;
import dao.DrawingDao;
import dao.DrawingReviewDao;
import dao.LikeCountDao;
import dao.LikeDao;

/**
 * ReviewAction�������������¼�
 */
//@WebServlet("/ReviewAction")
public class ReviewAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	private HttpServletRequest request;
	private HttpServletResponse response;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.response=response;
		this.request=request;
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String action=request.getParameter("action");
		String reviewId=request.getParameter("review_id");
		//ͬ�ⷢ����Ʒ
		if(action.equals("0")){
			publishDrawing(reviewId);
		}
		//ͬ�������Ʒ
		else if(action.equals("1")){
			updateDrawing(reviewId);
		}
		//ͬ��ɾ����Ʒ
		else if(action.equals("2")){
			deleteDrawing(reviewId);
		}
		//��ͬ�ⷢ����Ʒ
		else if(action.equals("3")){
			refusePublishDrawing(reviewId);
		}
		//��ͬ�������Ʒ
		else if(action.equals("4")){
			refuseUpdateDrawing(reviewId);
		}
		//��ͬ��ɾ����Ʒ
		else if(action.endsWith("5")){
			refuseDeleteDrawing(reviewId);
		}
		else{
			response.sendError(500, "��������");
			return;
		}
	}
	
	/**
	 * ������Ʒ
	 */
	private void publishDrawing(String reviewId) throws IOException
	{
		if(reviewId==null){
			return;
		}
		DrawingDao drawingDao=new DrawingDao();
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		DrawingReview drawingReview=drawingReviewDao.searchById(reviewId);
		drawingDao.insert(drawingReview.getStudentNumber(), drawingReview.getDrawingName(), drawingReview.getDrawingCategory(), drawingReview.getDrawingSize()
				, drawingReview.getCreateDate(), drawingReview.getPrizeName(), drawingReview.getPrizeLevel(), drawingReview.getPrizeDate()
				, drawingReview.getPrizePhoto(), drawingReview.getDrawingPhoto(), drawingReview.getDrawingDesc(), drawingReview.getPublishDate());
		
		//�����ɹ���Ҫɾ����
		drawingReviewDao.deleteItem(reviewId);
		response.getWriter().print("ͨ���������");
	}
	
	/**
	 * �ܾ�������Ʒ
	 * 1.ɾ�����ݿ�
	 * 2.ɾ��ͼƬ
	 * @throws IOException 
	 */
	private void refusePublishDrawing(String reviewId) throws IOException
	{
		if(reviewId==null){
			return;
		}
		deleteReview(reviewId);
		response.getWriter().print("δͨ���������");
	}
	
	/**
	 * ������Ʒ
	 * @throws IOException 
	 */
	private void updateDrawing(String reviewId) throws IOException
	{
		if(reviewId==null){
			return;
		}
		DrawingDao drawingDao=new DrawingDao();
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		DrawingReview drawingReview=drawingReviewDao.searchById(reviewId);
		drawingDao.update(drawingReview.getDrawingId(), DrawingDao.DrawingName, drawingReview.getDrawingName());
		drawingDao.update(drawingReview.getDrawingId(), DrawingDao.DrawingCategory, drawingReview.getDrawingCategory());
		drawingDao.update(drawingReview.getDrawingId(), DrawingDao.DrawingSize, drawingReview.getDrawingSize());
		drawingDao.update(drawingReview.getDrawingId(), DrawingDao.CreateDate, drawingReview.getCreateDate());
		drawingDao.update(drawingReview.getDrawingId(), DrawingDao.PrizeName, drawingReview.getPrizeName());
		drawingDao.update(drawingReview.getDrawingId(), DrawingDao.PrizeLevel, drawingReview.getPrizeLevel());
		drawingDao.update(drawingReview.getDrawingId(), DrawingDao.PrizeDate, drawingReview.getPrizeDate());
		drawingDao.update(drawingReview.getDrawingId(), DrawingDao.PrizePhoto, drawingReview.getPrizePhoto());
		drawingDao.update(drawingReview.getDrawingId(), DrawingDao.DrawingPhoto, drawingReview.getDrawingPhoto());
		drawingDao.update(drawingReview.getDrawingId(), DrawingDao.DrawingDesc, drawingReview.getDrawingDesc());
		drawingDao.update(drawingReview.getDrawingId(), DrawingDao.PublishDate, drawingReview.getPublishDate());
		
		drawingReviewDao.deleteItem(reviewId);
		
		response.getWriter().print("ͨ���������");

	}
	
	/**
	 * �ܾ�������Ʒ
	 * 1.ɾ�����ݿ���
	 * @throws IOException 
	 */
	private void refuseUpdateDrawing(String reviewId) throws IOException
	{
		if(reviewId==null){
			return;
		}
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		drawingReviewDao.deleteItem(reviewId);
		response.getWriter().print("δͨ���������");

	}
	
	/**
	 * ɾ����Ʒ
	 * 
	 * 1.ɾ����Ʒ
	 * 2.ɾ��������
	 */
	private void deleteDrawing(String reviewId) throws IOException
	{
		if(reviewId==null){
			return;
		}
		//1.ɾ����Ʒ
		/**
		 * ɾ����Ʒ���裺
		 * 0. ɾ�����ݱ��е���
		 * 1. ɾ����ƷͼƬ
		 * 2. ɾ������ƷͼƬ
		 * 3. ɾ����Ʒ����
		 * 4. ɾ����Ʒ��
		 */
		String ROOT=request.getServletContext().getRealPath("/");
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();

		String drawingId=drawingReviewDao.searchById(reviewId).getDrawingId();
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
		//2.ɾ��������
		drawingReviewDao.deleteItem(reviewId);
		response.getWriter().print("ͨ��ɾ�����");
	}
	
	/**
	 * �ܾ�ɾ����Ʒ
	 * 
	 * 1.�Ƴ����ݿ���
	 * @throws IOException 
	 */
	private void refuseDeleteDrawing(String reviewId) throws IOException
	{
		if(reviewId==null){
			return;
		}
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		drawingReviewDao.deleteItem(reviewId);
		response.getWriter().print("δͨ��ɾ�����");
	}
	/**
	 * ɾ�����
	 * 1.ɾ�����ݿ�
	 * 2.ɾ��ͼƬ
	 * @throws IOException 
	 */
	private void deleteReview(String reviewId) throws IOException
	{
		if(reviewId==null){
			return;
		}
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		DrawingReview drawingReview=drawingReviewDao.searchById(reviewId);
		String rootPath=request.getServletContext().getRealPath("/drawing");
		File drawingPhotoFile =new File(rootPath+"/"+ drawingReview.getDrawingPhoto());
		File prizePhotoFile =new File(rootPath+"/"+ drawingReview.getPrizePhoto());
		if(drawingPhotoFile.exists())
		{
			drawingPhotoFile.delete();
		}
		
		if(prizePhotoFile.exists())
		{
			prizePhotoFile.delete();
		}
		drawingReviewDao.deleteItem(reviewId);
		response.getWriter().print("ɾ�����");

	}
}
