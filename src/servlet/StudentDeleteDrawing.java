package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Drawing;
import bean.DrawingReview;
import dao.DrawingDao;
import dao.DrawingReviewDao;

/**
 * StudentDeleteDrawing��ѧ������ɾ����Ʒ������Աͬ���ſ�����Ч
 */
//@WebServlet("/StudentDeleteDrawing")
public class StudentDeleteDrawing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentDeleteDrawing() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=request.getParameter("action");
		String drawingId=request.getParameter("drawing_id");
		
		if(action==null||!action.equals("2")){
			response.getWriter().print("����ɾ����Ʒʧ��");
//			request.setAttribute("message", "����ɾ����Ʒʧ��");
//			request.setAttribute("page", "StudentCenter.jsp");
//			request.setAttribute("type", "danger");
//			request.setAttribute("time", "3");
//			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
			return;
		}
		
		if(drawingId==null||drawingId.equals("")){
			response.getWriter().print("����ɾ����Ʒʧ��");
//			request.setAttribute("message", "����ɾ����Ʒʧ��");
//			request.setAttribute("page", "StudentCenter.jsp");
//			request.setAttribute("type", "danger");
//			request.setAttribute("time", "3");
//			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		DrawingDao drawingDao=new DrawingDao();
		//DrawingReview drawingReview=drawingReviewDao.searchById(reviewId);
		Drawing drawing=drawingDao.searchById(drawingId);
		
		if(drawingReviewDao.insert(drawing.getId(), drawing.getStudentNumber(), drawing.getDrawingName(), drawing.getDrawingCategory(), drawing.getDrawingSize()
				, drawing.getCreateDate(), drawing.getPrizeName(), drawing.getPrizeLevel(), drawing.getPrizeDate(), drawing.getPrizePhoto()
				, drawing.getDrawingPhoto(), drawing.getDrawingDesc(), drawing.getPublishDate(), 2)){
			response.getWriter().print("����ɾ����Ʒ�ɹ�");
//			request.setAttribute("message", "����ɾ����Ʒ�ɹ�");
//			request.setAttribute("page", "StudentCenter.jsp");
//			request.setAttribute("type", "success");
//			request.setAttribute("time", "3");
//			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
		else{
			response.getWriter().print("����ɾ����Ʒʧ��");
		}
		
	}

}
