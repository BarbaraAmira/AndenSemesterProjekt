package model;
import java.util.ArrayList;
import java.util.List;


public class Cage {
	private int cageId;
	private String location;
	private ArrayList<FoodProduct> foodProducts;
	private ArrayList<NonFoodProduct> nonFoodProducts;
	
	public Cage(int cageId, String location){
		this.cageId = cageId;
		this.location = location;
		foodProducts = new ArrayList<>();
		nonFoodProducts = new ArrayList<>();
		
	}
	
	public int getCageId() {
		return cageId;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setCageId(int cageId) {
		this.cageId = cageId;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void addFoodItem(FoodProduct foodProduct) {
		this.foodProducts.add(foodProduct);
	}
	
	public void addNonFoodItem(NonFoodProduct nonFoodProduct) {
		this.nonFoodProducts.add(nonFoodProduct);
	}
	
	public void removeFoodItem(FoodProduct foodProduct) {
		this.foodProducts.remove(foodProduct);
	}
	
	public void removeNonFoodItem(NonFoodProduct nonFoodProduct) {
		this.nonFoodProducts.remove(nonFoodProduct);
	}
	
	public boolean findFoodProduct(FoodProduct foodProduct) {
		boolean found = false;
		int i = 0;
		while(i < foodProducts.size()) {
			if (foodProducts.get(i).getProductId() == (foodProduct.getProductId())) {
				found = true;
				break;
			}
			i++;
		}
		return found;
	}
	
	
	public boolean findNonFoodProduct(NonFoodProduct nonfoodProduct) {
		boolean found = false;
		int i = 0;
		while(i < nonFoodProducts.size()) {
			if (nonFoodProducts.get(i).getProductId() == (nonfoodProduct.getProductId())) {
				found = true;
				break;
			}
			i++;
		}
		return found;
	}
	
	public int getFoodProductSize() {
		return foodProducts.size();
	}
	
	public int getNonFoodProductSize() {
		return nonFoodProducts.size();
	}
	
	public List<FoodProduct> getFoodProducts() {
	    return foodProducts;
	}

	public List<NonFoodProduct> getNonFoodProducts() {
	    return nonFoodProducts;
	}
	
}
