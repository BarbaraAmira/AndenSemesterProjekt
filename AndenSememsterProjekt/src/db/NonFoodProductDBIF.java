package db;

import model.NonFoodProduct;

public interface NonFoodProductDBIF {

	NonFoodProduct findNonFoodProductById(int productId) throws DataAccessException;
	void updateNonFoodProduct(NonFoodProduct nfp) throws DataAccessException;

}
