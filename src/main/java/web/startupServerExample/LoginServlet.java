package web.startupServerExample;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class web.server.LoginServlet
 */
@WebServlet("/web.server.LoginServlet")
public class LoginServlet extends HttpServlet implements Runnable {
	private final static Logger LOGGER = Logger.getLogger("global");
	private final static long serialVersionUID = 1L;
	static int count = 0;
	static String cashedName = null;
	static String sessionName = null;
	public void run() {
        class Logger extends TimerTask {
            public void run() {
                LOGGER.log(Level.INFO, String.valueOf(count));
            }
        }
// And From your main() method or any other method
        Timer timer = new Timer();
        timer.schedule(new Logger(), 0, 5000);//5 sec
    }
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		count++;
		makeSession(request, "POST");
		// get request parameters for userID and password
		String user = request.getParameter("user");

//		if (user.equals("Pankaj")) {
		if (sessionName != null) {
			request.getSession().setAttribute("sessionName", sessionName);
			response.sendRedirect("LoginSuccess.jsp");
			//response.sendRedirect("LoginSuccess.jsp");
		} else {
			if (cashedName == null) {
				if (user.isEmpty()) {
					request.getSession().setAttribute("mySecretMessage", count);
					response.getWriter().println("<font color=red>User name is wrong.</font>");
					getServletContext().getRequestDispatcher("/login.jsp").include(request, response);
				} else {
					cashedName = user;
					System.out.println("Ожидание авторизации..");
					sessionName = cashedName;
					request.getSession().setAttribute("sessionName", sessionName);
					response.sendRedirect("LoginSuccess.jsp");
				}
			} else {
				sessionName = cashedName;
				request.getSession().setAttribute("sessionName", sessionName);
				response.sendRedirect("LoginSuccess.jsp");
			}
//			response.sendRedirect("/login");
//			request.getRequestDispatcher("/login").forward(request,response);
		}
	}

	private void makeSession(HttpServletRequest request, String prefix) {
		try
		{
			HttpSession httpSession = request.getSession();
			if (httpSession.isNew())
			{
				System.out.printf(prefix + " - New Session: %s%n", httpSession.getId());
			}
			else
			{
				System.out.printf(prefix + " - Old Session: %s%n", httpSession.getId());
			}
		}
		catch (IllegalStateException ex)
		{
			System.out.println(prefix + " - Exception!" + ex);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		count++;
		makeSession(request, "GET");
		request.getSession().setAttribute("mySecretMessage", count);
		if (sessionName == null) {
			if (cashedName == null) {
				request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("cashedName", cashedName);
				response.sendRedirect("WaitAuth.jsp");
				System.out.println("Ожидаем ввода имени..");
			}
		} else {
			request.getSession().setAttribute("sessionName", sessionName);
			response.sendRedirect("LoginSuccess.jsp");
		}
	}
}