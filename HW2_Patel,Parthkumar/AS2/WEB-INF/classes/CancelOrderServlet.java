import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class CancelOrderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(false);
		User currentUser = null;
		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
		}

		String src = request.getHeader("Referer");
		String cancelOrderId = request.getParameter("cancelOrder");
		response.setContentType("text/html");
		String _dirPath = getServletContext().getRealPath("/");

		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		if (session != null) {
			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}
		if (cancelOrderId != null) {
//			int returncode = SerializeDataStoreManager.cancelOrder(cancelOrderId);
			MySqlDataStoreUtilities msdsu = new MySqlDataStoreUtilities();
			int returncode = msdsu.cancelOrder(cancelOrderId);
			if (returncode > 0) {
				out.println(getHTMLContent("Order cancelled Successfully"));
			}else {
				out.print(getHTMLContent("Order is not cancelled Successfully"));
			}

		} else {
			response.sendRedirect(src);
		}
		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

	private String getHTMLContent(String msg) {
		String content =  "<div id='body'>"
										+ "<section id='content'>"
										+ "<h1>" + msg + "</h1>"
										+ "</section>"
										+ "</div>";

		return content;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
