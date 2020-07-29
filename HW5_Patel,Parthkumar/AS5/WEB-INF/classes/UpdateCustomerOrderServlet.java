import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateCustomerOrderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session =  request.getSession(false);
		User currentUser = null;
		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
		}


		response.setContentType("text/html");
		String _dirPath = getServletContext().getRealPath("/");

		String orderDisplay = request.getParameter("order");
		String orderId = request.getParameter("orderid");

		String update = request.getParameter("update");
		String updateOrderId = request.getParameter("updateOrderId");

		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		if (session != null) {

			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}

		String content = getHTMLContent();
		String contentFooter = getHTMLFooter();
		out.print(content);
		if (orderDisplay != null) {
			if (orderId != null) {
				

//				ArrayList<Order> allOrder = SerializeDataStoreManager.readOrderInfo();
				//ArrayList<Order> allOrder = SerializeDataStoreManager.readOrderInfo();
				//Order ord = Order.filterByOrderID(allOrder, orderId);

				MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
				Order ord = msdsq.readOrderInfo(orderId);
				if(ord!=null) {
					out.print(getOrderView(ord));
				}else {
					out.println("<center><h6>No Such Order Available.</h6></center>");
				}
			}
			out.print("");
		} else if (updateOrderId != null) {
			String updateQuantity = request.getParameter("quantity");
			int quantity;
			int productId = Integer.parseInt(request.getParameter("productId"));

			if (updateQuantity != null) {
				try {
					quantity = Integer.parseInt(updateQuantity);

//					int resultCode = SerializeDataStoreManager.updateOrder(updateOrderId, productId, quantity);
					MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
					int resultcode = msdsq.updateOrder(updateOrderId,productId,quantity);
					

				}catch(Exception e) {
					quantity = 0;
				}

			}else {
				out.println("<center><h6>Enter Quantity First</h6></center>");
			}
		}
		out.print(contentFooter);
		if (session != null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));

	}

private String getOrderView(Order ord) {

		String _Header = "<div class='row'>"
				+ "<div class='box'>"
				+ "<div class='box-header'>"
				+ "<h3 class='box-title'>Order Details <sub> ** Update product quantity one at a time</sub> </h3>"
				+ "<a class='btn btn-danger pull-right' role='button'  href='/AS5/CancelOrderServlet?cancelOrder="+ord.getOrderId()+"'    >Cancel Order</a>"
				+ "</div>"
				+"<div class='row'>"
				+ "<div class='col-sm-3'>"
				+ "<label class='label'>Name:&nbsp </label>"
				+ ord.getName()
				+ "</div>"
				+ "<div class='col-sm-3'>"
				+ "<label class='label'>Address:&nbsp </label>"
				+ ord.getAddress()
				+ "</div>"
				+ "<div class='col-sm-3'>"
				+ "<label class='label'>Mobile Number:&nbsp </label>"
				+ ord.getMno()
				+ "</div>"

				+ "</div>"
				+ "<div class='box-body no-padding'>"
				+ "<table class='table table-striped center-table'>"
				+ "<tr>"
				+ "<th style='width: 10px'>#</th>"
				+ "<th>Product</th>"
				+ "<th>Quantity</th>"
				+ "<th>Delivery_Date</th>"
				+ "<th>Price</th>"
				+ "<th></th>"
				+ "</tr>";

		HashMap<Product,Integer> cart = ord.getCart().displayCart();
		String _Content = "";
		double total = 0.0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
			Product p = entry.getKey();
			int Quantity = entry.getValue();
			double subtotal = Quantity * p.getPrice();
			total +=subtotal;

				_Content +=""
						+ "<form action='/AS5/UpdateCustomerOrderServlet?update=true' method='post'>"
						+ "<tr>"
						+ "<input type='hidden' name='updateOrderId' value ='" + ord.getOrderId() + "'>"
						+ "<input type='hidden' name='productId' value ='" + p.getProductId() + "'>"
						+ "<td>" + p.getProductId() + ".</td>"
						+ "<td>"+p.getModel()+"</td>"
						+ "<td>"+"<input type='text' size='3' name='quantity' value='"+Quantity+"'>"+"</td>"
						+ "<td>"
						+ new Date(ord.getDeliveryDate()).toString()
						+ "</td>"
						+ "<td>$"
						+ subtotal
						+ "</td>"
						+ "<td>"
						+ "<button class='btn btn-success pull-right' type='Submit'>Update Order</button>"
						+ "</td>"
         				+ "</form>";


		}
		String _total = ""
				+ "<tr>"
				+ "<td colspan='5' class='total' >Total &nbsp:&nbsp $"
				+ total
				+ "</td>"
				+ "<td></td>"
				+ "</tr>";


		String _Footer = "</table>"
				+ "</div>"
				+ "</div>";

		return _Header + _Content + _total + _Footer;
	}
	private String getHTMLFooter() {

		return  "</section>"
				+ "</div>";
	}
	private String getHTMLContent() {
		return "<div id='body'>"
				+ "<section id='content'>"
				+ "<div class='row'>"
				+ "<div class='col-md-3'></div>"
				+ "<div class='col-md-6'>"
				+ "<div class='container'>"
				+ "<form class='form-signin' method='post' action='/AS5/UpdateCustomerOrderServlet?order=display'>"
				+ "<center><h2 class='form-signin-heading'>View Order</h2></center>"
				+ ""
				+ ""
				+ "<label for='orderid' class='sr-only'>Order ID</label>"
				 + "<input name='orderid' type='text' id='orderid' class='form-control'placeholder='Order ID' required='' autofocus=''>"
				 + ""
				 + ""
				+ "<button class='btn btn-lg btn-primary btn-block' type='submit'>View Order</button>"
				 + "</form>"
				 + "</div>"
				+ "</div>"
				+ "<div class='col-md-3'></div>"
				+ "</div>"

				;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
