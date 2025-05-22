package db;

import java.sql.PreparedStatement;     
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cage;
import model.FoodProduct;
import model.NonFoodProduct;

//Implementation af preparedStatements, med SQL scripts
public class CageDB implements CageDBIF {
	private static final String findCageByIdQ = 
			"SELECT cageId from cage";
	private static final String updateCageQ = 
			"UPDATE cage set location = ? where cageId = ?";
	private static final String addFoodProductQ = "insert into cage foodProductId";
	private static final String addNonFoodProductQ = "insert into cage nonFoodProductId";
	private static final String getProductNameByIdQ = "SELECT name From product WHERE productId = ?";
	private static final String removeFoodProductFromCageQ = "UPDATE cage SET foodProductId = NULL WHERE cageId = ?";
	private static final String removeNonFoodProductFromCageQ = "UPDATE cage SET nonFoodProductId = NULL WHERE cageId = ?";
	private static final String getFoodProductIdFromCageQ = "SELECT foodProductId FROM cage WHERE cageId = ?";
	private static final String getNonFoodProductIdFromCageQ = "SELECT nonFoodProductId FROM cage WHERE cageId = ?";
	private static final String setFoodProductInCageQ = "UPDATE cage SET foodProductId = ? WHERE cageId = ?";
	private static final String setNonFoodProductInCageQ ="UPDATE cage SET nonFoodProductId = ? WHERE cageId = ?";
	private static final String getAllFoodProductsInCageQ =
		    "SELECT f.foodProductId, p.name, f.quantity, f.arrivalDate " +
		    "FROM cage c " +
		    "JOIN foodProduct f ON c.foodProductId = f.foodProductId " +
		    "JOIN product p ON f.foodProductId = p.productId " +
		    "WHERE c.cageId = ?";

		private static final String getAllNonFoodProductsInCageQ =
		    "SELECT n.nonFoodProductId, p.name, n.quantity, n.category " +
		    "FROM cage c " +
		    "JOIN nonFoodProduct n ON c.nonFoodProductId = n.nonFoodProductId " +
		    "JOIN product p ON n.nonFoodProductId = p.productId " +
		    "WHERE c.cageId = ?";
	
	
	private PreparedStatement findCageById, updateCage, addFoodProduct, addNonFoodProduct, getProductNameById, removeFoodProductFromCage,
	removeNonFoodProductFromCage, getFoodProductIdFromCage, getNonFoodProductIdFromCage, setFoodProductInCage, setNonFoodProductInCage,
	getAllFoodProductsInCage, getAllNonFoodProductsInCage;
	
	public CageDB() throws DataAccessException {
		try {
			findCageById = DBConnection.getInstance().getConnection()
					.prepareStatement(findCageByIdQ);
			updateCage = DBConnection.getInstance().getConnection()
					.prepareStatement(updateCageQ);
			addFoodProduct = DBConnection.getInstance().getConnection()
					.prepareStatement(addFoodProductQ);
			addNonFoodProduct = DBConnection.getInstance().getConnection()
					.prepareStatement(addNonFoodProductQ);
			getProductNameById = DBConnection.getInstance().getConnection()
					.prepareStatement(getProductNameByIdQ);
			removeFoodProductFromCage = DBConnection.getInstance().getConnection()
					.prepareStatement(removeFoodProductFromCageQ);
			removeNonFoodProductFromCage = DBConnection.getInstance().getConnection()
					.prepareStatement(removeNonFoodProductFromCageQ);
			getFoodProductIdFromCage = DBConnection.getInstance().getConnection()
					.prepareStatement(getFoodProductIdFromCageQ);
			getNonFoodProductIdFromCage = DBConnection.getInstance().getConnection()
					.prepareStatement(getNonFoodProductIdFromCageQ);
			setFoodProductInCage = DBConnection.getInstance().getConnection()
					.prepareStatement(setFoodProductInCageQ);
			setNonFoodProductInCage = DBConnection.getInstance().getConnection()
					.prepareStatement(setNonFoodProductInCageQ);
			getAllFoodProductsInCage = DBConnection.getInstance().getConnection()
						.prepareStatement(getAllFoodProductsInCageQ);
			getAllNonFoodProductsInCage = DBConnection.getInstance().getConnection()
						.prepareStatement(getAllNonFoodProductsInCageQ);
				
			
			
			
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not prepare statement");
		}
		
	}
	//Metode til at finde et bur med cageId
	@Override
	public Cage findCageById(int cageId) throws DataAccessException{
		try {
			
			findCageById.setInt(1, cageId);
			ResultSet rs = findCageById.executeQuery();
			Cage c = null;
			if(rs.next()) {
				c = buildObject(rs);
			}
			return c;
		} catch (SQLException e) {
			throw new DataAccessException(e , "Could not find by id = " + cageId);
		}
	}
	
