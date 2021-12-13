USE Invest
GO
CREATE SCHEMA Banks;
GO
CREATE SCHEMA Customers;
GO
CREATE SCHEMA Attachments;
GO
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES
           WHERE TABLE_NAME = N'BankGeneral')
BEGIN
	CREATE TABLE Banks.BankGeneral (
	bankId INT CONSTRAINT PK_Bank_Id NOT NULL PRIMARY KEY IDENTITY(1,1),
	email nvarchar(20) NOT NULL,
	password nvarchar(20) NOT NULL,
	bankName nvarchar(30) NOT NULL
);
END

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES
           WHERE TABLE_NAME = N'BankOffers')
BEGIN
CREATE TABLE Banks.BankOffers (
	offerId INT CONSTRAINT PK_Offer_Id NOT NULL PRIMARY KEY IDENTITY(1,1),
	percents decimal NOT NULL,
	mounthCount INT NOT NULL,
	bankId INT CONSTRAINT FK_Bank_Id FOREIGN KEY REFERENCES Banks.BankGeneral(bankId)
);
END

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES
           WHERE TABLE_NAME = N'CustomerGeneral')
BEGIN
CREATE TABLE Customers.CustomerGeneral (
	customerId INT CONSTRAINT PK_Customer_Id NOT NULL PRIMARY KEY IDENTITY(1,1),
	email nvarchar(20) NOT NULL,
	password nvarchar(20) NOT NULL,
	moneyCount decimal,
	name nvarchar(20),
	surname nvarchar(20)
)
END

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES
           WHERE TABLE_NAME = N'Attachments')
BEGIN
CREATE TABLE Attachments.Attachments (
	attachmentId INT CONSTRAINT PK_Attachment_Id NOT NULL PRIMARY KEY IDENTITY(1,1),
	offerId INT CONSTRAINT FK_Offer_Id FOREIGN KEY REFERENCES Banks.BankOffers(offerId),
	customerId INT CONSTRAINT FK_Customer_Id FOREIGN KEY REFERENCES Customers.CustomerGeneral(customerId),
	moneySum decimal NOT NULL,
	firstMoneySum decimal NOT NULL,
	dateOfEnd DATE NOT NULL,
	dateOfStart DATE NOT NULL,
	isOutOfSchedule bit NOT NULL
)
END