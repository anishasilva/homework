/*
  Raymond Elward
  CSC453 Section 901
  Assignment 1
  4/4/2011
*/

DROP TABLE REQUEST;
DROP TABLE FULLORDER;
DROP TABLE CUSTOMER;
DROP TABLE PRODUCT;

--Creating the customer table

CREATE TABLE CUSTOMER
(
  CustomerID      NUMBER(3),
  Name            VARCHAR2(30),
  Address         VARCHAR2(20),
  
  CONSTRAINT PK_CUSTOMER_KEY 
        PRIMARY KEY (CustomerID)
  
);
-- populating the customer table
INSERT INTO CUSTOMER
      VALUES( 2, 'CASUAL FURANATURE', 'IRVING, TX');
      
INSERT INTO CUSTOMER
      VALUES( 6, 'MOUNTIAN GALLERY', 'DENVER, CO');
      

-- creating the FULLORDER table


CREATE TABLE FULLORDER
(
  OrderID         NUMBER(4),
  OrderDate       DATE,
  CustomerID      NUMBER(3),
  
  CONSTRAINT PK_FULLORDER_KEY
          PRIMARY KEY (OrderID),
  CONSTRAINT FK_toCustomer_KEY
          FOREIGN KEY (CustomerID)
          REFERENCES CUSTOMER(CustomerID)
);
--populating the fullorder table:
INSERT INTO FULLORDER VALUES( 2106, '19-NOV-10',2);
INSERT INTO FULLORDER VALUES( 2107, '21-NOV-10', 6);
INSERT INTO FULLORDER VALUES( 2108, '21-NOV-10', 6);

--creating the product table:
CREATE TABLE PRODUCT
(
  ProductID       NUMBER(2),
  Description     VARCHAR2(30),
  Finish          VARCHAR2(10),
  Price           NUMBER(5,2)
                  CHECK((Price >= 0.00) AND (Price <= 999.99)),
                  
  CONSTRAINT PK_PRODUCT_KEY
          PRIMARY KEY (ProductID)
                  
);
--populating the PRODUCT table:
INSERT INTO PRODUCT VALUES( 3, 'DINING TABLE', 'ASH', 600);
INSERT INTO PRODUCT VALUES( 8, 'WRITING DESK', 'OAK', 425);
INSERT INTO PRODUCT VALUES( 7, 'ENTERTAINMENT CENTER', 'MAPLE', 650);
INSERT INTO PRODUCT VALUES( 11, 'CHILDRENS DRESSER', 'PINE', 300);
--creating the request table:
CREATE TABLE REQUEST
(
  OrderID         NUMBER(4),
  ProductID       NUMBER(2),
  Quantity        NUMBER(3)
                  CHECK((Quantity >= 1) AND (Quantity <= 100)),
                  
  CONSTRAINT PK_REQUEST_KEY
          PRIMARY KEY (OrderID, ProductId),
          
  CONSTRAINT FK_requestToProduct_KEY
          FOREIGN KEY (ProductID)
          REFERENCES PRODUCT(ProductID),
  CONSTRAINT FK_requestToOrder_KEY
          FOREIGN KEY (OrderID)
          REFERENCES FULLORDER(OrderID)
);
--populating the REQUEST table
INSERT INTO REQUEST VALUES(2106,3, 2);
INSERT INTO REQUEST VALUES(2106,8, 5);
INSERT INTO REQUEST VALUES(2106,7, 1);
INSERT INTO REQUEST VALUES(2107,11, 1);
INSERT INTO REQUEST VALUES(2107,7, 4);
INSERT INTO REQUEST VALUES(2108,11, 1);
--displaying the tables.
SELECT * FROM CUSTOMER;
SELECT * FROM FULLORDER;
SELECT * FROM PRODUCT;
SELECT * FROM REQUEST;