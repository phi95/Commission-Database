CREATE TABLE User (
 userId INT NOT NULL AUTO_INCREMENT,
 fname CHAR(20),
 lname CHAR(20),
 userName CHAR(20),
 password CHAR(50),
 email CHAR(20),
 phoneNumber CHAR(15)
);

ALTER TABLE User ADD CONSTRAINT PK_User PRIMARY KEY (userId);


CREATE TABLE Worker (
 workerId INT NOT NULL,
 userId INT NOT NULL
);

ALTER TABLE Worker ADD CONSTRAINT PK_Worker PRIMARY KEY (workerId);

CREATE TABLE WorkerTransaction (
 workerId INT NOT NULL,
 transactionId INT NOT NULL
);

ALTER TABLE WorkerTransaction ADD CONSTRAINT PK_WorkerTransaction PRIMARY KEY (workerId,transactionId);

CREATE TABLE Customer (
 customerId INT NOT NULL AUTO_INCREMENT,
 userId INT NOT NULL
);

ALTER TABLE Customer ADD CONSTRAINT PK_Customer PRIMARY KEY (customerId);

CREATE TABLE CustomerTransaction (
 customerId INT NOT NULL,
 transactionId INT NOT NULL
);

ALTER TABLE CustomerTransaction ADD CONSTRAINT PK_CustomerTransaction PRIMARY KEY (customerId,transactionId);

CREATE TABLE Manager (
 managerId INT NOT NULL AUTO_INCREMENT,
 userId INT NOT NULL
);

ALTER TABLE Manager ADD CONSTRAINT PK_Manager PRIMARY KEY (managerId);


CREATE TABLE ManagerWorker (
 managerId INT NOT NULL,
 workerId INT NOT NULL
);

ALTER TABLE ManagerWorker ADD CONSTRAINT PK_ManagerWorker PRIMARY KEY (managerId,workerId);


CREATE TABLE Transaction (
 transactionId INT NOT NULL AUTO_INCREMENT,
 date DATE NOT NULL,
);

ALTER TABLE Transaction ADD CONSTRAINT PK_Transaction PRIMARY KEY (transactionId);


ALTER TABLE Worker ADD CONSTRAINT FK_Worker_0 FOREIGN KEY (userId) REFERENCES User (userId) ON DELETE CASCADE;

ALTER TABLE WorkerTransaction ADD CONSTRAINT FK_WorkerTransaction_0 FOREIGN KEY (workerId) REFERENCES Worker (workerId) ON DELETE CASCADE;
ALTER TABLE WorkerTransaction ADD CONSTRAINT FK_WorkerTransaction_1 FOREIGN KEY (transactionId) REFERENCES Transaction (transactionId) ON DELETE CASCADE;

ALTER TABLE Customer ADD CONSTRAINT FK_Customer_0 FOREIGN KEY (userId) REFERENCES User (userId) ON DELETE CASCADE;

ALTER TABLE CustomerTransaction ADD CONSTRAINT FK_CustomerTransaction_0 FOREIGN KEY (customerId) REFERENCES Customer (customerId) ON DELETE CASCADE;
ALTER TABLE CustomerTransaction ADD CONSTRAINT FK_CustomerTransaction_1 FOREIGN KEY (transactionId) REFERENCES Transaction (transactionId) ON DELETE CASCADE;

ALTER TABLE Manager ADD CONSTRAINT FK_Manager_0 FOREIGN KEY (userId) REFERENCES User (userId) ON DELETE CASCADE;


ALTER TABLE ManagerWorker ADD CONSTRAINT FK_ManagerWorker_0 FOREIGN KEY (managerId) REFERENCES Manager (managerId) ON DELETE CASCADE;
ALTER TABLE ManagerWorker ADD CONSTRAINT FK_ManagerWorker_1 FOREIGN KEY (workerId) REFERENCES Worker (workerId) ON DELETE CASCADE;


ALTER TABLE Transaction ADD CONSTRAINT FK_Transaction_0 FOREIGN KEY (workerId) REFERENCES Worker (workerId) ON DELETE CASCADE;
ALTER TABLE Transaction ADD CONSTRAINT FK_Transaction_1 FOREIGN KEY (customerId) REFERENCES Customer (customerId) ON DELETE CASCADE;
