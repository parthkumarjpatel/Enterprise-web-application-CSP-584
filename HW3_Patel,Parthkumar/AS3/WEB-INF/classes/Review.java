
public class Review {
	int productId;
	String productName;
	double productPrice;
	String type;
	String manufacturer;
	String manufacturerRebate;
	boolean productOnSale;
	String retailername;
	String retailerzip;
	String retailerCity;
	String retailerState;
	String username;
	String age;
	String gender;
	String occupation;
	int rating;
	String review;
	long time;

	public Review(int productId, String productName, double productPrice, String type, String manufacturer, String manufacturerRebate, boolean productOnSale,
			String retailername, String retailerzip, String retailerCity, String retailerState, String username,
			String age, String gender, String occupation, int rating, String review, long time) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.type = type;
		this.manufacturer = manufacturer;
		this.manufacturerRebate = manufacturerRebate;
		this.productOnSale = productOnSale;
		this.retailername = retailername;
		this.retailerzip = retailerzip;
		this.retailerCity = retailerCity;
		this.retailerState = retailerState;
		this.username = username;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
		this.rating = rating;
		this.review = review;
		this.time = time;
	}

	@Override
	public String toString() {
		return "Review [productId=" + productId + ", productModelName=" + productName + ", productPrice=" + productPrice
				+ ", type=" + type
				+ ", manufacturer=" + manufacturer + ", manufacturerRebate=" + manufacturerRebate + ", productOnSale=" + productOnSale + ", retailername="
				+ retailername + ", retailerzip=" + retailerzip + ", retailerCity=" + retailerCity + ", retailerState="
				+ retailerState + ", username=" + username + ", age=" + age + ", gender=" + gender + ", occupation="
				+ occupation + ", rating=" + rating + ", review=" + review + ", time=" + time + "]";
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getManufacturerRebate() {
		return manufacturerRebate;
	}

	public void setManufacturerRebate(String manufacturerRebate) {
		this.manufacturerRebate = "Yes";
	}

	public boolean isProductOnSale() {
		return productOnSale;
	}

	public void setProductOnSale(boolean productOnSale) {
		this.productOnSale = productOnSale;
	}

	public String getRetailername() {
		return retailername;
	}

	public void setRetailername(String retailername) {
		this.retailername = retailername;
	}

	public String getRetailerzip() {
		return retailerzip;
	}

	public void setRetailerzip(String retailerzip) {
		this.retailerzip = retailerzip;
	}

	public String getRetailerCity() {
		return retailerCity;
	}

	public void setRetailerCity(String retailerCity) {
		this.retailerCity = retailerCity;
	}

	public String getRetailerState() {
		return retailerState;
	}

	public void setRetailerState(String retailerState) {
		this.retailerState = retailerState;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
