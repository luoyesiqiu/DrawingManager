package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDao;
import dao.DrawingTypeDao;
import dao.GradeDao;
import dao.MajorDao;
import dao.PrizeLevelDao;
import dao.StudentDao;
import util.MD5;

/**
 * Servlet implementation class InitSetting
 */
//@WebServlet("/InitSetting")
public class InitSetting extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String adminName=request.getParameter("admin_name");
		String adminPwd=request.getParameter("admin_pwd");
		String studentMajor=request.getParameter("student_major");
		String drawingType=request.getParameter("drawing_type");
		String prizeLevel=request.getParameter("prize_level");
		String studentGrade=request.getParameter("student_grade");
		String[] params={adminName,adminPwd,studentMajor,drawingType,prizeLevel,studentGrade};
		//��������Ƿ�Ϊ��
		for(String param:params){
			if(param==null){
				throwDataError(request,response);
				return;
			}
		}

		AdminDao adminDao=new AdminDao();
		adminDao.insert(adminName, MD5.encrypt(adminPwd));
		processStudentMajor(studentMajor, request, response);
		processDrawingType(drawingType, request, response);
		processPrizeLevel(prizeLevel, request, response);
		processStudentGrade(studentGrade, request, response);
		
		alertInstallSuccess(request, response);
	}
	
	/**
	 * �����꼶
	 * @param drawingType
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processStudentGrade(String studentGrade
			,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		GradeDao dao=new GradeDao();
		String[] grades=studentGrade.split("\\|");
		if(grades==null||grades.length==0){
			throwDataError(request,response);
			return;
		}
		
		for (int i = 0; i < grades.length; i++) {
			String grade=grades[i];
			dao.insert( grade);
		}
	}
	
	/**
	 * ����񽱵ȼ�
	 * @param drawingType
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processPrizeLevel(String prizeLevel
			,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		PrizeLevelDao dao=new PrizeLevelDao();
		String[] levels=prizeLevel.split("\\|");
		if(levels==null||levels.length==0){
			throwDataError(request,response);
			return;
		}
		
		for (int i = 0; i < levels.length; i++) {
			String level=levels[i];
			dao.insert( level);
		}
	}
	
	/**
	 * ������Ʒ����
	 * @param drawingType
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processDrawingType(String drawingType
			,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		DrawingTypeDao dao=new DrawingTypeDao();
		String[] types=drawingType.split("\\|");
		if(types==null||types.length==0){
			throwDataError(request,response);
			return;
		}
		
		for (int i = 0; i < types.length; i++) {
			String type=types[i];
			dao.insert( type);
		}
	}
	/**
	 * ����ѧ��רҵ
	 * @param studentMajor
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processStudentMajor(String studentMajor
			,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		MajorDao majorDao=new MajorDao();
		String[] majors=studentMajor.split("\\|");
		if(majors==null||majors.length==0){
			throwDataError(request,response);
			return;
		}
		
		for (int i = 0; i < majors.length; i++) {
			String major=majors[i];
			majorDao.insert( major);
		}
	}
	
	private void alertInstallSuccess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("message", "��װ��ɣ����ڽ�����ҳ");
		request.setAttribute("page", "index.jsp");
		request.setAttribute("type", "success");
		request.setAttribute("time", "3");
		request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
	}
	
	private void throwDataError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("message", "���ݴ������ڷ���");
		request.setAttribute("page", "InitDatabase.jsp");
		request.setAttribute("type", "danger");
		request.setAttribute("time", "3");
		request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
	}

}
