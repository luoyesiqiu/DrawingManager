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
 * ReviewAction：处理各种审核事件
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
		//同意发布作品
		if(action.equals("0")){
			publishDrawing(reviewId);
		}
		//同意更新作品
		else if(action.equals("1")){
			updateDrawing(reviewId);
		}
		//同意删除作品
		else if(action.equals("2")){
			deleteDrawing(reviewId);
		}
		//不同意发布作品
		else if(action.equals("3")){
			refusePublishDrawing(reviewId);
		}
		//不同意更新作品
		else if(action.equals("4")){
			refuseUpdateDrawing(reviewId);
		}
		//不同意删除作品
		else if(action.endsWith("5")){
			refuseDeleteDrawing(reviewId);
		}
		else{
			response.sendError(500, "参数错误");
			return;
		}
	}
	
	/**
	 * 发布作品
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
		
		//发布成功，要删除项
		drawingReviewDao.deleteItem(reviewId);
		response.getWriter().print("通过发布审核");
	}
	
	/**
	 * 拒绝发布作品
	 * 1.删除数据库
	 * 2.删除图片
	 * @throws IOException 
	 */
	private void refusePublishDrawing(String reviewId) throws IOException
	{
		if(reviewId==null){
			return;
		}
		deleteReview(reviewId);
		response.getWriter().print("未通过发布审核");
	}
	
	/**
	 * 更新作品
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
		
		response.getWriter().print("通过更新审核");

	}
	
	/**
	 * 拒绝更新作品
	 * 1.删除数据库项
	 * @throws IOException 
	 */
	private void refuseUpdateDrawing(String reviewId) throws IOException
	{
		if(reviewId==null){
			return;
		}
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		drawingReviewDao.deleteItem(reviewId);
		response.getWriter().print("未通过更新审核");

	}
	
	/**
	 * 删除作品
	 * 
	 * 1.删除作品
	 * 2.删除数据项
	 */
	private void deleteDrawing(String reviewId) throws IOException
	{
		if(reviewId==null){
			return;
		}
		//1.删除作品
		/**
		 * 删除作品步骤：
		 * 0. 删除数据表中的项
		 * 1. 删除作品图片
		 * 2. 删除获奖作品图片
		 * 3. 删除作品评论
		 * 4. 删除作品赞
		 */
		String ROOT=request.getServletContext().getRealPath("/");
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();

		String drawingId=drawingReviewDao.searchById(reviewId).getDrawingId();
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
		//2.删除数据项
		drawingReviewDao.deleteItem(reviewId);
		response.getWriter().print("通过删除审核");
	}
	
	/**
	 * 拒绝删除作品
	 * 
	 * 1.移除数据库项
	 * @throws IOException 
	 */
	private void refuseDeleteDrawing(String reviewId) throws IOException
	{
		if(reviewId==null){
			return;
		}
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		drawingReviewDao.deleteItem(reviewId);
		response.getWriter().print("未通过删除审核");
	}
	/**
	 * 删除审核
	 * 1.删除数据库
	 * 2.删除图片
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
		response.getWriter().print("删除审核");

	}
}
