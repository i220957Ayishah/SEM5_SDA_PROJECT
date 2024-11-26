package com.example.project2.Storage;

import com.example.project2.BOOKING.Booking;
import com.example.project2.CAR.Car;
import com.example.project2.PAYMENT.Payment;
import com.example.project2.USER.Admin;
import com.example.project2.USER.Customer;
import com.example.project2.USER.User;
import com.example.project2.DRIVER.Driver;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;

public class DataBase {
    private static final String connectionString = "jdbc:mysql://localhost:3306/CRS";
    private static final String username = "Ayishah";
    private static final String password = "A*003313s";

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database: " + e.getMessage(), e);
        }
    }

    public User readUserByID(int id) {
        String sql = "{CALL GetUserByID(?)}";
        User user = null;
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            ResultSet obj = stmt.executeQuery();
            while (obj.next()) {
                if (obj.getString("type").equals("customer")) {
                    user = new Customer(obj.getString("username"), obj.getInt("userID"), obj.getString("password"), obj.getString("fname"), obj.getString("lname"), obj.getString("email"), obj.getDate("dob").toLocalDate(), obj.getString("phone"), obj.getString("address"), obj.getString("gender"), obj.getString("type"));
                }
                else if (obj.getString("type").equals("admin")) {
                    user = new Admin(obj.getString("username"), obj.getInt("userID"), obj.getString("password"), obj.getString("fname"), obj.getString("lname"), obj.getString("email"), obj.getDate("dob").toLocalDate(), obj.getString("phone"), obj.getString("address"), obj.getString("gender"), obj.getString("type"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading loaned books from the database: " + e.getMessage(), e);
        }
        return user;
    }
    public Car readCarByID(int id) {
        String sql = "{CALL GetCarById(?)}";
        Car car = null;
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            ResultSet obj = stmt.executeQuery();
            while (obj.next()) {
                car = new Car(obj.getInt("carID"), obj.getString("name"), obj.getString("model"), obj.getString("color"), obj.getBoolean("available"), obj.getDouble("chargesPerDay"), obj.getBoolean("sunroof"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading loaned books from the database: " + e.getMessage(), e);
        }
        return car;
    }
    public Driver readDriverByID(int id) {
        String sql = "{CALL GetDriverById(?)}";
        Driver driver = null;
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            ResultSet obj = stmt.executeQuery();
            while (obj.next()) {
                driver = new Driver(obj.getInt("driverID"), obj.getString("fname"), obj.getString("lname"), obj.getString("email"), obj.getDate("dob").toLocalDate(), obj.getString("phone"), obj.getString("address"), obj.getString("gender"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading loaned books from the database: " + e.getMessage(), e);
        }
        return driver;
    }
    public Payment readPaymentByID(int id) {
        String sql = "{CALL GetPaymentById(?)}";
        Payment payment = null;
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            ResultSet obj = stmt.executeQuery();
            while (obj.next()) {
                payment = new Payment(obj.getInt("paymentID"), obj.getString("modeOfPayment"), obj.getDouble("amount"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading loaned books from the database: " + e.getMessage(), e);
        }
        return payment;
    }
    public ArrayList<Booking> readBookingsByID(int id) {
        String sql = "{CALL GetUserBookings(?)}";
        ArrayList<Booking> bookings = new ArrayList();
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            ResultSet obj = stmt.executeQuery();
            while (obj.next()) {
                createBooking(obj, bookings);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading loaned books from the database: " + e.getMessage(), e);
        }
        return bookings;
    }

    private void createUser(ResultSet obj, ArrayList<User> users) throws SQLException {
        if (obj.getString("type").equals("customer")) {
            User user = new Customer(obj.getString("username"), obj.getInt("userID"), obj.getString("password"), obj.getString("fname"), obj.getString("lname"), obj.getString("email"), obj.getDate("dob").toLocalDate(), obj.getString("phone"), obj.getString("address"), obj.getString("gender"), obj.getString("type"));
            users.add(user);
        }
        else if (obj.getString("type").equals("admin")) {
            User user = new Admin(obj.getString("username"), obj.getInt("userID"), obj.getString("password"), obj.getString("fname"), obj.getString("lname"), obj.getString("email"), obj.getDate("dob").toLocalDate(), obj.getString("phone"), obj.getString("address"), obj.getString("gender"), obj.getString("type"));
            users.add(user);
        }
    }
    private ArrayList<User> getUsersHelper(ArrayList<User> users, String query) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                createUser(rs, users);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading from the database: " + e.getMessage(), e);
        }
        return users;
    }
    public ArrayList<User> readUserList() {
        ArrayList<User> users = new ArrayList<>();
        String query = "{CALL GetAllUsers()}";
        return getUsersHelper(users, query);
    }

    private void createCar(ResultSet obj, ArrayList<Car> cars) throws SQLException {
        Car car = new Car(obj.getInt("carID"), obj.getString("name"), obj.getString("model"), obj.getString("color"), obj.getBoolean(5), obj.getDouble("chargesPerDay"), obj.getBoolean("sunroof"));
        cars.add(car);
    }
    private ArrayList<Car> getCarsHelper(ArrayList<Car> cars, String query) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                createCar(rs, cars);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading from the database: " + e.getMessage(), e);
        }
        return cars;
    }
    public ArrayList<Car> readAvailableCarsList() {
        ArrayList<Car> cars = new ArrayList<>();
        String query = "{CALL GetAvailableCars()}";
        return getCarsHelper(cars, query);
    }
    public ArrayList<Car> readAllCars() {
        ArrayList<Car> cars = new ArrayList<>();
        String query = "{CALL GetAllCars()}";
        return getCarsHelper(cars, query);
    }

    private void createDriver(ResultSet obj, ArrayList<Driver> drivers) throws SQLException {
            Driver driver = new Driver(obj.getInt("driverID"), obj.getString("fname"), obj.getString("lname"), obj.getString("email"), obj.getDate("dob").toLocalDate(), obj.getString("phone"), obj.getString("address"), obj.getString("gender"));
            drivers.add(driver);
    }
    private ArrayList<Driver> getDriversHelper(ArrayList<Driver> drivers, String query) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                createDriver(rs, drivers);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading from the database: " + e.getMessage(), e);
        }
        return drivers;
    }
    public ArrayList<Driver> readDriversList() {
        ArrayList<Driver> drivers = new ArrayList<>();
        String query = "{CALL GetAllDrivers()}";
        return getDriversHelper(drivers, query);
    }

    private void createBooking(ResultSet obj, ArrayList<Booking> bookings) throws SQLException {
        Booking booking = null;
        if(obj.wasNull()) {
            if(obj.getDate("customerReturnDate") == null) {
                booking = new Booking(obj.getInt("bookingID"),
                        readCarByID(obj.getInt("carID")),
                        readUserByID(obj.getInt("userID")),
                        null,
                        readPaymentByID(obj.getInt("paymentID")),
                        obj.getDouble("totalCharges"),
                        obj.getDate("issueDate").toLocalDate(),
                        obj.getDate("returnDate").toLocalDate(),
                        null,
                        obj.getBoolean("paymentPending")
                );
            }
            else{
                booking = new Booking(obj.getInt("bookingID"),
                        readCarByID(obj.getInt("carID")),
                        readUserByID(obj.getInt("userID")),
                        null,
                        readPaymentByID(obj.getInt("paymentID")),
                        obj.getDouble("totalCharges"),
                        obj.getDate("issueDate").toLocalDate(),
                        obj.getDate("returnDate").toLocalDate(),
                        obj.getDate("customerReturnDate").toLocalDate(),
                        obj.getBoolean("paymentPending")
                );
            }
        }
        else {
            if(obj.getDate("customerReturnDate") == null) {
                booking = new Booking(obj.getInt("bookingID"),
                        readCarByID(obj.getInt("carID")),
                        readUserByID(obj.getInt("userID")),
                        readDriverByID(obj.getInt("driverID")),
                        readPaymentByID(obj.getInt("paymentID")),
                        obj.getDouble("totalCharges"),
                        obj.getDate("issueDate").toLocalDate(),
                        obj.getDate("returnDate").toLocalDate(),
                        null,
                        obj.getBoolean("paymentPending")
                );
            }
            else{
                booking = new Booking(obj.getInt("bookingID"),
                        readCarByID(obj.getInt("carID")),
                        readUserByID(obj.getInt("userID")),
                        readDriverByID(obj.getInt("driverID")),
                        readPaymentByID(obj.getInt("paymentID")),
                        obj.getDouble("totalCharges"),
                        obj.getDate("issueDate").toLocalDate(),
                        obj.getDate("returnDate").toLocalDate(),
                        obj.getDate("customerReturnDate").toLocalDate(),
                        obj.getBoolean("paymentPending")
                );
            }
        }
        bookings.add(booking);
    }
    private ArrayList<Booking> getBookingsHelper(ArrayList<Booking> bookings, String query) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                createBooking(rs, bookings);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading from the database: " + e.getMessage(), e);
        }
        return bookings;
    }
    public ArrayList<Booking> readBookingsList() {
        ArrayList<Booking> bookings = new ArrayList<>();
        String query = "{CALL GetAllBookings()}";
        return getBookingsHelper(bookings, query);
    }

    private void createPayment(ResultSet obj, ArrayList<Payment> payments) throws SQLException {
        Payment payment = new Payment(obj.getInt(1), obj.getString(2), obj.getDouble(3));
        payments.add(payment);
    }
    private ArrayList<Payment> getPaymentHelper(ArrayList<Payment> payments, String query) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                createPayment(rs, payments);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading from the database: " + e.getMessage(), e);
        }
        return payments;
    }
    public ArrayList<Payment> readPaymentList() {
        ArrayList<Payment> payment = new ArrayList<>();
        String query = "{CALL GetAllPayments()}";
        return getPaymentHelper(payment, query);
    }

    public void addUser(User user){
        String addUserSql = "{CALL AddUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(addUserSql)) {

            // Set parameters for the stored procedure
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFName());
            stmt.setString(4, user.getLName());
            stmt.setString(5, user.getEmail());
            stmt.setDate(6, new java.sql.Date(Date.from(user.getDob().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            stmt.setString(7, user.getPhone());
            stmt.setString(8, user.getAddress());
            stmt.setString(9, user.getGender());
            stmt.setString(10, user.getType());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding user to the database: " + e.getMessage(), e);
        }
    }
    public void updateUser(User user){
        String sql = "{CALL UpdateUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";  // Calling the stored procedure

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, user.getUserID());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getFName());
            stmt.setString(5, user.getLName());
            stmt.setString(6, user.getEmail());
            stmt.setDate(7,new java.sql.Date(Date.from(user.getDob().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            stmt.setString(8, user.getPhone());
            stmt.setString(9, user.getAddress());
            stmt.setString(10, user.getGender());
            stmt.setString(11, user.getType());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user in the database: " + e.getMessage(), e);
        }
    }
    public void deleteUser(int id){
        String sql = "{CALL DeleteUser(?)}";  // Calling the stored procedure

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user from the database: " + e.getMessage(), e);
        }
    }

    public void addCar(Car car){
        String addUserSql = "{CALL AddCar(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(addUserSql)) {

            // Set parameters for the stored procedure
            stmt.setString(1, car.getName());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getColor());
            stmt.setBoolean(4, car.isAvailable());
            stmt.setDouble(5, car.getChargesPerDay());
            stmt.setBoolean(6, car.isSunroof());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding user to the database: " + e.getMessage(), e);
        }
    }
    public void updateCar(Car car){
        String sql = "{CALL UpdateCar(?, ?, ?, ?, ?, ?)}";  // Calling the stored procedure

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, car.getCarID());
            stmt.setString(2, car.getName());
            stmt.setString(3, car.getModel());
            stmt.setString(4, car.getColor());
            stmt.setBoolean(5, car.isAvailable());
            stmt.setDouble(6, car.getChargesPerDay());
            stmt.setBoolean(7, car.isSunroof());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user in the database: " + e.getMessage(), e);
        }
    }
    public void deleteCar(int id){
        String sql = "{CALL DeleteCar(?)}";  // Calling the stored procedure

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user from the database: " + e.getMessage(), e);
        }
    }
    public void setCarStatus(int id, boolean status){
        String sql = "{CALL setCarStatus(?, ?)}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.setBoolean(2, status);
            stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error reading loaned books from the database: " + e.getMessage(), e);
        }
    }

    public void addDriver(Driver driver){
        String addUserSql = "{CALL AddDriver(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(addUserSql)) {

            stmt.setString(1, driver.getFName());
            stmt.setString(2, driver.getLName());
            stmt.setString(3, driver.getEmail());
            stmt.setDate(4, new java.sql.Date(Date.from(driver.getDob().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            stmt.setString(5, driver.getPhone());
            stmt.setString(6, driver.getAddress());
            stmt.setString(7, driver.getGender());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding user to the database: " + e.getMessage(), e);
        }
    }
    public void updateDriver(Driver driver){
        String sql = "{CALL UpdateDriver(?, ?, ?, ?, ?, ?, ?, ?)}";  // Calling the stored procedure

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, driver.getDriverID());
            stmt.setString(2, driver.getFName());
            stmt.setString(3, driver.getLName());
            stmt.setString(4, driver.getEmail());
            stmt.setDate(5, new java.sql.Date(Date.from(driver.getDob().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            stmt.setString(6, driver.getPhone());
            stmt.setString(7, driver.getAddress());
            stmt.setString(8, driver.getGender());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user in the database: " + e.getMessage(), e);
        }
    }
    public void deleteDriver(int id){
        String sql = "{CALL DeleteDriver(?)}";  // Calling the stored procedure

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user from the database: " + e.getMessage(), e);
        }
    }

    public void addBooking(Booking booking){
        String addUserSql = "{CALL AddBooking(?, ?, ?, ?, ?, ?, ?, ?)}";
        if(booking.getDriver() != null) {
            if(booking.getPayment() == null) {
                try (Connection conn = getConnection();
                     CallableStatement stmt = conn.prepareCall(addUserSql)) {

                    stmt.setInt(1, booking.getCar().getCarID());
                    stmt.setInt(2, booking.getUser().getUserID());
                    stmt.setNull(3, java.sql.Types.INTEGER);
                    stmt.setInt(4, booking.getDriver().getDriverID());
                    stmt.setDouble(5, booking.getTotalCharges());
                    stmt.setDate(6, new java.sql.Date(Date.from(booking.getIssueDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                    stmt.setDate(7, new java.sql.Date(Date.from(booking.getReturnDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                    stmt.setBoolean(8, booking.isPaymentPending());

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException("Error adding booking to the database: " + e.getMessage(), e);
                }
            }
            else {
                try (Connection conn = getConnection();
                     CallableStatement stmt = conn.prepareCall(addUserSql)) {

                    stmt.setInt(1, booking.getCar().getCarID());
                    stmt.setInt(2, booking.getUser().getUserID());
                    stmt.setInt(3, booking.getPayment().getPaymentID());
                    stmt.setInt(4, booking.getDriver().getDriverID());
                    stmt.setDouble(5, booking.getTotalCharges());
                    stmt.setDate(6, new java.sql.Date(Date.from(booking.getIssueDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                    stmt.setDate(7, new java.sql.Date(Date.from(booking.getReturnDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                    stmt.setBoolean(8, booking.isPaymentPending());

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException("Error adding booking to the database: " + e.getMessage(), e);
                }
            }
        }
        else{
            if(booking.getPayment() == null){
                try (Connection conn = getConnection();
                     CallableStatement stmt = conn.prepareCall(addUserSql)) {

                    stmt.setInt(1, booking.getCar().getCarID());
                    stmt.setInt(2, booking.getUser().getUserID());
                    stmt.setNull(3, java.sql.Types.INTEGER);
                    stmt.setNull(4, java.sql.Types.INTEGER);
                    stmt.setDouble(5, booking.getTotalCharges());
                    stmt.setDate(6,new java.sql.Date(Date.from(booking.getIssueDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                    stmt.setDate(7, new java.sql.Date(Date.from(booking.getReturnDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                    stmt.setBoolean(8, booking.isPaymentPending());

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException("Error adding booking to the database: " + e.getMessage(), e);
                }
            }
            else{
                try (Connection conn = getConnection();
                     CallableStatement stmt = conn.prepareCall(addUserSql)) {

                    stmt.setInt(1, booking.getCar().getCarID());
                    stmt.setInt(2, booking.getUser().getUserID());
                    stmt.setInt(3, booking.getPayment().getPaymentID());
                    stmt.setNull(4, java.sql.Types.INTEGER);
                    stmt.setDouble(5, booking.getTotalCharges());
                    stmt.setDate(6,new java.sql.Date(Date.from(booking.getIssueDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                    stmt.setDate(7, new java.sql.Date(Date.from(booking.getReturnDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                    stmt.setBoolean(8, booking.isPaymentPending());

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException("Error adding booking to the database: " + e.getMessage(), e);
                }
            }
        }
    }
    public void updateBooking(Booking booking){
        String sql = "{CALL UpdateBooking(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";  // Calling the stored procedure

        if(booking.getDriver() != null) {
            try (Connection conn = getConnection();
                 CallableStatement stmt = conn.prepareCall(sql)) {

                stmt.setInt(1, booking.getBookingID());
                stmt.setInt(2, booking.getCar().getCarID());
                stmt.setInt(3, booking.getUser().getUserID());
                stmt.setInt(4, booking.getPayment().getPaymentID());
                stmt.setInt(5, booking.getDriver().getDriverID());
                stmt.setDouble(6, booking.getTotalCharges());
                stmt.setDate(7, new java.sql.Date(Date.from(booking.getIssueDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                stmt.setDate(8, new java.sql.Date(Date.from(booking.getReturnDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                stmt.setDate(9, new java.sql.Date(Date.from(booking.getCustomerReturnDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                stmt.setBoolean(10, booking.isPaymentPending());

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        else{
            try (Connection conn = getConnection();
                 CallableStatement stmt = conn.prepareCall(sql)) {

                stmt.setInt(1, booking.getBookingID());
                stmt.setInt(2, booking.getCar().getCarID());
                stmt.setInt(3, booking.getUser().getUserID());
                stmt.setInt(4, booking.getPayment().getPaymentID());
                stmt.setNull(5, java.sql.Types.INTEGER);
                stmt.setDouble(6, booking.getTotalCharges());
                stmt.setDate(7, new java.sql.Date(Date.from(booking.getIssueDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                stmt.setDate(8, new java.sql.Date(Date.from(booking.getReturnDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                stmt.setDate(9, new java.sql.Date(Date.from(booking.getCustomerReturnDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
                stmt.setBoolean(10, booking.isPaymentPending());

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }
    public void deleteBooking(int id){
        String sql = "{CALL DeleteBooking(?)}";  // Calling the stored procedure

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting booking from the database: " + e.getMessage(), e);
        }
    }

    public void addPayment(Payment payment){
        String addUserSql = "{CALL AddPayment(?, ?)}";

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(addUserSql)) {

            stmt.setString(1, payment.getModeOfPayment());
            stmt.setDouble(2, payment.getAmount());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding user to the database: " + e.getMessage(), e);
        }
    }
    public void updatePayment(Payment payment){
        String sql = "{CALL UpdatePayment(?, ?, ?)}";  // Calling the stored procedure

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, payment.getPaymentID());
            stmt.setString(2, payment.getModeOfPayment());
            stmt.setDouble(3, payment.getAmount());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    public void deletePayment(int id){
        String sql = "{CALL DeletePayment(?)}";  // Calling the stored procedure

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Payment from the database: " + e.getMessage(), e);
        }
    }
    public void setPaymentStatus(int id, boolean status){
        String sql = "{CALL setPaymentStatus(?, ?)}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.setBoolean(2, status);
            stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error reading loaned books from the database: " + e.getMessage(), e);
        }
    }

}
