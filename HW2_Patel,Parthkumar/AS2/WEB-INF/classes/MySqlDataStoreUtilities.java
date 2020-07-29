
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.LinkedHashMap;

public class MySqlDataStoreUtilities {
	Connection con = null;
	String DRIVERS = "com.mysql.jdbc.Driver";
	String URL = "jdbc:mysql://localhost:3306/";
	String db = "bestdeal";
	String USER = "root";
	String PASSWORD = "root";
	PreparedStatement ps = null;
	public static HashMap<String, ArrayList<Product>> productData;
	private static ArrayList<Product> products;

	public MySqlDataStoreUtilities() {
	}

	public void prepareCatalog() {
		getConnection();
		productData = new HashMap<String, ArrayList<Product>>();
		products = new ArrayList<Product>();

		prepareProductCatalog();
		prepareAccessoryList();
		closeConnection();

	}

	private int getLastOrderID() {
		int result = -1;
		try {
			getConnection();
			String SQL = "select max(orderid) from customerorders";
			ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
			}
			rs.close();
			ps.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public LinkedHashMap<String, Integer> getMostSelled() {
		LinkedHashMap<String, Integer> p = new LinkedHashMap();
		getConnection();
		try {
			//String SQL = "SELECT count(orderquantity), zip, ordername FROM customerorders group by zip,ordername order by count(orderquantity) DESC";
			String SQL = "SELECT model, SUM(orderquantity) AS TotalItemsSold FROM productcatalog,customerorders where productcatalog.productid=customerorders.ordername group by ordername order by SUM(orderquantity) DESC limit 5";
			ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			int flag = 0;
			while (rs.next()) {
				String model = rs.getString(1);
				int count = rs.getInt(2);
				//String zip = rs.getString(2);
				//int id = rs.getInt(3);
				//Product pr = findProductById(id);
				p.put(model, count);
				flag++;
				if (flag > 6)
					break;
			}
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeConnection();
		return p;
	}

	public LinkedHashMap<String, Integer> getTopZip() {
		LinkedHashMap<String, Integer> p = new LinkedHashMap();
		getConnection();
		try {
			String SQL = "SELECT zip, SUM(orderquantity) AS TotalItemsSold FROM customerorders group by zip order by SUM(orderquantity) DESC limit 5";
			ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			int flag = 0;
			while (rs.next()) {
				String zip = rs.getString(1);
				int count = rs.getInt(2);
				//String zip = rs.getString(2);
				//int id = rs.getInt(3);
				//Product pr = findProductById(id);
				p.put(zip, count);
				flag++;
				if (flag > 6)
					break;
			}
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeConnection();
		return p;
	}

	public ArrayList<String> getAllProductNames(){
		ArrayList<String> productnameslist = new ArrayList<String>();
		getConnection();
		try {
			String SQL = "SELECT model FROM productcatalog";
			ps = con.prepareStatement(SQL);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				productnameslist.add(rs.getString("model"));
			}
			rs.close();
			ps.close();
		} 
		catch (SQLException e) 
		{
		}
		closeConnection();
		return productnameslist;
	}
	public int updateOrder(String updateOrderId, int productId, int quantity) {
		int result = 0;
		try {
			getConnection();
			String SQL = "update customerorders  set orderquantity=? where orderid=? and ordername=?";
			ps = con.prepareStatement(SQL);
			ps.setInt(1, quantity);
			ps.setInt(2, Integer.parseInt(updateOrderId));
			ps.setInt(3, productId);
			result = ps.executeUpdate();
			ps.close();
			closeConnection();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int cancelOrder(String cancelOrderId) {
		int result = -1;
		int orderId = Integer.parseInt(cancelOrderId);
		String SQL = "delete from customerorders  where orderid=?";
		getConnection();
		try {
			ps = con.prepareStatement(SQL);
			ps.setInt(1, orderId);
			result = ps.executeUpdate();
			ps.close();
			closeConnection();
			// System.out.println("Cancel Result: " + result);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Order readOrderInfo(String oId, User currentUser) {
		int orderId = Integer.parseInt(oId);
		Order order = null;
		String SQL = "select * from " + " customerorders as c Join productcatalog as p "
				+ " on c.ordername = p.productid " + " where c.orderId=? and c.username=?";

		try {
			getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, orderId);
			ps.setString(2, currentUser.getUserId());
			ResultSet rs = ps.executeQuery();
			Cart cart = new Cart();
			User user = new User();
			Product p = null;
			Accessory a = null;
			if (rs != null) {
				order = new Order();
				while (rs.next()) {
					order.setOrderId(rs.getInt(1) + "");
					user.setUserId(rs.getString(2));
					order.setUser(user);
					int quantity = rs.getInt(4);
					order.setAddress(rs.getString(6));
					order.setCc(rs.getString(7));

					Timestamp t = rs.getTimestamp(8);
					order.setDeliveryDate(t.getTime());
					order.setZip(rs.getString(9));
					int productid = rs.getInt(10);
					String model = rs.getString(11);
					double price = rs.getDouble(12);
					String image = rs.getString(13);
					String manufacturer = rs.getString(14);
					String condition = rs.getString(15);
					double discount = rs.getDouble(16);
					int qty = rs.getInt(17);
					String type = rs.getString(18);
					if (!type.equalsIgnoreCase("accessory")) {
						p = null;
						p = new Product();
						p.setProductId(productid);
						p.setModel(model);
						p.setPrice(price);
						p.setImage(image);
						p.setManufacturer(manufacturer);
						p.setCondition(condition);
						p.setDiscount(discount);
						p.setQuantity(quantity);
						p.setType(type);

						for (int i = 0; i < quantity; i++) {
							cart.addToCart(p);
						}

					} else {
						a = null;
						a = new Accessory();
						a.setProductId(productid);
						a.setModel(model);
						a.setPrice(price);
						a.setImage(image);
						a.setManufacturer(manufacturer);
						a.setType(type);
						for (int i = 0; i < quantity; i++) {
							cart.addToCart(a);
						}
					}
				}

			}
			order.setCart(cart);
			rs.close();
			ps.close();
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return order;
	}

	public Order readOrderInfo(String oId) {
		int orderId = Integer.parseInt(oId);
		Order order = new Order();
		String SQL = "select * from " + "customerorders as c Join productcatalog as p "
				+ " on c.ordername = p.productid " + " where c.orderId=?";

		try {
			getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			Cart cart = new Cart();
			User user = new User();
			Product p = null;
			Accessory a = null;
			while (rs.next()) {
				order.setOrderId(rs.getInt(1) + "");
				user.setUserId(rs.getString(2));
				order.setUser(user);
				int quantity = rs.getInt(4);
				order.setAddress(rs.getString(6));
				order.setCc(rs.getString(7));

				Timestamp t = rs.getTimestamp(8);
				order.setDeliveryDate(t.getTime());
				order.setZip(rs.getString(9));
				int productid = rs.getInt(10);
				String model = rs.getString(11);
				double price = rs.getDouble(12);
				String image = rs.getString(13);
				String manufacturer = rs.getString(14);
				String condition = rs.getString(15);
				double discount = rs.getDouble(16);
				int qty = rs.getInt(17);
				String type = rs.getString(18);
				if (!type.equalsIgnoreCase("accessory")) {
					p = null;
					p = new Product();
					p.setProductId(productid);
					p.setModel(model);
					p.setPrice(price);
					p.setImage(image);
					p.setManufacturer(manufacturer);
					p.setCondition(condition);
					p.setDiscount(discount);
					p.setQuantity(quantity);
					p.setType(type);

					for (int i = 0; i < quantity; i++) {
						cart.addToCart(p);
					}

				} else {
					a = null;
					a = new Accessory();
					a.setProductId(productid);
					a.setModel(model);
					a.setPrice(price);
					a.setImage(image);
					a.setManufacturer(manufacturer);
					a.setType(type);
					for (int i = 0; i < quantity; i++) {
						cart.addToCart(a);
					}
				}

			}
			order.setCart(cart);
			rs.close();
			ps.close();
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return order;
	}

	public int writeOrderInfo(Order o) {
		int index = getLastOrderID();
		try {
			getConnection();
			con.setAutoCommit(false);
			if (index >= 0) {
				index++;
				String SQL = "insert into customerorders values(?,?,?,?,?,?,?,?,?)";
				ps = con.prepareStatement(SQL);
				ps.setInt(1, index);
				ps.setString(2, o.getUser().getUserId());
				ps.setInt(5, 0);
				ps.setString(6, o.getAddress());
				ps.setString(7, o.getCc());
				ps.setString(9, o.getZip());
				ps.setTimestamp(8, new Timestamp(o.getDeliveryDate()));
				HashMap<Product, Integer> cart = o.getCart().displayCart();
				for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
					ps.setInt(3, entry.getKey().getProductId());
					ps.setInt(4, entry.getValue());
					int result = ps.executeUpdate();
					if (result <= 0) {
						con.rollback();
					}
				}
				con.commit();
				ps.close();
				closeConnection();
				return index++;
			} else {
				return -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int writeUserInfo(User user) {
		try {
			getConnection();
			String SQL = "insert into registration values(?,?,?,?,?,?,?)";
			ps = con.prepareStatement(SQL);
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFname());
			ps.setString(4, user.getLname());
			ps.setString(5, user.getEmail());
			ps.setString(6, user.getMno());
			ps.setInt(7, user.getRole());
			int result = ps.executeUpdate();
			ps.close();
			closeConnection();
			if (result > 0) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<User> readUserInfo() {
		ArrayList<User> users = new ArrayList<User>();
		try {
			getConnection();
			ResultSet rs = null;
			String SQL = "select * from registration";
			ps = con.prepareStatement(SQL);
			rs = ps.executeQuery();
			User u = null;
			while (rs.next()) {
				u = null;
				u = new User();
				u.setUserId(rs.getString(1));
				u.setPassword(rs.getString(2));
				u.setFname(rs.getString(3));
				u.setLname(rs.getString(4));
				u.setEmail(rs.getString(5));
				u.setMno(rs.getString(6));
				u.setRole(rs.getInt(7));
				users.add(u);
			}
			rs.close();
			ps.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}

	public static HashMap<String, ArrayList<Product>> retriveAllProducts() {
		final String key = "allProducts";
		productData.put(key, products);
		return productData;
	}

	private void prepareAccessoryList() {
		ResultSet rs = null;
		try {
			for (Product p : products) {
				if (!p.getType().equalsIgnoreCase("accessory")) {
					String SQL = "select a_id from product_accessory where p_id= ?";

					ps = con.prepareStatement(SQL);
					ps.setInt(1, p.getProductId());
					rs = ps.executeQuery();
					while (rs.next()) {
						int id = rs.getInt(1);
						p.getAccessories().add(id);
					}
				}
				if (rs != null)
					rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void prepareProductCatalog() {
		String SQL = "select * from productcatalog";
		try {
			ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			Product p = null;
			Accessory a = null;
			while (rs.next()) {
				int id = rs.getInt(1);
				String model = rs.getString(2);
				double price = rs.getDouble(3);
				String image = rs.getString(4);
				String manufacturer = rs.getString(5);
				String condition = rs.getString(6);
				double discount = rs.getDouble(7);
				int quantity = rs.getInt(8);
				String type = rs.getString(9);

				if (type.equalsIgnoreCase("accessory")) {
					a = null;
					a = new Accessory();
					a.setProductId(id);
					a.setModel(model);
					a.setPrice(price);
					a.setImage(image);
					a.setManufacturer(manufacturer);
					a.setType(type);
					products.add(a);
				} else {
					p = null;
					p = new Product();
					p.setProductId(id);
					p.setModel(model);
					p.setPrice(price);
					p.setImage(image);
					p.setManufacturer(manufacturer);
					p.setCondition(condition);
					p.setDiscount(discount);
					p.setQuantity(quantity);
					p.setType(type);
					products.add(p);
				}

			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getConnection() {
		try {								
			Class.forName(DRIVERS).newInstance();
			con = DriverManager.getConnection(URL + db, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			con.close();
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void firstTimeAddProduct(HashMap<String, ArrayList<Product>> hashMap) {
		getConnection();
		ArrayList<Product> products = null;
		for (Map.Entry<String, ArrayList<Product>> entry : hashMap.entrySet()) {
			products = entry.getValue();
		}
		// System.out.println("Size: " + products.size());
		if (products.size() != 0) {
			String SQL = "Insert into productcatalog values(?,?,?,?,?,?,?,?,?)";
			try {
				PreparedStatement ps = con.prepareStatement(SQL);
				int result;
				int count = 0;
				int size = products.size();
				for (Product p : products) {
					if (!p.getType().equalsIgnoreCase("accessory")) {
						ps.setInt(1, p.getProductId());
						ps.setString(2, p.getModel());
						ps.setDouble(3, p.getPrice());
						ps.setString(4, p.getImage());
						ps.setString(5, p.getManufacturer());
						ps.setString(6, p.getCondition());
						ps.setDouble(7, p.getDiscount());
						ps.setInt(8, p.getQuantity());
						ps.setString(9, p.getType());
					} else {
						ps.setInt(1, p.getProductId());
						ps.setString(2, p.getModel());
						ps.setDouble(3, p.getPrice());
						ps.setString(4, p.getImage());
						ps.setString(5, p.getManufacturer());
						ps.setString(6, null);
						ps.setDouble(7, 0.0);
						ps.setInt(8, 0);
						ps.setString(9, p.getType());
					}
					result = ps.executeUpdate();
				}
				ps.close();

				String SQL1 = "Insert into product_accessory values(?,?)";
				PreparedStatement ps1 = con.prepareStatement(SQL1);
				for (Product p : products) {
					if (p instanceof Product) {
						if (p.getAccessories().size() > 0) {
							ArrayList<Integer> accessories = p.getAccessories();
							// System.out.println(p.getProductId() + " " + accessories.size());
							for (Integer i : accessories) {
								ps1.setInt(1, p.getProductId());
								ps1.setInt(2, i);
								result = ps1.executeUpdate();
							}
						}
					}
				}
				ps1.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		closeConnection();
	}

	// modify this
	public int addProduct(Product p) {
		try {
			if (p != null) {
				getConnection();
				String SQL = "insert into productcatalog values (?,?,?,?,?,?,?,?,?)";
				ps = con.prepareStatement(SQL);
				ps.setInt(1, p.getProductId());
				ps.setString(2, p.getModel());
				ps.setDouble(3, p.getPrice());
				ps.setString(4, p.getImage());
				ps.setString(5, p.getManufacturer());
				ps.setString(6, p.getCondition());
				ps.setDouble(7, p.getDiscount());
				ps.setInt(8, p.getQuantity());
				ps.setString(9, p.getType());
				int result = ps.executeUpdate();
				if (result > 0) {
					MySqlDataStoreUtilities.products.add(p);
					ps.close();
					closeConnection();
					return 1;
				} else {
					return 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	// modify this
	public int updateProduct(Product p) {
		try {
			if (p != null) {
				getConnection();
				String SQL = "update productcatalog set model=?, price=?, image=?,  manufacturer=?,pcondition=?, discount=?, quantity=?, type=?  where productid=?";
				ps = con.prepareStatement(SQL);
				ps.setString(1, p.getModel());
				ps.setDouble(2, p.getPrice());
				ps.setString(3, p.getImage());
				ps.setString(4, p.getManufacturer());
				ps.setString(5, p.getCondition());
				ps.setDouble(6, p.getDiscount());
				ps.setInt(7, p.getQuantity());
				ps.setString(8, p.getType());
				ps.setInt(9, p.getProductId());
				int result = ps.executeUpdate();
				ps.close();
				closeConnection();
				if (result > 0) {
					for (Product p1 : MySqlDataStoreUtilities.products) {
						if (p1.getProductId() == p.getProductId()) {
							p1.updateProduct(p);
							break;
						}
					}
					return 1;
				} else {
					return 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// modify this
	public int deleteProduct(int id) {
		try {
			getConnection();
			String SQL = "Delete from productcatalog where productid=?";
			ps = con.prepareStatement(SQL);
			ps.setInt(1, id);
			int result = ps.executeUpdate();
			ps.close();
			closeConnection();
			if (result > 0) {
				ArrayList<Product> updatedCatalog = new ArrayList<Product>();
				for (Product p : products) {
					if (p.getProductId() != id) {
						updatedCatalog.add(p);
					}
				}
				MySqlDataStoreUtilities.products = updatedCatalog;
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static HashMap<String, ArrayList<Product>> filterByType(String type) {
		HashMap<String, ArrayList<Product>> resultData = new HashMap<String, ArrayList<Product>>();
		ArrayList<Product> filterProduct = new ArrayList<Product>();
		for (Product p : products) {
			if (p.getType().equalsIgnoreCase(type)) {
				filterProduct.add(p);
			}
		}
		resultData.put(type, filterProduct);

		return resultData;
	}

	public static Product fetchProductById(int id) {
		Product resultProdut = null;
		for (Product p : products) {
			if (p.getProductId() == id) {
				resultProdut = p;
				break;
			}
		}
		return resultProdut;
	}

	public static HashMap<String, ArrayList<Product>> filterByManufacturer(String type, String manufacturer) {
		HashMap<String, ArrayList<Product>> resultData = new HashMap<String, ArrayList<Product>>();
		ArrayList<Product> filterProduct = new ArrayList<Product>();
		for (Product p : products) {
			if (p.getManufacturer().equalsIgnoreCase(manufacturer) && p.getType().equalsIgnoreCase(type)) {
				filterProduct.add(p);
			}
		}
		resultData.put(manufacturer, filterProduct);
		return resultData;
	}

	public static HashMap<String, Product> filterByID(String productId) {
		HashMap<String, Product> result = new HashMap<String, Product>();
		Product p = MySqlDataStoreUtilities.findProductById(Integer.parseInt(productId));
		result.put(p.getType(), p);
		return result;
	}

	public static Product findProductById(int parseInt) {
		for (Product p : products) {
			if (p.getProductId() == parseInt) {
				return p;
			}
		}
		return null;
	}

	public static HashMap<Integer, ArrayList<Accessory>> findAccessoryByProductID(String productId) {
		HashMap<Integer, ArrayList<Accessory>> accessoryList = new HashMap<Integer, ArrayList<Accessory>>();

		Product p = MySqlDataStoreUtilities.findProductById(Integer.parseInt(productId));
		if (p != null) {
			ArrayList<Integer> accessoryListIds = p.getAccessories();

			HashMap<String, ArrayList<Product>> allAccessories = MySqlDataStoreUtilities.filterByType("accessory");
			ArrayList<Accessory> filterAccessory = new ArrayList<Accessory>();

			for (Map.Entry<String, ArrayList<Product>> entry : allAccessories.entrySet()) {
				for (Product p1 : entry.getValue()) {
					if (p1 instanceof Accessory && accessoryListIds.contains(p1.getProductId())) {
						filterAccessory.add((Accessory) p1);
					}
				}
			}
			accessoryList.put(filterAccessory.size(), filterAccessory);
		}
		return accessoryList;
	}

}
