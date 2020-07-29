import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.*;
public class MongoDBDataStoreUtilities {
	MongoClient mongo;
	String URL = "localhost";
	int port = 27017;
	DBCollection myReviews;
	DB db;

	public MongoDBDataStoreUtilities() {
		mongo = new MongoClient(URL, port);
		db = mongo.getDB("CustomerReviews");
		myReviews = db.getCollection("myReviews");
	}

	public HashMap<String, ArrayList<Review>> readReview(int productId) {
		HashMap<String, ArrayList<Review>> result = new HashMap<String, ArrayList<Review>>();
		ArrayList<Review> review = new ArrayList<Review>();
		if (mongo == null) {
			return null;
		}
		BasicDBObject obj = new BasicDBObject();
		obj.put("productId", productId);
		DBCursor cursor = myReviews.find(obj);
		Review r = null;
		while (cursor.hasNext()) {
			BasicDBObject data = (BasicDBObject) cursor.next();
			int pid = data.getInt("productId");
			String productName = data.getString("productName");
			double productPrice = data.getDouble("price");
			String type = data.getString("type");
			String manufacturer = data.getString("manufacturer");
			boolean productOnSale = data.getBoolean("productOnSale");
			String retailername = data.getString("retailerName");
			String retailerzip = data.getString("zip");
			String retailerCity = data.getString("city");
			String retailerState = data.getString("state");
			String username = data.getString("username");
			String age = data.getString("age");
			String gender = data.getString("gender");
			String occupation = data.getString("occupation");
			int rating = Integer.parseInt(data.getString("rating"));
			String rev = data.getString("review");
			long time = data.getLong("date");
			System.out.println(time);
			String manufacturerRebate = data.getString("manufacturerRebate");
			r = new Review(pid, productName, productPrice, type, manufacturer, manufacturerRebate,productOnSale, retailername, retailerzip,
					retailerCity, retailerState, username, age, gender, occupation, rating, rev, time);
			review.add(r);

		}
		result.put(productId + "", review);
		return result;
	}

	public LinkedHashMap<String, Integer> getMostReviewed() {
		LinkedHashMap<String, ArrayList<Review>> reviewHashmap = new LinkedHashMap<String, ArrayList<Review>>();

		DBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("_id", "$productName");
		groupFields.put("count", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);

		DBObject sort = new BasicDBObject();
		sort.put("count", -1);
		DBObject orderby = new BasicDBObject();
		orderby = new BasicDBObject("$sort", sort);

		DBObject limit = new BasicDBObject("$limit", 5);

		AggregationOutput output = myReviews.aggregate(group, orderby, limit);

		LinkedHashMap<String, Integer> productList = new LinkedHashMap<String, Integer>();

		for (final DBObject result : output.results()) {
			productList.put(result.get("_id").toString(), Integer.parseInt(result.get("count").toString()));
		}
		// System.out.println("MostReviewed");
		// System.out.println(productList.toString());

		return productList;
	}

	public LinkedHashMap<String, Float> getMostLiked() {
		LinkedHashMap<String, Float> results = new LinkedHashMap<String, Float>();
		DBObject groupfield = new BasicDBObject("_id", 0);
		groupfield.put("_id", "$productName");
		groupfield.put("average", new BasicDBObject("$avg", "$rating"));
		DBObject group = new BasicDBObject("$group", groupfield);

		DBObject sort = new BasicDBObject();
		sort.put("average", -1);
		DBObject orderby = new BasicDBObject("$sort", sort);

		DBObject limit = new BasicDBObject("$limit", 5);

		AggregationOutput output = myReviews.aggregate(group, orderby, limit);

		for (final DBObject result : output.results()) {
			results.put(result.get("_id").toString(), Float.parseFloat(result.get("average").toString()));
		}

		// System.out.println(results.toString());
		return results;
	}

	public int storeReview(Review r) {
		BasicDBObject doc = new BasicDBObject();
		doc.append("productId", r.getProductId()).append("productName", r.getProductName())
				.append("type",r.getType())
				.append("price", r.getProductPrice()).append("retailerName", r.getRetailername())
				.append("zip", r.getRetailerzip()).append("city", r.getRetailerCity())
				.append("state", r.getRetailerState()).append("productOnSale", r.isProductOnSale())
				.append("manufacturerRebate", r.getManufacturerRebate())
				.append("manufacturer", r.getManufacturer()).append("username", r.getUsername())
				.append("age", r.getAge()).append("gender", r.getGender()).append("occupation", r.getOccupation())
				.append("rating", r.getRating()).append("date", r.getTime()).append("review", r.getReview());

		if (mongo == null) {
			return -999;
		}
		myReviews.insert(doc);
		return 1;
	}

	public static void main(String[] args) {
		MongoDBDataStoreUtilities m = new MongoDBDataStoreUtilities();
		m.getMostReviewed();
		m.getTopZip();
	}

	public LinkedHashMap<String, Integer> getTopZip() {
		BasicDBObject query = new BasicDBObject();
		query.put("zip", new BasicDBObject("$ne", null));
		query.put("zip", new BasicDBObject("$ne", ""));

		DBObject match = new BasicDBObject("$match", query);

		DBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("_id", "$zip");
		groupFields.put("count", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);

		DBObject sort = new BasicDBObject();
		sort.put("count", -1);
		DBObject orderby = new BasicDBObject("$sort", sort);

		DBObject limit = new BasicDBObject("$limit", 5);

		AggregationOutput output = myReviews.aggregate(match, group, orderby, limit);

		LinkedHashMap<String, Integer> productList = new LinkedHashMap<String, Integer>();

		for (final DBObject result : output.results()) {
			productList.put(result.get("_id").toString(), Integer.parseInt(result.get("count").toString()));
		}
		// System.out.println(productList.toString());
		return productList;

	}

}
