CREATE TABLE `Users` (
  `UserID` INT PRIMARY KEY AUTO_INCREMENT,
  `Username` VARCHAR(255) NOT NULL,
  `Email` VARCHAR(255) NOT NULL,
  `Password` VARCHAR(255) NOT NULL,
  `JoinDate` DATETIME,
  `LastLogin` DATETIME,
  `FirstName` VARCHAR(255),
  `LastName` VARCHAR(255),
  `DateOfBirth` DATE,
  `Gender` ENUM ('Male', 'Female', 'Other'),
  `PhoneNumber` VARCHAR(20),
  `Address` VARCHAR(255),
  `City` VARCHAR(100),
  `PostalCode` VARCHAR(20),
  `ProfilePictureURL` VARCHAR(255),
  `Bio` TEXT,
  `Status` TINYINT,
  `UserType` ENUM ('Admin', 'User', 'Guest')
);

CREATE TABLE `Products` (
  `ProductID` INT PRIMARY KEY AUTO_INCREMENT,
  `ProductName` VARCHAR(255) NOT NULL,
  `Price` DECIMAL(10,2) NOT NULL,
  `Description` TEXT,
  `Stock` INT,
  `SellerID` INT,
  `CategoryID` INT,
  `CreatedAt` DATETIME,
  `UpdatedAt` DATETIME,
  `ImageURL` VARCHAR(255),
  `IsActive` BOOLEAN,
  `RatingAverage` DECIMAL(3,2),
  `ReviewCount` INT,
  `Weight` DECIMAL(6,3),
  `Dimensions` VARCHAR(50),
  `Color` VARCHAR(50),
  `Material` VARCHAR(50),
  `Brand` VARCHAR(50),
  `SKU` VARCHAR(50),
  `WarrantyPeriod` INT
);

CREATE TABLE `Categories` (
  `CategoryID` INT PRIMARY KEY AUTO_INCREMENT,
  `CategoryName` VARCHAR(255) NOT NULL,
  `ParentID` INT,
  `Description` TEXT,
  `ImageURL` VARCHAR(255),
  `IsActive` BOOLEAN,
  `CreatedAt` DATETIME,
  `UpdatedAt` DATETIME,
  `MetaTitle` VARCHAR(255),
  `MetaDescription` TEXT,
  `MetaKeywords` VARCHAR(255)
);

CREATE TABLE `Orders` (
  `OrderID` INT PRIMARY KEY AUTO_INCREMENT,
  `UserID` INT,
  `OrderDate` DATETIME,
  `TotalPrice` DECIMAL(10,2),
  `Status` ENUM ('Pending', 'Completed', 'Cancelled'),
  `PaymentStatus` ENUM ('Pending', 'Paid', 'Refunded'),
  `ShipmentStatus` ENUM ('Pending', 'Shipped', 'Delivered'),
  `TrackingNumber` VARCHAR(255),
  `Notes` TEXT,
  `BillingAddressID` INT,
  `ShippingAddressID` INT
);

CREATE TABLE `OrderDetails` (
  `OrderDetailID` INT PRIMARY KEY AUTO_INCREMENT,
  `OrderID` INT,
  `ProductID` INT,
  `Quantity` INT,
  `Price` DECIMAL(10,2),
  `DiscountAmount` DECIMAL(10,2),
  `TotalPrice` DECIMAL(10,2),
  `ReturnStatus` ENUM ('No Return', 'Requested', 'Approved')
);

CREATE TABLE `Reviews` (
  `ReviewID` INT PRIMARY KEY AUTO_INCREMENT,
  `ProductID` INT,
  `UserID` INT,
  `Rating` INT,
  `Comment` TEXT,
  `ReviewDate` DATETIME,
  `IsApproved` BOOLEAN,
  `HelpfulCount` INT,
  `ImageURLs` TEXT,
  `VideoURLs` TEXT
);

CREATE TABLE `Friendships` (
  `FriendshipID` INT PRIMARY KEY AUTO_INCREMENT,
  `RequestorID` INT,
  `ReceiverID` INT,
  `Status` ENUM ('Pending', 'Accepted', 'Declined'),
  `StartDate` DATETIME,
  `EndDate` DATETIME,
  `LastInteraction` DATETIME
);

