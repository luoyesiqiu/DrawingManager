package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDao;

/**
 * Servlet implementation class AdminLogin
 */
//@WebServlet({"/AdminLogin","/admin-login"})
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int COOKIE_AGE = 24*60*60;
    public static final String ADMIN_NAME_KEY="admin_name";
    public static final String ADMIN_PASSWORD_KEY="admin_password";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLogin() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String adminName=request.getParameter(ADMIN_NAME_KEY);
		String adminPassword=request.getParameter(ADMIN_PASSWORD_KEY);
		AdminDao adminDao=new AdminDao();
		HttpSession session=request.getSession();
		if(adminDao.login(adminName, adminPassword)){
			
			session.setAttribute(ADMIN_NAME_KEY, adminName);
			session.setAttribute(ADMIN_PASSWORD_KEY, adminPassword);
			
			Cookie userCookie=new Cookie(ADMIN_NAME_KEY, adminName);
			userCookie.setMaxAge(COOKIE_AGE);
			response.addCookie(userCookie);
			
			Cookie passwordCookie=new Cookie(ADMIN_PASSWORD_KEY, adminPassword);
			passwordCookie.setMaxAge(COOKIE_AGE);
			response.addCookie(passwordCookie);
			
			request.setAttribute("message", "登录成功");
			request.setAttribute("page", "AdminCenter.jsp");
			request.setAttribute("type", "success");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
		else{
			request.setAttribute("message", "登录失败，请检查用户名和密码");
			request.setAttribute("page", "login.jsp");
			request.setAttribute("type", "danger");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
	}

}
