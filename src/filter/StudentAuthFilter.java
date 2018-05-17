package filter;

import java.io.IOException;
import javax.servlet.DispatcherType;
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

import dao.StudentDao;
import servlet.StudentLogin;


/**
 * 学生认证过滤器
 */
//@WebFilter(
//		dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.FORWARD }
//					, 
//		urlPatterns = { 
//				"/StudentAuthFilter", 
//				"/StudentCenter.jsp"
//		})
public class StudentAuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public StudentAuthFilter() {
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
		StudentDao studentDao=new StudentDao();
		HttpServletRequest httpServletRequest=((HttpServletRequest)request);
		Cookie[] cookies=httpServletRequest.getCookies();
		HttpSession session=httpServletRequest.getSession();
		String studentId=null;
		String studentPassword=null;
		//session空就尝试读取cookie
		if(session.getAttribute(StudentLogin.STUDENT_NUMBER_KEY)==null){
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie=cookies[i];
				if(cookie.getName().equals(StudentLogin.STUDENT_NUMBER_KEY)){
					studentId=cookie.getValue();
					session.setAttribute(cookie.getName(), cookie.getValue());
				}
				if(cookie.getName().equals(StudentLogin.STUDENT_PASSWORD_KEY)){
					studentPassword=cookie.getValue();
					session.setAttribute(cookie.getName(), cookie.getValue());
				}
			}
			if(!(studentId!=null&&studentPassword!=null)||!studentDao.login(studentId, studentPassword)){
				request.setAttribute("message", "您未登录，请先登录。");
				request.setAttribute("page", "login.jsp");
				request.setAttribute("type", "warning");
				request.setAttribute("time", "3");
				request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
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
