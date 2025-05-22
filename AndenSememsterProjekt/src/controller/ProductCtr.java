package controller;


import db.DataAccessException;  
import db.FoodProductDB;
import db.FoodProductDBIF;
import db.NonFoodProductDB;
import db.NonFoodProductDBIF;
import model.FoodProduct;
import model.NonFoodProduct;

// metode kald
public class ProductCtr {
	private FoodProductDBIF foodProductDB;
	private NonFoodProductDBIF nonFoodProductDB;
	
	public ProductCtr() throws DataAccessException {
		this.foodProductDB = new FoodProductDB();
		this.nonFoodProductDB = new NonFoodProductDB();
	}
	
	
	public FoodProduct findFoodProductById(int productId) throws DataAccessException {
		return foodProductDB.findFoodProductById(productId);
	}
	
	public void updateFoodProduct (FoodProduct fp) throws DataAccessException {
		foodProductDB.updateFoodProduct(fp);
	}
	
	public NonFoodProduct findNonProductById(int productId) throws DataAccessException {
		return nonFoodProductDB.findNonFoodProductById(productId);
	}
	
	public void updateNonProduct (NonFoodProduct nfp) throws DataAccessException {
		nonFoodProductDB.updateNonFoodProduct(nfp);
	}


}
