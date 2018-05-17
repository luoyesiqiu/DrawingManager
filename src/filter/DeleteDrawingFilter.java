package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.AdminDao;
import dao.StudentDao;
import servlet.AdminLogin;
import servlet.StudentLogin;

/**
 * Servlet Filter implementation class DeleteDrawingFilter
 */
//@WebFilter("/DeleteDrawingFilter")
public class DeleteDrawingFilter implements Filter {

    /**
     * Default constructor. 
     */
    public DeleteDrawingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		AdminDao adminDao=new AdminDao();
		StudentDao studentDao=new StudentDao();
		HttpServletRequest httpServletRequest=((HttpServletRequest)request);
		Cookie[] cookies=httpServletRequest.getCookies();
		HttpSession session=httpServletRequest.getSession();
		String adminName=null;
		String adminPassword=null;
		String studentId=null;
		String studentPassword=null;
		String role=null;
		//sessionø’æÕ≥¢ ‘∂¡»°cookie
		if(session.getAttribute(AdminLogin.ADMIN_NAME_KEY)==null||session.getAttribute(StudentLogin.STUDENT_NUMBER_KEY)==null){
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie=cookies[i];
				if(cookie.getName().equals(AdminLogin.ADMIN_NAME_KEY)){
					role="admin";
					adminName=cookie.getValue();
					session.setAttribute(cookie.getName(), cookie.getValue());
				}
				if(cookie.getName().equals(AdminLogin.ADMIN_PASSWORD_KEY)){
					adminPassword=cookie.getValue();
					session.setAttribute(cookie.getName(), cookie.getValue());
				}
				
				if(cookie.getName().equals(StudentLogin.STUDENT_NUMBER_KEY)){
					role="student";
					studentId=cookie.getValue();
					session.setAttribute(cookie.getName(), cookie.getValue());
				}
				if(cookie.getName().equals(StudentLogin.STUDENT_PASSWORD_KEY)){
					studentPassword=cookie.getValue();
					session.setAttribute(cookie.getName(), cookie.getValue());
				}
			}

			if(role==null)
			{
				request.setAttribute("message", "ƒ˙Œ¥µ«¬º£¨«Îœ»µ«¬º°£1");
				request.setAttribute("page", "login.jsp");
				request.setAttribute("type", "warning");
				request.setAttribute("time", "3");
				request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
				return ;
			}
			else if(role.equals("admin")){
				//≈–∂œ «∑ÒŒ™ø’∫Õ”√ªß√‹¬Î «∑Ò∫œ¿Ì
				if(!(adminName!=null&&adminPassword!=null)||!adminDao.login(adminName, adminPassword)){
					request.setAttribute("message", "ƒ˙Œ¥µ«¬º£¨«Îœ»µ«¬º°£1");
					request.setAttribute("page", "login.jsp");
					request.setAttribute("type", "warning");
					request.setAttribute("time", "3");
					request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
					return ;
				}
			}
			else if(role.equals("student")){
				if(!(studentId!=null&&studentPassword!=null)||!studentDao.login(studentId, studentPassword)){
					request.setAttribute("message", "ƒ˙Œ¥µ«¬º£¨«Îœ»µ«¬º°£2");
					request.setAttribute("page", "login.jsp");
					request.setAttribute("type", "warning");
					request.setAttribute("time", "3");
					request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
					return;
				}
			}
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
