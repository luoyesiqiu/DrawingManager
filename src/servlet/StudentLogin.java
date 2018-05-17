package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.CookieManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.StudentDao;
import util.JavascriptUtils;

/**
 * ѧ����¼�߼�
 */
//@WebServlet({ "/StudentLogin", "/student-login" })
public class StudentLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final int COOKIE_AGE=60*60*24;
	//��
    public final static String STUDENT_NUMBER_KEY="student_id";
    public final static String STUDENT_PASSWORD_KEY="student_password";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		StudentDao studentDao=new StudentDao();
		HttpSession session=request.getSession();
		String studentId=(String)request.getParameter(STUDENT_NUMBER_KEY);
		String password=(String)request.getParameter(STUDENT_PASSWORD_KEY);
		if(studentDao.login(studentId, password)){
			
			session.setAttribute(STUDENT_NUMBER_KEY, studentId);
			session.setAttribute(STUDENT_PASSWORD_KEY, password);
			
			Cookie userCookie=new Cookie(STUDENT_NUMBER_KEY, studentId);
			userCookie.setMaxAge(COOKIE_AGE);
			response.addCookie(userCookie);
			
			Cookie passwordCookie=new Cookie(STUDENT_PASSWORD_KEY, password);
			passwordCookie.setMaxAge(COOKIE_AGE);
			response.addCookie(passwordCookie);
			
			request.setAttribute("message", "��¼�ɹ�");
			request.setAttribute("page", "StudentCenter.jsp");
			request.setAttribute("type", "success");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
		else{
			request.setAttribute("message", "��¼ʧ�ܣ������û���������");
			request.setAttribute("page", "login.jsp");
			request.setAttribute("type", "danger");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
	}

}
