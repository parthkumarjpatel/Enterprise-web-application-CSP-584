import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ValidateUser extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uname = request.getParameter("uname");
		String pwd = request.getParameter("pwd");
		int role = Integer.parseInt(request.getParameter("role"));
		User currentUser = null;
		ArrayList<User> users = SerializeDataStoreManager.readUserInfo();
		boolean isUserFound = false;
		for (User user : users) {
			if (user.getUserId().equalsIgnoreCase(uname) && user.getPassword().equals(pwd) && user.getRole() == role) {
				isUserFound = true;
				currentUser = user;
				break;
			}
		}
		response.setContentType("text/html");
		String _dirPath = getServletContext().getRealPath("/");

		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		out.print(utility.printHtml(_dirPath + "Header.html"));

		if (isUserFound) {
			HttpSession session  = request.getSession();
			session.setAttribute("user", currentUser);
			response.sendRedirect("/AS1/");


			out.print("<div id='body'>" + "<section id='content'>" + "<center><h1>user Logged in....</h1></center>" + "</section>"
					+ "</div>");
		} else {
			out.print("<div id='body'>" + "<section id='content'>" + "<center><h1>user not found....</h1></center>" + "</section>"
					+ "</div>");
		}
		out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		out.print(utility.printHtml(_dirPath + "Footer.html"));

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
