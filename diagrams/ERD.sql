CREATE TABLE User (
 userId INT NOT NULL AUTO_INCREMENT,
 fname CHAR(20),
 lname CHAR(20),
 userName CHAR(20),
 password CHAR(50),
 email CHAR(20),
 phoneNumber CHAR(15),
 PRIMARY KEY(userId)
);

CREATE TABLE Employee (
 employeeId INT NOT NULL,
 userId INT NOT NULL
);

ALTER TABLE Employee ADD CONSTRAINT PK_Employee PRIMARY KEY (employeeId);

CREATE TABLE EmployeeTransaction (
 employeeId INT NOT NULL,
 transactionId INT NOT NULL
);

ALTER TABLE EmployeeTransaction ADD CONSTRAINT PK_EmployeeTransaction PRIMARY KEY (employeeId,transactionId);

CREATE TABLE Customer (
 customerId INT NOT NULL AUTO_INCREMENT,
 userId INT NOT NULL,
 PRIMARY KEY(customerId)
);


CREATE TABLE CustomerTransaction (
 customerId INT NOT NULL,
 transactionId INT NOT NULL
);

ALTER TABLE CustomerTransaction ADD CONSTRAINT PK_CustomerTransaction PRIMARY KEY (customerId,transactionId);

CREATE TABLE Employer (
 employerId INT NOT NULL AUTO_INCREMENT,
 userId INT NOT NULL,
 PRIMARY KEY(employerId)
);

CREATE TABLE EmployerEmployee (
 employerId INT NOT NULL,
 employeeId INT NOT NULL
);

ALTER TABLE EmployerEmployee ADD CONSTRAINT PK_EmployerEmployee PRIMARY KEY (employerId,employeeId);


CREATE TABLE Transaction (
 transactionId INT NOT NULL AUTO_INCREMENT,
 date DATE NOT NULL,
 PRIMARY KEY(transactionId)
);

ALTER TABLE Employee ADD CONSTRAINT FK_Employee_0 FOREIGN KEY (userId) REFERENCES User (userId) ON DELETE CASCADE;

ALTER TABLE EmployeeTransaction ADD CONSTRAINT FK_EmployeeTransaction_0 FOREIGN KEY (employeeId) REFERENCES Employee (employeeId) ON DELETE CASCADE;
ALTER TABLE EmployeeTransaction ADD CONSTRAINT FK_EmployeeTransaction_1 FOREIGN KEY (transactionId) REFERENCES Transaction (transactionId) ON DELETE CASCADE;

ALTER TABLE Customer ADD CONSTRAINT FK_Customer_0 FOREIGN KEY (userId) REFERENCES User (userId) ON DELETE CASCADE;

ALTER TABLE CustomerTransaction ADD CONSTRAINT FK_CustomerTransaction_0 FOREIGN KEY (customerId) REFERENCES Customer (customerId) ON DELETE CASCADE;
ALTER TABLE CustomerTransaction ADD CONSTRAINT FK_CustomerTransaction_1 FOREIGN KEY (transactionId) REFERENCES Transaction (transactionId) ON DELETE CASCADE;

ALTER TABLE Employer ADD CONSTRAINT FK_Employer_0 FOREIGN KEY (userId) REFERENCES User (userId) ON DELETE CASCADE;


ALTER TABLE EmployerEmployee ADD CONSTRAINT FK_EmployerEmployee_0 FOREIGN KEY (employerId) REFERENCES Employer (employerId) ON DELETE CASCADE;
ALTER TABLE EmployerEmployee ADD CONSTRAINT FK_EmployerEmployee_1 FOREIGN KEY (employeeId) REFERENCES Employee (employeeId) ON DELETE CASCADE;