CREATE TABLE `Coupons` (
  `CouponID` INT PRIMARY KEY AUTO_INCREMENT,
  `CouponCode` VARCHAR(255) NOT NULL,
  `Description` TEXT,
  `DiscountAmount` DECIMAL(10,2),
  `ExpiryDate` DATE,
  `CreatedAt` DATETIME,
  `UpdatedAt` DATETIME,
  `UsageLimit` INT,
  `IsEnabled` BOOLEAN,
  `AppliesToNewCustomers` BOOLEAN,
  `MinimumPurchaseAmount` DECIMAL(10,2),
  `AppliesToCategoryID` INT,
  `AppliesToProductID` INT
);

CREATE TABLE `CouponUses` (
  `UseID` INT PRIMARY KEY AUTO_INCREMENT,
  `CouponID` INT,
  `UserID` INT,
  `UseDate` DATETIME,
  `OrderID` INT,
  `DiscountApplied` DECIMAL(10,2)
);

CREATE TABLE `Wishlists` (
  `WishlistID` INT PRIMARY KEY AUTO_INCREMENT,
  `UserID` INT,
  `CreatedAt` DATETIME,
  `UpdatedAt` DATETIME,
  `Title` VARCHAR(255),
  `IsPublic` BOOLEAN
);

CREATE TABLE `WishlistItems` (
  `WishlistItemID` INT PRIMARY KEY AUTO_INCREMENT,
  `WishlistID` INT,
  `ProductID` INT,
  `AddedAt` DATETIME
);

CREATE TABLE `Carts` (
  `CartID` INT PRIMARY KEY AUTO_INCREMENT,
  `UserID` INT,
  `CreatedAt` DATETIME,
  `UpdatedAt` DATETIME,
  `IsCheckedOut` BOOLEAN
);

CREATE TABLE `CartItems` (
  `CartItemID` INT PRIMARY KEY AUTO_INCREMENT,
  `CartID` INT,
  `ProductID` INT,
  `Quantity` INT,
  `AddedAt` DATETIME,
  `LastModified` DATETIME
);

CREATE TABLE `Shipments` (
  `ShipmentID` INT PRIMARY KEY AUTO_INCREMENT,
  `OrderID` INT,
  `ShipmentDate` DATETIME,
  `EstimatedArrival` DATETIME,
  `Carrier` VARCHAR(255),
  `TrackingURL` VARCHAR(255),
  `Status` ENUM ('Preparing', 'Shipped', 'Delivered'),
  `Notes` TEXT
);

CREATE TABLE `Addresses` (
  `AddressID` INT PRIMARY KEY AUTO_INCREMENT,
  `UserID` INT,
  `Street` VARCHAR(255),
  `City` VARCHAR(100),
  `State` VARCHAR(100),
  `PostalCode` VARCHAR(20),
  `Country` VARCHAR(100),
  `IsPrimary` BOOLEAN,
  `Type` ENUM ('Billing', 'Shipping'),
  `AddedAt` DATETIME
);

CREATE TABLE `Payments` (
  `PaymentID` INT PRIMARY KEY AUTO_INCREMENT,
  `OrderID` INT,
  `Amount` DECIMAL(10,2),
  `PaymentMethod` VARCHAR(255),
  `PaymentStatus` ENUM ('Completed', 'Pending', 'Failed'),
  `PaymentDate` DATETIME,
  `TransactionID` VARCHAR(255),
  `Currency` VARCHAR(3),
  `Notes` TEXT
);

CREATE TABLE `Events` (
  `EventID` INT PRIMARY KEY AUTO_INCREMENT,
  `EventName` VARCHAR(255),
  `StartDate` DATETIME,
  `EndDate` DATETIME,
  `Description` TEXT,
  `Location` VARCHAR(255),
  `OrganizedBy` VARCHAR(255),
  `IsPublic` BOOLEAN,
  `MaxParticipants` INT,
  `ImageURL` VARCHAR(255)
);

CREATE TABLE `EventParticipants` (
  `ParticipantID` INT PRIMARY KEY AUTO_INCREMENT,
  `EventID` INT,
  `UserID` INT,
  `JoinedAt` DATETIME,
  `Status` ENUM ('Confirmed', 'Cancelled', 'Pending')
);

CREATE TABLE `Promotions` (
  `PromotionID` INT PRIMARY KEY AUTO_INCREMENT,
  `Description` TEXT,
  `StartDate` DATETIME,
  `EndDate` DATETIME,
  `IsActive` BOOLEAN,
  `Code` VARCHAR(50),
  `DiscountPercentage` DECIMAL(5,2),
  `MaximumRedemption` INT,
  `AppliesToTotal` BOOLEAN,
  `ExclusionDetails` TEXT
);

