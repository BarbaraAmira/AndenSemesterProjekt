package db;

import model.FoodProduct;

public interface FoodProductDBIF {

	FoodProduct findFoodProductById(int productId) throws DataAccessException;
	void updateFoodProduct(FoodProduct fp) throws DataAccessException;


}
