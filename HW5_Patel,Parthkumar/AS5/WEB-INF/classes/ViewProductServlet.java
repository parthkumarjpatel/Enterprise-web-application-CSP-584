import java.io.*;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewProductServlet extends HttpServlet {
	MongoDBDataStoreUtilities mdsu;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		User currentUser = null;
		if (session != null) {
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
		if (productId != null) {
			out.print(getHTMLContent(productId));
			out.println(getReviewForm(productId));
			String reviewForm = request.getParameter("reviewform");
			mdsu = new MongoDBDataStoreUtilities();
			if (reviewForm != null) {
				storeDataToMongoDB(request, response, out, productId);
			}
			HashMap<String, ArrayList<Review>> reviews = mdsu.readReview(Integer.parseInt(productId));
			out.println(getReviews(reviews, productId));

			out.print(getCarousel(productId));

		} else {
			out.println("<center><h6>No Such Product Available.</h6></center>");
		}
		out.print(getContentFooter());
		if (session != null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));

	}

	private String getReviews(HashMap<String, ArrayList<Review>> reviews, String productId) {
		String _Header = "" + "<div class='container'>" + "<center>" + "<h2 class='form-signin-heading'>Reviews</h2>"
				+ "</center>" + "</div>";

		String _Content = "";
		ArrayList<Review> review = reviews.get(productId);
		if(review.size()==0) {
			_Content = "<center>No Review Available.</center>";
		}else {
			boolean isSet = true;
			for(Review r: review) {
				if(isSet) {
					_Content +=""
							+ "<div class='row'>"
							+ "<div class='col-md-3'>"
							+ "<label class='dark'>Retailer:</label>"
							+ r.getRetailername()
							+"  "
							+"</div>"
							+ "<div class='col-md-3'>"
							+ "<label class='dark'>City:</label>"
							+ r.getRetailerCity()
							+ "   "
							+"</div>"
							+ "<div class='col-md-3'>"
							+ "<label class='dark'>State:</label>"
							+ r.getRetailerState()
							+ "  "
							+"</div>"
							+ "<div class='col-md-3'>"
							+ "<label class='dark'>Zip:</label>"
							+ r.getRetailerzip()
							+"</div>"
							+"</div>"
							;
					isSet = false;
				}
				_Content +=""
						+ "<blockquote>"
						+ "<p>";
				
				int rating =r.getRating();
				for(int i=0;i<rating;i++) {
					_Content +=
							"<i class='fa fa-star' aria-hidden='true'></i>";
				}
				_Content += "</p>"
						+ "<p>"
						+ r.getReview()
						+ "</p>"
						+ "<footer class='user-info'>"
						+ r.getUsername()
						+" "
						+ "<cite title='Source Title'>"
						+ "("
						+ r.getGender()
						+") "
						+ "Age: "
						+ r.getAge()
						+" "
						+ r.getOccupation()
						+ " by occupation"
						+ "--"
						+ new Date(r.getTime()).toString()
						+ "</cite>"
						+ "</footer>"
						+ "</blockquote>";
			}
		}

		return _Header + _Content;
	}
	
	private void storeDataToMongoDB(HttpServletRequest request, HttpServletResponse response, PrintWriter out,
			String productId) {
		Product product = MySqlDataStoreUtilities.fetchProductById(Integer.parseInt(productId));

		int prodId = product.getProductId();
		String productName = product.getModel();
		double productPrice = product.getPrice();
		String type = product.getType();
		String manufacturer = product.getManufacturer();
		String manufacturerRebate="Yes";
		boolean productOnSale = (product.getQuantity() > 0 ? true : false);
		String retailername = request.getParameter("retailername");
		String retailerzip = request.getParameter("retailerzip");
		String retailercity = request.getParameter("retailercity");
		String retailerstate = request.getParameter("retailerstate");
		String username = request.getParameter("reviewername");
		String age = request.getParameter("reviewerage");
		String gender = request.getParameter("reviewergender");
		String occupation = request.getParameter("revieweroccupation");
		int rating = Integer.parseInt(request.getParameter("ratings"));
		String review = request.getParameter("review");
		long time = System.currentTimeMillis();
		
		// System.out.println(time);
		Review rev = new Review(prodId, productName, productPrice, type, manufacturer,manufacturerRebate, productOnSale ,retailername, retailerzip, retailercity, retailerstate,
			username, age, gender, occupation, rating, review, time);
		mdsu.storeReview(rev);
	}



	private String getReviewForm(String productId) {
		String _Content = "<form class='review-form' method='get' action='/AS5/ViewProductServlet'>"
				+ "<input type='hidden' name='productId' value='" 
				+ productId + "'>"
				+ "<input type='hidden' name='reviewform' value='true'>" 
				+ "<center>"
				+ "<h2 class='form-signin-heading'>Write Review About Product</h2>" 
				+ "</center>"
				+ "<div class='form-group'>" 
				+ "<div class='row'>" + "<div class='col-md-12'>"
				+ "<label class='dark'>Retailer Name:</label>"
				+ "<input type='text'  name='retailername'>" 
				+ "</div>" 
				+ "</div>"
				+ "<div class='row'>" 
				+ "<div class='col-md-4'>" 
				+ "<label class='dark'>Zip:</label>"
				+ "<input type='text'	name='retailerzip'>" 
				+ "</div>" 
				+ "<div class='col-md-4'>"
				+ "<label class='dark'>City:</label>" 
				+ "<input type='text' name='retailercity' >" 
				+ "</div>"
				+ "<div class='col-md-4'>" 
				+ "<label class='dark'>State:</label>"
				+ "<input type='text' name='retailerstate'>" 
				+ "</div>" 
				+ "</div>" 
				+ "</div>"
				+ "<div class='form-group'>" 
				+ "<div class='row'>" 
				+ "<div class='col-md-12'>"
				+ "<label class='dark'>Reviewer Name:</label> " 
				+ "<input type='text' name='reviewername'>" 
				+ "</div>"
				+ "</div>" + "<div class='row'>" 
				+ "<div class='col-md-4'>" 
				+ "<label class='dark'>Your Age:</label>"
				+ "<input type='text' name='reviewerage'>" 
				+ "</div>" 
				+ "<div class='col-md-4'>"
				+ "<label class='dark'>Gender:</label> " 
				+ "<input type='text' name='reviewergender'>" 
				+ "</div>"
				+ "<div class='col-md-4'>" 
				+ "<label class='dark'>Occupation:</label>"
				+ "<input type='text' name='revieweroccupation'>" 
				+ "</div>" + "</div>"
				+ "</div>"
				+ "<div class='form-group'>"
				+ "<label class='dark'>Comments:</label>"
				+ "<textarea name='review' placeholder='write your review for the product...'></textarea>" + "</div>"
				+ "<div class='form-group'>" + "<label class='dark'>Ratings:</label>" + " <select name='ratings'>"
				+ "<option value='5'>5</option>" + "<option value='4'>4</option>" + "<option value='3'>3</option>"
				+ "<option value='2'>2</option>" + "<option value='1'>1</option>" + "</select> "
				+ "<input type='submit' class='btn btn-primary pull-right' name='Submit Review'>" + "</div>"
				+ "</form>";
		return _Content;
	}

	private String getCarousel(String productId) {
		// HashMap<Integer,ArrayList<Accessory>> accessories =
		// SaxParserReader.findAccessoryByProductID(productId);
		HashMap<Integer, ArrayList<Accessory>> accessories = MySqlDataStoreUtilities
				.findAccessoryByProductID(productId);
		String _Heading = "" + "<div class='container'>" + "<div class='col-md-12'>"
				+ "<div id='carouselExampleIndicators' class='carousel slide' data-ride='carousel'>"
				+ "<div class='carousel-inner'>" + "";
		String _Content = "";

		for (Map.Entry<Integer, ArrayList<Accessory>> entry : accessories.entrySet()) {
			int count = entry.getKey();
			ArrayList<Accessory> accessory = entry.getValue();
			boolean firstFlag = true;
			for (int i = 0; i < count; i += 3) {
				if (i > count)
					break;

				_Content += "" + (i == 0 ? "<div class='carousel-item active'>" : "<div class='carousel-item'>")
						+ "<div class='row center-row'>";

				for (int j = i; j < (i + 3); j++) {
					if (j < count) {
						_Content += "" + "<div class='col-xs-4'>" + "<table>" + "<tr>" + "<td>"
								+ "<img class='img-thumbnail accessory-img-block' src='" + accessory.get(j).getImage()
								+ "' alt='IMG'>" + "</td>" + "</tr>" + "<tr>" + "<td>"
								+ "<label class='control-label'>Manufacturer: </label>"
								+ "<label class='info-label '>" + accessory.get(j).getManufacturer() + "</label>"
								+ "</td>" + "</tr>" + "<tr>" + "<td>"
								+ "<label class='control-label price'>Price: </label>"
								+ "<label class='info-label '> " + accessory.get(j).getPrice() + " </label>" + "</td>"
								+ "</tr>" + "<tr>" + "<td>"
								+ "<form action='/AS5/AddToCart' name='requestedForm' value='AddToCart' method='post'>"
								+ "<input type='hidden' name='requestedForm' value='AddToCart' >"
								+ "<button class='btn btn-outline-dark' name='cartProduct' value='"
								+ accessory.get(j).getProductId() + "'   type='submit'>Add to Cart</button>" + "</form>"
								+ "</td>" + "</tr>" + "</table>" + "</div>";
					} else {
						break;
					}
				}
				_Content += "" + "</div>" + "</div>";

			}
		}

		_Content += ""
				+ "<a class='carousel-control-prev' href='#carouselExampleIndicators'role='button' data-slide='prev'> "
				+ "<span class='fa fa-3x fa-chevron-left' aria-hidden='true'></span> "
				+ "<span class='sr-only'>Previous</span>" + "</a> "
				+ "<a class='carousel-control-next' href='#carouselExampleIndicators' 	role='button' data-slide='next'> "
				+ "<span class='fa fa-3x fa-chevron-right' aria-hidden='true'></span> "
				+ "<span class='sr-only'>Next</span>" + "</a>" + "</div></div>" + "</div>";

		return _Heading + _Content;
	}

	private String getContentFooter() {

		return "</section>" + "</div>";
	}

	private String getHTMLContent(String productId) {
		String _Heading = "" + "<div id='body'>" + "<section id='content'>" + "";

		// HashMap<String, Product> product = SaxParserReader.filterByID(productId);
		HashMap<String, Product> product = MySqlDataStoreUtilities.filterByID(productId);

		String _Product = "";
		for (Map.Entry<String, Product> entry : product.entrySet()) {

			Product p = entry.getValue();
				_Product += "<div class='row row-space'>" + "<div class='col-xs-3 img-block'>" + ""
						+ "<a href='#'><img src='" + p.getImage() + "' alt='" + p.getType()
						+ "' class='img-thumbnail'></a>" + "" + "</div>" + "<div class='col-xs-6 divide-block'>"
						+ "<h4 class=''>" 
						// + "<a href='#'>" 
						+ p.getModel() + "</a>" + "</h4>"
						+ "<label class='control-label'>Manufacturer: </label>" + "<label class='info-label '>"
						+ p.getManufacturer() + "</label>" + "<x class='space'></x> |"
						+ "<label class='control-label space'>Condition: </label>" + "<label class='info-label '>"
						+ p.getCondition() + "</label>" + "<x class='space'></x> |"
						+ "<label class='control-label space'>Discount: </label> " + "<label class='info-label'>"
						+ p.getDiscount() + "% " + "</label> <br>" + "<label "
						+ (p.getQuantity() > 0 ? "class='availibility'>Currently Available</label>"
								: "class='availibility red'>Currently Unavailable")
						+ "</div>"+

					// if (p.getQuantity()>0) {
					// _Product +=
						 "<div class='col-xs-1 space-price-block '>" + "<h3 class='price space'>$"
						+ p.getPrice() + "</h3>"
						+ "<form action='/AS5/AddToCart' name='requestedForm' value='AddToCart' method='post'>"
						+ "<table>" + "<tr>" + "<td>"

						+ "<input type='hidden' name='requestedForm' value='AddToCart' >"
						+ "<button class='btn btn-outline-dark' name='cartProduct' value='" + p.getProductId()
						+ "'   type='submit'>Add to Cart</button>" + "</td>" + "</tr>" + "<tr>" + "<td>"
						+ "<a class='btn btn-link' role='button' href='/AS5/ViewProductServlet?productId="
						+ p.getProductId() + "'>View Product</a>" + "</td>" + "</tr>" + "</table>" + "</form>" + "</div>"
						+ "</div>" + "<hr class='draw-line'>";
					// }
					// else{
					// _Product +=
					// 	 "<div class='col-xs-1 space-price-block '>" + "<h3 class='price space'>$"
					// 	+ p.getPrice() + "</h3>"
					// 	+ "<table>" 
					// 	+ "<tr>" + "<td>"
					// 	+ "<button class='btn btn-outline-danger' name='cartProduct'>Add to Cart</button>" 
					// 	+ "</td>" + "</tr>" 
					// 	+ "<tr>" + "<td>"
					// 	+ "<a class='btn btn-link' role='button' href='/AS5/ViewProductServlet?productId="
					// 	+ p.getProductId() + "'>View Product</a>" 
					// 	+ "</td>" + "</tr>" 
					// 	+ "</table>"
					// 	+ "</div>"
					// 	+"</div>" + "<hr class='draw-line'>";
					// }

		}

		return _Heading + _Product;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
