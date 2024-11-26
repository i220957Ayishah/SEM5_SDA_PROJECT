create database CRS;
use CRS;




CREATE TABLE User (
                      userID INT PRIMARY KEY AUTO_INCREMENT, -- Unique identifier for each user
                      username VARCHAR(50) NOT NULL UNIQUE ,          -- Username, must be unique
                      password VARCHAR(255) NOT NULL,         -- Password, encrypted in practice
                      fName VARCHAR(50) NOT NULL,             -- First name
                      lName VARCHAR(50) NOT NULL,             -- Last name
                      email VARCHAR(100) NOT NULL UNIQUE,     -- Email, must be unique
                      dob DATE NOT NULL,                      -- Date of birth
                      phone VARCHAR(15) NOT NULL,             -- Phone number
                      address TEXT NOT NULL,                  -- Address
                      gender ENUM('male', 'female') NOT NULL, -- Gender as predefined options
                      type ENUM('admin', 'customer') NOT NULL               -- User type (e.g., admin, customer, etc.)
);

CREATE TABLE Admin (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       userId INT NOT NULL,
                       FOREIGN KEY (userId) REFERENCES User(userID)
);

CREATE TABLE Customer (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          userId INT NOT NULL,
                          FOREIGN KEY (userId) REFERENCES User(userID)
);

CREATE TABLE Car (
                     carID INT PRIMARY KEY AUTO_INCREMENT,         -- Unique identifier for each car
                     name VARCHAR(50) NOT NULL,                    -- Car name or brand
                     model VARCHAR(50) NOT NULL,                   -- Model of the car
                     color VARCHAR(30) NOT NULL,                   -- Color of the car
                     available BOOLEAN NOT NULL DEFAULT TRUE,      -- Availability status
                     chargesPerDay DECIMAL(10, 2) NOT NULL,        -- Daily rental charges
                     sunroof BOOLEAN NOT NULL DEFAULT FALSE        -- Indicates if the car has a sunroof
);

CREATE TABLE Driver (
                        driverID INT PRIMARY KEY AUTO_INCREMENT,     -- Unique identifier for each driver
                        fName VARCHAR(50) NOT NULL,                  -- First name of the driver
                        lName VARCHAR(50) NOT NULL,                  -- Last name of the driver
                        email VARCHAR(100) NOT NULL UNIQUE,          -- Email address (must be unique)
                        dob DATE NOT NULL,                           -- Date of birth
                        phone VARCHAR(15) NOT NULL UNIQUE,           -- Phone number (must be unique)
                        address TEXT NOT NULL,                       -- Address of the driver
                        gender ENUM('Male', 'Female') NOT NULL -- Gender of the driver
);

CREATE TABLE Payment (
                         paymentID INT PRIMARY KEY AUTO_INCREMENT,     -- Unique identifier for each payment
                         modeOfPayment VARCHAR(50) NOT NULL,           -- Payment method (e.g., Credit Card, Cash)
                         amount INT NOT NULL

);

CREATE TABLE Booking (
                         bookingID INT PRIMARY KEY AUTO_INCREMENT,     -- Unique identifier for each booking
                         carID INT NOT NULL,                           -- Foreign key referencing Car
                         userID INT NOT NULL,                          -- Foreign key referencing User
                         paymentID INT,
                         driverID INT,                                 -- Foreign key referencing Driver (nullable if no driver is assigned)
                         totalCharges DOUBLE NOT NULL,                 -- Total charges for the booking
                         issueDate DATE NOT NULL,                      -- The date the car is issued
                         returnDate DATE NOT NULL,                     -- The planned return date
                         customerReturnDate DATE,                      -- The actual return date (nullable if not yet returned)
                         paymentPending BOOLEAN NOT NULL DEFAULT TRUE, -- Indicates if payment is pending
                         FOREIGN KEY (carID) REFERENCES Car(carID) ON DELETE CASCADE,
                         FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE,
                         FOREIGN KEY (driverID) REFERENCES Driver(driverID) ON DELETE SET NULL,
                         FOREIGN KEY (paymentID) REFERENCES Payment(paymentID) ON DELETE CASCADE
);


-- ......................................................................................................................



CREATE PROCEDURE AddUser(
    IN p_username VARCHAR(50),
    IN p_password VARCHAR(255),
    IN p_fName VARCHAR(50),
    IN p_lName VARCHAR(50),
    IN p_email VARCHAR(100),
    IN p_dob DATE,
    IN p_phone VARCHAR(15),
    IN p_address TEXT,
    IN p_gender ENUM('male', 'female'),
    IN p_type ENUM('admin', 'customer')
)
BEGIN
    DECLARE newUserID INT;

    INSERT INTO User (username, password, fName, lName, email, dob, phone, address, gender, type)
    VALUES (p_username, p_password, p_fName, p_lName, p_email, p_dob, p_phone, p_address, p_gender, p_type);

    SET newUserID = LAST_INSERT_ID();

    IF p_type = 'admin' THEN
        INSERT INTO Admin (userId) VALUES (newUserID);
    ELSEIF p_type = 'customer' THEN
        INSERT INTO Customer (userId) VALUES (newUserID);
    END IF;