	//Metode til at opdatere informationerne i et bur
	@Override
	public void updateCage(Cage c) throws DataAccessException {
		final int cageId = c.getCageId();
		final String location = c.getLocation();
		try {
			updateCage.setInt(1, cageId);
			updateCage.setString(2, location);
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not update cage where cageId = " + cageId);
		}
	}
	
	//Metode til at tilføje et FoodProduct objekt til et bur
	@Override
	public void addFoodProduct(Cage c, FoodProduct fp) throws DataAccessException {
		int cageId = c.getCageId();
		int foodProductId = fp.getProductId();
		int quantity = fp.getQuantity();
		int arrivalDate = fp.getArrivalDate();
		try {
			addFoodProduct.setInt(1, cageId);
			addFoodProduct.setInt(2, foodProductId);
			addFoodProduct.setInt(3, quantity);
			addFoodProduct.setInt(4, arrivalDate);
			
			addFoodProduct.execute();
		}
		catch(SQLException e) {
			throw new DataAccessException(e, "Could not add food product to cage with ID " + cageId);
		}
	}
	
	//Metode til at tilføje et NonFoodProduct til et bur
	@Override
	public void addNonFoodProduct(Cage c, NonFoodProduct nfp) throws DataAccessException {
		int cageId = c.getCageId();
		int nonFoodProductId = nfp.getProductId();
		int quantity = nfp.getQuantity();
		String category = nfp.getCategory();
		try {
			addNonFoodProduct.setInt(1, cageId);
			addNonFoodProduct.setInt(2, nonFoodProductId);
			addNonFoodProduct.setInt(3, quantity);
			addNonFoodProduct.setString(4, category);
			
			addNonFoodProduct.execute();
		}
		catch(SQLException e) {
			throw new DataAccessException(e, "Could not add non food product to cage with ID" + cageId);
		}
	}
	
