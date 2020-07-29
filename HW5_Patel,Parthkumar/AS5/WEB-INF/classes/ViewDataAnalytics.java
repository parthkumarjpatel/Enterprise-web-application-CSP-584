import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.*;
public class ViewDataAnalytics extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		

		HttpSession session = request.getSession(false);
		User currentUser = null;
		if (session != null) {
			currentUser = (User) session.getAttribute("user");
		}

		response.setContentType("text/html");
		String _dirPath = getServletContext().getRealPath("/");

		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);

		if (session != null) {
			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}

		String _Heading = "" + "<div id='body'>" + "<section id='content'>" + "";
		String _content ="";

		MongoDBDataStoreUtilities mdsu = new MongoDBDataStoreUtilities();
		MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
		
		String filters[]=request.getParameterValues("querycheckbox");
		String productnamedropdown = request.getParameter("productnamedropdown");
		int price=0;
		if(request.getParameter("price")!="")
		{
			 price = Integer.parseInt(request.getParameter("price"));
		}	
		String compareprice = request.getParameter("compareprice");
		int reviewratedropdown=0;
		if(request.getParameter("reviewratedropdown")!=null)
		{
			 reviewratedropdown = Integer.parseInt(request.getParameter("reviewratedropdown"));
		}
		String comparerating = request.getParameter("comparerating");
		String city = request.getParameter("city");
		String groupbydropdown = request.getParameter("groupbydropdown");
		String countordetail = request.getParameter("countordetail");
		
		boolean filterbyprodname=false;
		boolean filterbyprodprice=false;
		boolean filterbyrate=false;
		boolean filterbycity =false;
		boolean filterbygroupby = false;
	
		// mdsu.getConnection();
		BasicDBObject query = new BasicDBObject();
		ArrayList<BasicDBObject> andquery = new ArrayList<BasicDBObject>();
		DBObject group = null;
		if(filters!=null)
		{
			for(int i=0;i<filters.length;i++)
			{
				System.out.println(filters[i]);
				switch(filters[i])
				{
				//PRODUCT NAME:
				case "productname":
					filterbyprodname=true;
					if(productnamedropdown.equals(""))
					{
						ArrayList<String> prodnamelist=msdsq.getAllProductNames();
						for(String pn : prodnamelist)
							andquery.add(new BasicDBObject("productName",pn));
					}
				
				//PRODUCT PRICE
				case "productprice":
					filterbyprodprice=true;
					if(compareprice==null)
					{
						
					}
					else if(compareprice.equals("priceequals"))
					{
						// System.out.println("inside price equal");
						andquery.add(new BasicDBObject("price", price));
					}
					else if(compareprice.equals("pricegreaterthan"))
					{
						andquery.add(new BasicDBObject("price", new BasicDBObject("$gt",price)));
					}
					else
					{
						andquery.add(new BasicDBObject("price", new BasicDBObject("$lt",price)));
					}
					break;
				// REVIEW RATE
				case "reviewrating":
					filterbyrate=true;
					if(comparerating==null)
					{
						if(countordetail==null)
						{
							
						}
						else if(countordetail.equals("wantcount"))
						{
							andquery.add(new BasicDBObject("$group", "productName"));
						}
					}
					else{
					if(comparerating.equals("rateequals"))
					{
						andquery.add(new BasicDBObject("rating", reviewratedropdown));
						
					}
					else
					{
						// System.out.println("inside prate gt");
						andquery.add(new BasicDBObject("rating", new BasicDBObject("$gt",reviewratedropdown)));
					}
					}
					break;
				// RETAILER CITY
				case "retailercity":
					filterbycity=true;
					andquery.add(new BasicDBObject("city", city));
					break;
				
				//GROUP BY
				case "groupby":
					filterbygroupby=true;
					if(countordetail.equals("wantcount"))
					{
						DBObject groupfield = new BasicDBObject("_id",0);
						groupfield.put("_id", groupbydropdown);
						groupfield.put("count", new BasicDBObject("$sum",1));
						group = new BasicDBObject("$group",groupfield);
					}
					
				}
			}
		}
	

		//output of DA1
		if(filterbyprodname && filterbyrate && productnamedropdown.equals("") && comparerating==null)
		{
			query.put("$or", andquery);
			DBCursor dbCursor = mdsu.myReviews.find(query);
			System.out.println(query.toString());
			System.out.println(andquery.toString());
			_content+=""
					+"<table>"
					+"<h2><center>Products and it's Ratings</center></h2>";
			while(dbCursor.hasNext())
			{	
				BasicDBObject bobj = (BasicDBObject) dbCursor.next();	
				_content+=""
						+"<tr>"
						+"<td>"
						+"<span style='font-weight:bold'>Product Name</span>"
						+"</td>"
						+"<td>"
						+"<span style='font-weight:bold'>"+bobj.getString("productName")+"</span>"
						+"</td>"
						+"</tr>"

						+"<tr>"
						+"<td>"
						+"Review Rate"
						+"</td>"
						+"<td>"
						+bobj.getInt("rating")
						+"</td>"
						+"</tr>"


						+"<tr>"
						+"<td>"
						+"Review"
						+"</td>"
						+"<td>"
						+bobj.getString("review")
						+"</td>"
						+"</tr>"
						+"<tr>"
						+"     "
						+"</tr>"
						+"<tr><td></td><td></td><tr>";
			}
					_content+=""+"</table>";
				
		}
		
		//output of DA 2,3,5
		else
		{
			query.put("$and", andquery);
			System.out.println(query.toString());
			System.out.println(andquery.toString());
			
			DBCursor dbCursor = mdsu.myReviews.find(query);


			_content+=""+"<table>"
					+"<h2><center>Reviews</center></h2>";

			while(dbCursor.hasNext())
			{
				// System.out.println("inside while");
				BasicDBObject bobj = (BasicDBObject) dbCursor.next();
				
				_content+=""
				+"<tr>"
				+"<td>"
				+"<span style='font-weight:bold'>Product Name</span>"
				// +"Product Name"
				+"</td>"
				+"<td>"
				+"<span style='font-weight:bold'>"+bobj.getString("productName")+"</span>"
				// +bobj.getString("productName")
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"Product Price"
				+"</td>"
				+"<td>"
				+bobj.getString("price")
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"City"
				+"</td>"
				+"<td>"
				+bobj.getString("city")
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"Review Rate"
				+"</td>"
				+"<td>"
				+bobj.getInt("rating")
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"Review Date"
				+"</td>"
				+"<td>"
				+ new Date(bobj.getLong("date")).toString()
				// + bobj.getLong("date").toString()
				// +new Date(bobj.getTime()).toString()
				//+bobj.getLong("date")
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"Review"
				+"</td>"
				+"<td>"
				+bobj.getString("review")
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"     "
				+"</tr>"
				+"<tr><td></td><td></td><tr>";
			}
			_content+=""+"</table>";

		}

		// out.println(getHTMLContent());
		out.println(_Heading+_content);

		out.println(getContentFooter());

		if (session != null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));

	}
	private String getContentFooter() {

		return "</section>" + "</div>";
	}
}