END;


CREATE PROCEDURE UpdateUser(
    IN p_userID INT,
    IN p_username VARCHAR(50),
    IN p_password VARCHAR(255),
    IN p_fName VARCHAR(50),
    IN p_lName VARCHAR(50),
    IN p_email VARCHAR(100),
    IN p_dob DATE,
    IN p_phone VARCHAR(15),
    IN p_address TEXT,
    IN p_gender ENUM('male', 'female'),
    IN p_type ENUM('admin', 'customer')
)
BEGIN
UPDATE User
SET username = p_username, password = p_password, fName = p_fName, lName = p_lName,
    email = p_email, dob = p_dob, phone = p_phone, address = p_address, gender = p_gender, type = p_type
WHERE userID = p_userID;
END;


CREATE PROCEDURE DeleteUser(
    IN p_userID INT
)
BEGIN
    DECLARE userType ENUM('admin', 'customer');

    SELECT type INTO userType
    FROM User
    WHERE userID = p_userID;

    IF userType = 'admin' THEN
        DELETE FROM Admin WHERE userId = p_userID;
    ELSEIF userType = 'customer' THEN
        DELETE FROM Customer WHERE userId = p_userID;
    END IF;

    DELETE FROM User WHERE userID = p_userID;
END;


CREATE PROCEDURE GetUserBookings(
    IN p_userID INT
)
BEGIN
    SELECT
        b.bookingID,
        b.carID,
        c.name AS carName,
        c.model AS carModel,
        b.paymentID,
        b.driverID,
        d.fName AS driverFirstName,
        d.lName AS driverLastName,
        b.totalCharges,
        b.issueDate,
        b.returnDate,
        b.customerReturnDate,
        b.paymentPending
    FROM Booking b
             LEFT JOIN Car c ON b.carID = c.carID
             LEFT JOIN Driver d ON b.driverID = d.driverID
    WHERE b.userID = p_userID;
END;


CREATE PROCEDURE GetAllUsers()
BEGIN
    SELECT*
    FROM User;
END;


CREATE PROCEDURE GetUserById(IN p_userID INT)
BEGIN
    SELECT * FROM User WHERE userID = p_userID;
END;



-- ......................................................................................................................



CREATE PROCEDURE AddCar(
    IN p_name VARCHAR(50),
    IN p_model VARCHAR(50),
    IN p_color VARCHAR(30),
    IN p_available BOOLEAN,
    IN p_chargesPerDay DECIMAL(10, 2),
    IN p_sunroof BOOLEAN
)
BEGIN
    INSERT INTO Car (name, model, color, available, chargesPerDay, sunroof)
    VALUES (p_name, p_model, p_color, p_available, p_chargesPerDay, p_sunroof);
END;


CREATE PROCEDURE UpdateCar(
    IN p_carID INT,
    IN p_name VARCHAR(50),
    IN p_model VARCHAR(50),
    IN p_color VARCHAR(30),
    IN p_available BOOLEAN,
    IN p_chargesPerDay DECIMAL(10, 2),
    IN p_sunroof BOOLEAN
)
BEGIN
    UPDATE Car
    SET name = p_name, model = p_model, color = p_color, available = p_available,
        chargesPerDay = p_chargesPerDay, sunroof = p_sunroof
    WHERE carID = p_carID;
END;


CREATE PROCEDURE DeleteCar(
    IN p_carID INT
)
BEGIN
    DELETE FROM Car WHERE carID = p_carID;
END;


CREATE PROCEDURE GetAvailableCars()
BEGIN
    SELECT
        carID,
        name,
        model,
        color,
        chargesPerDay,
        sunroof
    FROM Car
    WHERE available = TRUE;
END;


CREATE PROCEDURE GetAllCars()
BEGIN
    SELECT* FROM Car;
END;


CREATE PROCEDURE GetCarById(IN p_carID INT)
BEGIN
    SELECT * FROM Car WHERE carID = p_carID;
END;


CREATE PROCEDURE setCarStatus(
    IN p_carID INT,
    IN p_carStatus BOOlEAN
)
BEGIN

    UPDATE Car
    SET available = p_carStatus
    WHERE carID=p_carID;
END;



-- ...................................................................................................................................



