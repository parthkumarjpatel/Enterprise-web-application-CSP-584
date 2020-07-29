import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewProductServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session =  request.getSession(false);
		User currentUser = null;
		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
		}

		String productId = request.getParameter("productId");

		response.setContentType("text/html");
		String _dirPath = getServletContext().getRealPath("/");

		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		if (session != null) {

			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}
		if(productId!=null) {
			out.print(getHTMLContent(productId));

			out.print(getCarousel(productId));


		}else {
			out.println("<center><h6>No Such Product Available.</h6></center>");
		}
		out.print(getContentFooter());
		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));

	}

	private String getCarousel(String productId) {
		HashMap<Integer,ArrayList<Accessory>> accessories = SaxParserReader.findAccessoryByProductID(productId);
		String _Heading = ""
				+ "<div class='container'>"
				+ "<div class='col-md-12'>"
				+ "<div id='carouselExampleIndicators' class='carousel slide' data-ride='carousel'>"
				+ "<div class='carousel-inner'>"
				+ "";
		String _Content = "";

		for (Map.Entry<Integer, ArrayList<Accessory>> entry : accessories.entrySet()) {
			int count = entry.getKey();
			ArrayList<Accessory> accessory = entry.getValue();
			boolean firstFlag = true;
			for (int i = 0; i < count; i +=3) {
				if(i>count)
					break;

				_Content +=""
						+(i == 0 ? "<div class='carousel-item active'>" : "<div class='carousel-item'>")
						+ "<div class='row center-row'>";

				for (int j = i; j < (i + 3); j++) {
					if (j < count) {
						_Content += ""
								+ "<div class='col-xs-4'>"
								+ "<table>"
								+ "<tr>"
								+ "<td>"
								+ "<img class='img-thumbnail accessory-img-block' src='"+accessory.get(j).getImage()+"' alt='IMG'>"
								+ "</td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>"
								+ "<label class='control-label'>Manufacturer: </label>"
								+ "<label class='info-label '>" + accessory.get(j).getManufacturer() + "</label>"
								+ "</td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>"
								+ "<label class='control-label price'>Price: </label>"
								+ "<label class='info-label '> " + accessory.get(j).getPrice() + " </label>"
								+ "</td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>"
								+ "<form action='/AS1/AddToCart' name='requestedForm' value='AddToCart' method='post'>"
								+ "<input type='hidden' name='requestedForm' value='AddToCart' >"
								+ "<button class='btn btn-outline-dark' name='cartProduct' value='"+accessory.get(j).getProductId()+"'   type='submit'>Add to Cart</button>"
								+ "</form>"
								+ "</td>"
								+ "</tr>"
								+ "</table>"
								+ "</div>"
						;
					} else {
						break;
					}
				}
				_Content +=""
						+ "</div>"
						+ "</div>";

			}
		}

		_Content += ""
				+ "<a class='carousel-control-prev' href='#carouselExampleIndicators'role='button' data-slide='prev'> "
				+ "<span class='fa fa-3x fa-chevron-left' aria-hidden='true'></span> "
				+ "<span class='sr-only'>Previous</span>"
				+ "</a> "
				+ "<a class='carousel-control-next' href='#carouselExampleIndicators' 	role='button' data-slide='next'> "
				+ "<span class='fa fa-3x fa-chevron-right' aria-hidden='true'></span> "
				+ "<span class='sr-only'>Next</span>"
				+ "</a>"
				+ "</div></div>"
				+ "</div>";


		return _Heading + _Content;
	}

	private String getContentFooter() {

		return  "</section>" + "</div>";
	}

	private String getHTMLContent(String productId) {
		String _Heading = ""
				+ "<div id='body'>"
				+ "<section id='content'>" + "";

		HashMap<String, Product> product = SaxParserReader.filterByID(productId);

		String _Product = "";
		for (Map.Entry<String, Product> entry : product.entrySet()) {

			Product p = entry.getValue();

				_Product += "<div class='row row-space'>" + "<div class='col-xs-3 img-block'>" + ""
						+ "<a href='#'><img src='" + p.getImage() + "' alt='" + p.getType() + "' class='img-thumbnail'></a>"
						+ "" + "</div>"
						+ "<div class='col-xs-6 divide-block'>"
						+ "<h4 class=''>"
						//+ "<a href='#'>"
						+ p.getModel()
						+ "</a>"
						+ "</h4>"
						+ "<label class='control-label'>Manufacturer: </label>"
						+ "<label class='info-label '>" + p.getManufacturer() + "</label>"
						+ "<x class='space'></x> |"
						+ "<label class='control-label space'>Condition: </label>"
						+ "<label class='info-label '>" + p.getCondition() + "</label>"
						+ "<x class='space'></x> |"
						+ "<label class='control-label space'>Discount: </label> "
						+ "<label class='info-label'>" + p.getDiscount() + "% " + "</label> <br>"
						+ "<label "
						+ (p.getQuantity() > 0 ? "class='availibility'>Currently Available</label>"
								: "class='availibility'>Currently Unavailable")
						+ "</div>"
						+ "<div class='col-xs-1 space-price-block '>"
						+ "<h3 class='price space'>$"
						+ p.getPrice()
						+ "</h3>"
						+ "<form action='/AS1/AddToCart' name='requestedForm' value='AddToCart' method='post'>"
						+ "<input type='hidden' name='requestedForm' value='AddToCart' >"
						+ "<button class='btn btn-outline-dark' name='cartProduct' value='"+p.getProductId()+"'   type='submit'>Add to Cart</button>"
						+ "</form>"
						+ "</div>"
						+ ""
						+ "</div>"
						+ "<hr class='draw-line'>";

		}

		return _Heading + _Product;
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