	//Metode til at få alle bur id. Bliver brugt i GUI for at kunne vælge et bur 
	@Override
	public List<String> getAllCageIds() throws DataAccessException{
		List<String> cageIds = new ArrayList<>();
		try {
			ResultSet rs = findCageById.executeQuery();
			while (rs.next()) {
				cageIds.add(String.valueOf(rs.getInt("cageId")));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not retrieve cage Ids");
		}
		return cageIds;
	}
	
	//Metode til at få et navn på et produkt fra productId. Bliver brug i GUI, når man søger efter en vare bliver navnet automatisk udflydt
	@Override
	public String getProductNameById(int productId) throws DataAccessException {
	    try {
	        getProductNameById.setInt(1, productId);
	        ResultSet rs = getProductNameById.executeQuery();
	        if (rs.next()) {
	            return rs.getString("name");
	        } else {
	            throw new DataAccessException(null, "Product ID not found: " + productId);
	        }

	    } catch (SQLException e) {
	        throw new DataAccessException(e, "Failed to fetch product name");
	    }
	}
	
	// Metode til at fjerne et product fra et bur
	@Override
	public void removeProductFromCage(int cageId, String productType) throws DataAccessException {
	    try {
	        if (productType.equalsIgnoreCase("food")) {
	            removeFoodProductFromCage.setInt(1, cageId);
	            removeFoodProductFromCage.executeUpdate();
	        } else if (productType.equalsIgnoreCase("nonfood")) {
	            removeNonFoodProductFromCage.setInt(1, cageId);
	            removeNonFoodProductFromCage.executeUpdate();
	        } else {
	            throw new DataAccessException(null, "Invalid product type");
	        }
	    } catch (SQLException e) {
	        throw new DataAccessException(e, "Failed to remove product from cage");
	    }
	}
	
	//Metode til at kunne flytte et product fra et bur til et andet et
	@Override
	public void moveProductToAnotherCage(int fromCageId, int toCageId, String productType) throws DataAccessException {
	    try {
	        int productId;

	        if (productType.equalsIgnoreCase("food")) {
	            getFoodProductIdFromCage.setInt(1, fromCageId);
	            ResultSet rs = getFoodProductIdFromCage.executeQuery();
	            if (rs.next()) {
	                productId = rs.getInt("foodProductId");

	                setFoodProductInCage.setInt(1, productId);
	                setFoodProductInCage.setInt(2, toCageId);
	                setFoodProductInCage.executeUpdate();

	                removeFoodProductFromCage.setInt(1, fromCageId);
	                removeFoodProductFromCage.executeUpdate();
	            } else {
	                throw new DataAccessException(null, "No food product found in source cage.");
	            }

	        } else if (productType.equalsIgnoreCase("nonfood")) {
	            getNonFoodProductIdFromCage.setInt(1, fromCageId);
	            ResultSet rs = getNonFoodProductIdFromCage.executeQuery();
	            if (rs.next()) {
	                productId = rs.getInt("nonFoodProductId");

	                setNonFoodProductInCage.setInt(1, productId);
	                setNonFoodProductInCage.setInt(2, toCageId);
	                setNonFoodProductInCage.executeUpdate();

	                removeNonFoodProductFromCage.setInt(1, fromCageId);
	                removeNonFoodProductFromCage.executeUpdate();
	            } else {
	                throw new DataAccessException(null, "No non-food product found in source cage.");
	            }

	        } else {
	            throw new DataAccessException(null, "Invalid product type: " + productType);
	        }

	    } catch (SQLException e) {
	        throw new DataAccessException(e, "Failed to move product between cages");
	    }
	}
	
	//Metode til at få en liste af alle mad produkter i et bur. Brugt i GUI til at vise en liste 
	@Override
	public List<FoodProduct> getFoodProductsInCage(int cageId) throws DataAccessException {
	    List<FoodProduct> foodProducts = new ArrayList<>();
	    try {
	        getAllFoodProductsInCage.setInt(1, cageId);
	        try (ResultSet rs = getAllFoodProductsInCage.executeQuery()) {
	            while (rs.next()) {
	                String name = rs.getString("name").trim();
	            	int productId = rs.getInt("foodProductId");
	                int quantity = rs.getInt("quantity");
	                int arrivalDate = rs.getInt("arrivalDate");

	                foodProducts.add(new FoodProduct(name, productId, quantity, arrivalDate));
	            }
	        }
	    } catch (SQLException e) {
	        throw new DataAccessException(e, "Could not list all food products");
	    }
	    return foodProducts;
	}
	
	//Metode til at få en liste af alle ikke mad produkter i et bur. Brugt i GUI til at vise en liste 
	@Override
	public List<NonFoodProduct> getNonFoodProductsInCage(int cageId) throws DataAccessException {
	    List<NonFoodProduct> nonFoodProducts = new ArrayList<>();
	    try {
	        getAllNonFoodProductsInCage.setInt(1, cageId);
	        try (ResultSet rs = getAllNonFoodProductsInCage.executeQuery()) {
	            while (rs.next()) {
	            	String name = rs.getString("name").trim();
	            	int productId = rs.getInt("nonFoodProductId");
	                int quantity = rs.getInt("quantity");
	                String category = rs.getString("category").trim();

	                nonFoodProducts.add(new NonFoodProduct(name, productId, quantity, category));
	            }
	        }
	    } catch (SQLException e) {
	       throw new DataAccessException(e, "Could not list all nonFood products");
	    }
	    return nonFoodProducts;
	}
	
	
	
	
	
	
	
	// buildObject
	private Cage buildObject(ResultSet rs) throws SQLException {
		Cage p = new Cage(
				rs.getInt("cageId"),
				rs.getString("location")
				);
		return p;
	}
	
	private List<Cage> buildObjects(ResultSet rs) throws SQLException {
		List<Cage> res = new ArrayList<>();
		while(rs.next()) {
			res.add(buildObject(rs));
		}
		return res;
	}
}
