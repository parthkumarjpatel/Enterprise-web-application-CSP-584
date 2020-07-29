import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CartServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String src = request.getHeader("Referer");

		HttpSession session =  request.getSession(false);
		Cart cart =StartupServlet.cart;

		String requestedForm = request.getParameter("requestedForm");
		// Cart cart = StartupServlet.cart;
		if (requestedForm.equalsIgnoreCase("AddToCart")) {
			String addToCartProductID = request.getParameter("cartProduct");
			int productID = Integer.parseInt(addToCartProductID);
//			Product p = SaxParserReader.fetchProductById(productID);
			Product p = MySqlDataStoreUtilities.fetchProductById(productID);
			if (p != null) {
				cart.addToCart(p);

				if(session !=null) {
					session.setAttribute("cart",cart);
					cart = (Cart) session.getAttribute("cart");
				}
				response.sendRedirect("/AS4/CartWithCarousel?productId="+productID);
			}else{
				response.sendRedirect(src);
		}
		} else if (requestedForm.equalsIgnoreCase("RemoveFromCart")) {
			String removeFromCartProductID = request.getParameter("cartProduct");
			int productID = Integer.parseInt(removeFromCartProductID);
//			Product p = SaxParserReader.fetchProductById(productID);
			Product p = MySqlDataStoreUtilities.fetchProductById(productID);
			if (p != null) {
				cart.removeFromCart(p);
				if(session !=null) {
					session.setAttribute("cart",cart);
					cart = (Cart) session.getAttribute("cart");
				}
			}
			response.sendRedirect(src);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
