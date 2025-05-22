package test;

import static org.junit.jupiter.api.Assertions.*; 

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.CageCtr;
import db.DataAccessException;

class FindProductByIdTest {

	static CageCtr ac;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ac = new CageCtr();
	}



	@Test
	void test() throws DataAccessException {
		//Arrange
		int cageId = 2;
		
		// Act
		ac.findCageById(cageId);
		
		//Assert
		assertNotNull(cageId);
	
	}
	
		

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	
	
}
