package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginOut
 */
//@WebServlet("/AdminLoginOut")
public class AdminLoginOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginOut() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getSession().invalidate();
		Cookie[] cookies=request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			String name=cookies[i].getName();
			//清除cookie
			if(name.equals(AdminLogin.ADMIN_NAME_KEY)||name.equals(AdminLogin.ADMIN_PASSWORD_KEY)){
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
		}
		request.setAttribute("message", "退出成功");
		request.setAttribute("page", "login.jsp");
		request.setAttribute("type", "success");
		request.setAttribute("time", "3");
		request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
	}

}
