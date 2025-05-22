package model;

abstract class Product {

	private String name;
	private int productId;
	private int quantity;

	
	public Product (String name, int productId, int quantity) {
		this.name = name;
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

}