CREATE PROCEDURE AddDriver(
    IN p_fName VARCHAR(50),
    IN p_lName VARCHAR(50),
    IN p_email VARCHAR(100),
    IN p_dob DATE,
    IN p_phone VARCHAR(15),
    IN p_address TEXT,
    IN p_gender ENUM('Male', 'Female')
)
BEGIN
    INSERT INTO Driver (fName, lName, email, dob, phone, address, gender)
    VALUES (p_fName, p_lName, p_email, p_dob, p_phone, p_address, p_gender);
END;


CREATE PROCEDURE UpdateDriver(
    IN p_driverID INT,
    IN p_fName VARCHAR(50),
    IN p_lName VARCHAR(50),
    IN p_email VARCHAR(100),
    IN p_dob DATE,
    IN p_phone VARCHAR(15),
    IN p_address TEXT,
    IN p_gender ENUM('Male', 'Female')
)
BEGIN
    UPDATE Driver
    SET fName = p_fName, lName = p_lName, email = p_email, dob = p_dob,
        phone = p_phone, address = p_address, gender = p_gender
    WHERE driverID = p_driverID;
END;


CREATE PROCEDURE DeleteDriver(
    IN p_driverID INT
)
BEGIN
    DELETE FROM Driver WHERE driverID = p_driverID;
END;


CREATE PROCEDURE GetAllDrivers()
BEGIN
    SELECT*
    FROM Driver;
END;


CREATE PROCEDURE GetDriverById(IN p_driverID INT)
BEGIN
    SELECT * FROM Driver WHERE driverID = p_driverID;
END;




-- .......................................................................................................................................




CREATE PROCEDURE AddBooking(
    IN p_carID INT,
    IN p_userID INT,
    IN p_paymentID INT,
    IN p_driverID INT,
    IN p_totalCharges DOUBLE,
    IN p_issueDate DATE,
    IN p_returnDate DATE,
    IN p_paymentPending BOOLEAN
)
BEGIN
    INSERT INTO Booking (carID, userID, paymentID, driverID, totalCharges, issueDate, returnDate, paymentPending)
    VALUES (p_carID, p_userID, p_paymentID, p_driverID, p_totalCharges, p_issueDate, p_returnDate, p_paymentPending);
END;


CREATE PROCEDURE UpdateBooking(
    IN p_bookingID INT,
    IN p_carID INT,
    IN p_userID INT,
    IN p_paymentID INT,
    IN p_driverID INT,
    IN p_totalCharges DOUBLE,
    IN p_issueDate DATE,
    IN p_returnDate DATE,
    IN p_customerReturnDate DATE,
    IN p_paymentPending BOOLEAN
)
BEGIN
    UPDATE Booking
    SET carID = p_carID, userID = p_userID, paymentID = p_paymentID, driverID = p_driverID,
        totalCharges = p_totalCharges, issueDate = p_issueDate, returnDate = p_returnDate,
        customerReturnDate = p_customerReturnDate, paymentPending = p_paymentPending
    WHERE bookingID = p_bookingID;
END;


CREATE PROCEDURE DeleteBooking(
    IN p_bookingID INT
)
BEGIN
    DELETE FROM Booking WHERE bookingID = p_bookingID;
END;


CREATE PROCEDURE GetAllBookings()
BEGIN
    SELECT * FROM Booking;
END;


-- ...............................................................................................................................



CREATE PROCEDURE AddPayment(
    IN p_modeOfPayment VARCHAR(50),
    IN p_amount INT
)
BEGIN
    INSERT INTO Payment (modeOfPayment, amount)
    VALUES (p_modeOfPayment, p_amount);
END;


CREATE PROCEDURE UpdatePayment(
    IN p_paymentID INT,
    IN p_modeOfPayment VARCHAR(50),
    IN p_amount INT
)
BEGIN
    UPDATE Payment
    SET modeOfPayment = p_modeOfPayment, amount = p_amount
    WHERE paymentID = p_paymentID;
END;


CREATE PROCEDURE DeletePayment(IN p_paymentID INT)
BEGIN
    DELETE FROM Payment WHERE paymentID = p_paymentID;
END;


CREATE PROCEDURE GetPaymentById(IN p_paymentID INT)
BEGIN
    SELECT * FROM Payment WHERE paymentID = p_paymentID;
END;


CREATE PROCEDURE setPaymentStatus(
    IN p_BookingID INT,
    IN p_status BOOlEAN
)
BEGIN

    UPDATE booking
    SET paymentPending = p_status
    WHERE bookingID = p_BookingID;
END;


CREATE PROCEDURE GetAllPayments()
BEGIN
    SELECT *
        FROM payment;
end;


select * from user