CREATE TABLE `ProductPromotions` (
  `ProductPromotionID` INT PRIMARY KEY AUTO_INCREMENT,
  `ProductID` INT,
  `PromotionID` INT,
  `DiscountRate` DECIMAL(5,2),
  `StartDate` DATETIME,
  `EndDate` DATETIME,
  `IsActive` BOOLEAN,
  `Notes` TEXT
);

ALTER TABLE `Products` ADD FOREIGN KEY (`SellerID`) REFERENCES `Users` (`UserID`);

ALTER TABLE `Products` ADD FOREIGN KEY (`CategoryID`) REFERENCES `Categories` (`CategoryID`);

ALTER TABLE `Categories` ADD FOREIGN KEY (`ParentID`) REFERENCES `Categories` (`CategoryID`);

ALTER TABLE `Orders` ADD FOREIGN KEY (`UserID`) REFERENCES `Users` (`UserID`);

ALTER TABLE `Orders` ADD FOREIGN KEY (`BillingAddressID`) REFERENCES `Addresses` (`AddressID`);

ALTER TABLE `Orders` ADD FOREIGN KEY (`ShippingAddressID`) REFERENCES `Addresses` (`AddressID`);

ALTER TABLE `OrderDetails` ADD FOREIGN KEY (`OrderID`) REFERENCES `Orders` (`OrderID`);

ALTER TABLE `OrderDetails` ADD FOREIGN KEY (`ProductID`) REFERENCES `Products` (`ProductID`);

ALTER TABLE `Reviews` ADD FOREIGN KEY (`ProductID`) REFERENCES `Products` (`ProductID`);

ALTER TABLE `Reviews` ADD FOREIGN KEY (`UserID`) REFERENCES `Users` (`UserID`);

ALTER TABLE `Friendships` ADD FOREIGN KEY (`RequestorID`) REFERENCES `Users` (`UserID`);

ALTER TABLE `Friendships` ADD FOREIGN KEY (`ReceiverID`) REFERENCES `Users` (`UserID`);

ALTER TABLE `Coupons` ADD FOREIGN KEY (`AppliesToCategoryID`) REFERENCES `Categories` (`CategoryID`);

ALTER TABLE `Coupons` ADD FOREIGN KEY (`AppliesToProductID`) REFERENCES `Products` (`ProductID`);

ALTER TABLE `CouponUses` ADD FOREIGN KEY (`CouponID`) REFERENCES `Coupons` (`CouponID`);

ALTER TABLE `CouponUses` ADD FOREIGN KEY (`UserID`) REFERENCES `Users` (`UserID`);

ALTER TABLE `CouponUses` ADD FOREIGN KEY (`OrderID`) REFERENCES `Orders` (`OrderID`);

ALTER TABLE `Wishlists` ADD FOREIGN KEY (`UserID`) REFERENCES `Users` (`UserID`);

ALTER TABLE `WishlistItems` ADD FOREIGN KEY (`WishlistID`) REFERENCES `Wishlists` (`WishlistID`);

ALTER TABLE `WishlistItems` ADD FOREIGN KEY (`ProductID`) REFERENCES `Products` (`ProductID`);

ALTER TABLE `Carts` ADD FOREIGN KEY (`UserID`) REFERENCES `Users` (`UserID`);

ALTER TABLE `CartItems` ADD FOREIGN KEY (`CartID`) REFERENCES `Carts` (`CartID`);

ALTER TABLE `CartItems` ADD FOREIGN KEY (`ProductID`) REFERENCES `Products` (`ProductID`);

ALTER TABLE `Shipments` ADD FOREIGN KEY (`OrderID`) REFERENCES `Orders` (`OrderID`);

ALTER TABLE `Addresses` ADD FOREIGN KEY (`UserID`) REFERENCES `Users` (`UserID`);

ALTER TABLE `Payments` ADD FOREIGN KEY (`OrderID`) REFERENCES `Orders` (`OrderID`);

ALTER TABLE `EventParticipants` ADD FOREIGN KEY (`EventID`) REFERENCES `Events` (`EventID`);

ALTER TABLE `EventParticipants` ADD FOREIGN KEY (`UserID`) REFERENCES `Users` (`UserID`);

ALTER TABLE `ProductPromotions` ADD FOREIGN KEY (`ProductID`) REFERENCES `Products` (`ProductID`);

ALTER TABLE `ProductPromotions` ADD FOREIGN KEY (`PromotionID`) REFERENCES `Promotions` (`PromotionID`);
