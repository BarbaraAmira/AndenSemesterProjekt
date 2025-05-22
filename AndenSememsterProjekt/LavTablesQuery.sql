USE [DMA-CSD-S243_10632081]

CREATE TABLE product(
	productId int IDENTITY(1,1),
	name CHAR(30) UNIQUE NOT NULL,
	productType CHAR(30) NOT NULL,
	CONSTRAINT pk_product_id PRIMARY KEY(productId)
);
	
CREATE TABLE nonFoodProduct(
	nonFoodProductId int NOT NULL,
	name CHAR(30) UNIQUE NOT NULL,
	quantity int CHECK(quantity>0),
	category CHAR(30),
	CONSTRAINT pk_nonFoodProduct_id PRIMARY KEY (nonFoodProductId),
	CONSTRAINT fk_product_id FOREIGN KEY (nonFoodProductId) REFERENCES product(productId),
	CONSTRAINT fk_product_name FOREIGN KEY (name) REFERENCES product(name)
);

CREATE TABLE foodProduct(
	foodProductId int NOT NULL,
	name CHAR(30) UNIQUE NOT NULL,
	quantity int CHECK(quantity>0),
	arrivalDate int,
	CONSTRAINT pk_foodProduct_id PRIMARY KEY (foodProductId),
	CONSTRAINT fk_product_id2 FOREIGN KEY (foodProductId) REFERENCES product(productId),
	CONSTRAINT fk_product_name2 FOREIGN KEY (name) REFERENCES product(name)
);



CREATE TABLE cage(
	cageId int ,
	nonFoodProductId int,
	foodProductId int,

	CONSTRAINT pk_cage_cageId PRIMARY KEY(cageId),
	CONSTRAINT pk_cage_nonFoodProductId FOREIGN KEY (nonFoodProductId) REFERENCES nonFoodProduct(nonFoodProductId),
	CONSTRAINT pk_cage_foodProductId FOREIGN KEY (foodProductId) REFERENCES foodProduct (foodProductId)
);






