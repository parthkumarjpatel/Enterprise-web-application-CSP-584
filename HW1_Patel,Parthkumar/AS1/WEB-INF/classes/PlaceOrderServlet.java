import java.io.*;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;

public class PlaceOrderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(false);
		User currentUser = null;
		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
		}


		String caller = request.getHeader("Referer");
		String place = request.getParameter("place");
		int orderSize = StartupServlet.cart.size();
		if (orderSize > 0 && place == null) {
			response.setContentType("text/html");
			String _dirPath = getServletContext().getRealPath("/");

			PrintWriter out = response.getWriter();
			Utilities utility = new Utilities(out);
			if (session != null) {

				out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
			} else {
				out.print(utility.printHtml(_dirPath + "Header.html"));
			}

			String content = getLoginHTML();
			out.print(content);

			if(session !=null) {
				out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
			}else {
				out.print(utility.printHtml(_dirPath + "LeftNav.html"));
			}

			out.print(utility.printHtml(_dirPath + "Footer.html"));
		} else if (orderSize == 0 && place == null) {
			response.sendRedirect(caller);
		}else {
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String mno = request.getParameter("mno");
			String cc = request.getParameter("cc");

			Order order = new Order();
			order.setName(name);
			order.setAddress(address);
			order.setMno(mno);
			order.setCc(cc);
			order.setCart(StartupServlet.cart);
			order.setOrderId(Order.generateUniqueOrderID());
			order.setDeliveryDate(Order.getDeliveryDate(System.currentTimeMillis()));
			order.setUser(currentUser);
			// Write Data TO File

			SerializeDataStoreManager.writeOrderInfo(order);
			StartupServlet.cart = null;
			StartupServlet.cart = new Cart();
			session.setAttribute("cart",StartupServlet.cart);
			response.setContentType("text/html");
			String _dirPath = getServletContext().getRealPath("/");

			PrintWriter out = response.getWriter();
			Utilities utility = new Utilities(out);
			if (session != null) {

				out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
			} else {
				out.print(utility.printHtml(_dirPath + "Header.html"));
			}


			out.print(
					"<div id='body'>" + "<section id='content'>"
							+ "<center>"
							+ "<h3>Order with orderID: &nbsp"+order.getOrderId()+" Placed   ....</h3>"
							+ "<h5>Delivery Date: "+new Date(order.getDeliveryDate()) +"</h5>"
							+ "</center>"
							+ "</section>" + "</div>");

			if(session !=null) {
				out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
			}else {
				out.print(utility.printHtml(_dirPath + "LeftNav.html"));
			}
			out.print(utility.printHtml(_dirPath + "Footer.html"));

		}

	}

	private String getLoginHTML() {
		return "<div id='body'>"
				+ "<section id='content'>"
				+ "<div class='row'>"
				+ "<div class='col-md-3'></div>"
				+ "<div class='col-md-6'>"
				+ "<div class='container'>"
				+ "<form class='form-signin' method='post' action='/AS1/PlaceOrderServlet?place=success'>"
				+ "<center><h2 class='form-signin-heading'>Place Your Order</h2></center>"
				+ ""
				+ ""
				+ "<label for='name' class='sr-only'>Name</label>"
				+ "<input name='name' type='text' id='name' class='form-control'placeholder='Full Name' required='' autofocus=''>"
				+ ""
				+ ""
				 + "<label for='address' class='sr-only'>Email address</label>"
				 + "<input name='address' type='text' id='address' class='form-control'placeholder='Address' required=''>"
				 + ""
				 + ""
				 + "<label for='mno' class='sr-only'>Mobile Number</label>"
				 + "<input name='mno' type='text' id='mno' class='form-control'placeholder='Mobile Number' required='' >"
				 + ""
				 + ""
				 + "<label for='cc' class='sr-only'>Credit Card</label>"
				 + "<input name='cc' type='text' id='cc' class='form-control'placeholder='Credit Card(xxxx-xxxx-xxxx-xxxx)' required=''>"
				 + ""
				 + ""
				 + "<button class='btn btn-lg btn-primary btn-block' type='submit'>Place Order</button>"
				 + "</form>"
				 + "</div>"
				+ "</div>"
				+ "<div class='col-md-3'></div>"
				+ "</div>"
				+ "</section>"
				+ "</div>";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
