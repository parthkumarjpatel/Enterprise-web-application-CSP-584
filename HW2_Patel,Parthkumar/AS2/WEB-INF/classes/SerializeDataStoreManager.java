import java.io.*;
import java.util.*;

public class SerializeDataStoreManager {

	private static final String USER$INFO = "C:/apache-tomcat-7.0.34/webapps/AS1"+"/webdata/person.txt";
	private static final String ORDER$INFO = "C:/apache-tomcat-7.0.34/webapps/AS1"+"/webdata/order.txt";


	public static int updateOrder(String updateOrderId, int productId, int quantity) {
		ArrayList<Order> storedData = SerializeDataStoreManager.readOrderInfo();
		ArrayList<Order> newStoredData = new ArrayList<Order>();


		for (Order o : storedData) {
			if (o.getOrderId().equals(updateOrderId)) {
				HashMap<Product, Integer> cart = o.getCart().displayCart();
				for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
					Product p = entry.getKey();
					if (p.getProductId() == productId) {
						entry.setValue(quantity);

					}
				}
				o.setCart(o.getCart().updateCart(cart));
				newStoredData.add(o);

			} else {
				newStoredData.add(o);
			}
		}

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDER$INFO, false));
			if (oos != null) {
				if (newStoredData.size() > 0) {
					oos.writeObject(newStoredData);
					oos.flush();
				}
			}
			oos.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int cancelOrder(String cancelOrderId) {
		ArrayList<Order> storedData = SerializeDataStoreManager.readOrderInfo();
		ArrayList<Order> newStoredData = new ArrayList<Order>();
		long currentdate = System.currentTimeMillis();

		for (Order o : storedData) {
			long datediff = Math.abs(currentdate - o.getDeliveryDate()) / (24 * 60 * 60 * 1000);


			if (o.getOrderId().equals(cancelOrderId) && datediff >= 5) {

			} else {
				newStoredData.add(o);
			}
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDER$INFO, false));
			if (oos != null) {
				if (newStoredData.size() > 0) {
					oos.writeObject(newStoredData);
					oos.flush();
				}
			}
			oos.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static void writeOrderInfo(Order order) {
		ArrayList<Order> storedData = SerializeDataStoreManager.readOrderInfo();
		storedData.add(order);

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDER$INFO, false));
			if (oos != null) {
				oos.writeObject(storedData);
				oos.flush();
			}
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Order> readOrderInfo() {
		ArrayList<Order> orders = new ArrayList<Order>();
		File orderInfo = new File(ORDER$INFO);
		if (!orderInfo.exists()) {
			try {
				orderInfo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			FileInputStream fis = new FileInputStream(ORDER$INFO);
			if (fis.available() > 0) {
				ObjectInputStream ois = new ObjectInputStream(fis);
				if (ois != null) {
					orders = (ArrayList<Order>) ois.readObject();
				}
				ois.close();
			}
			fis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return orders;
	}

	public static void writeUserInfo(User user) {
		ArrayList<User> storedData = SerializeDataStoreManager.readUserInfo();
		storedData.add(user);

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER$INFO));
			if (oos != null) {
				oos.writeObject(storedData);
				oos.flush();
			}
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<User> readUserInfo() {
		ArrayList<User> users = new ArrayList<User>();
		File userInfo = new File(USER$INFO);
		if (!userInfo.exists()) {
			try {
				userInfo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			FileInputStream fis = new FileInputStream(userInfo);
			if (fis.available() > 0) {
				ObjectInputStream ois = new ObjectInputStream(fis);
				if (ois != null) {
					users = (ArrayList<User>) ois.readObject();
				}
				ois.close();
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return users;
	}
}
