package filters;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This init in this filter is activated in each startup of server.
 * The do filter is activated when user try to reach any html file in this project.
 * 
 * @author ashom
 *
 */
public class LogFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
        String emailName = "cafeteria-admin-email";
        String passwordName = "cafeteria-admin-password";
        String serverIp = "cafeteria-server-ip";

		boolean emailCookie = false;
		boolean passwordCookie = false;
		boolean serverCookie = false;

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		Cookie[] cookies = request.getCookies();
	   
		if (cookies != null){
			for(int i=0;i<cookies.length;i++){  
				if (cookies[i].getName().equals(emailName)){
					emailCookie = true;
				} else if (cookies[i].getName().equals(passwordName)){
					passwordCookie = true;
				}  else if (cookies[i].getName().equals(serverIp)){
					serverCookie = true;
				} 
				
				System.out.println("cookie[" + i + "] : " + cookies[i].getName() + " " + cookies[i].getValue());
			}  
		}

		String url = request.getRequestURI().toString();
		String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
		System.out.println("url: " + url);
		System.out.println("base url: " + baseURL);
		
		if (url.equals("/CafeteriaServer/index.html")
				|| url.equals("/index.html")
				){
			chain.doFilter(req, res);

		}else{
		
			if (emailCookie == false || passwordCookie == false || serverCookie == false){
			System.out.println("user is not logged");
			response.sendRedirect(baseURL+"index.html");
		}else{
			chain.doFilter(req, res);
		}
	}
		
	}
	public void init(FilterConfig config) throws ServletException {
		
//		//Get init parameter
//		String testParam = config.getInitParameter("test-param");
//		
//		//Print the init parameter
//		System.out.println("Test Param: " + testParam);
	}
	public void destroy() {
		//add code to release any resource
	}
}
