USE [DMA-CSD-S243_10632081]

-- Insert into product
INSERT INTO product (name, productType) VALUES
('Toothbrush', 'NonFood'),
('Shampoo', 'NonFood'),
('Apple', 'Food'),
('Bread', 'Food');


-- Insert into nonFoodProduct
INSERT INTO nonFoodProduct (nonFoodProductId, name, quantity, category) VALUES
(1, 'Toothbrush', 100, 'Hygiene'),
(2, 'Shampoo', 50, 'Hygiene');


-- Insert into foodProduct
INSERT INTO foodProduct (foodProductId, name, quantity, arrivalDate) VALUES
(3, 'Apple', null, null),
(4, 'Bread', null, null);


-- Insert into cage
INSERT INTO cage (cageId, foodProductId, nonFoodProductId) Values

(1, null, null),
(2, null, null);
