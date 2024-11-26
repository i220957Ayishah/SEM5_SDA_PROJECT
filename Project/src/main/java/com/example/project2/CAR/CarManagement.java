package com.example.project2.CAR;
import com.example.project2.Storage.DataBase;

import java.util.ArrayList;

public class CarManagement {
    private static DataBase DB = new DataBase();
    private static ArrayList<Car> carList = new ArrayList<>();

    public CarManagement(DataBase db) {
        DB = db;
        carList = db.readAllCars();
    }

    public static void addCar(String name, String model, String color, boolean available, double chargesPerDay, boolean sunroof) {
        int id = carList.getLast().getCarID()+1;
        Car car = new Car(id, name, model, color, available, chargesPerDay, sunroof );
        carList.add(car);
        DB.addCar(car);
        System.out.println("Car added successfully: " + car.getName());
    }

    public static boolean deleteCar(int carID) {
        for (Car car : carList) {
            if (car.getCarID() == carID) {
                if(car.isAvailable()){
                    carList.remove(car);
                    DB.deleteCar(carID);
                    System.out.println("Car deleted successfully: " + car.getName());
                    return true;
                }
                else {
                    System.out.println("Car is currently booked");
                }
            }
        }
        System.out.println("Car with ID " + carID + " not found.");
        return false;
    }

    public Car searchCarByID(int carID) {
        for (Car car : carList) {
            if (car.getCarID() == carID) {
                System.out.println("Car found: " + car.getName());
                return car;
            }
        }
        System.out.println("Car with ID " + carID + " not found.");
        return null;
    }

    public static Car findCarByID(int carID) {
        for (Car car : carList) {
            if (car.getCarID() == carID) {
                System.out.println("Car found: " + car.getName());
                return car;
            }
        }
        System.out.println("Car with ID " + carID + " not found.");
        return null;
    }

    public ArrayList<Car> getCarsByModel(String model) {
        ArrayList<Car> cars = new ArrayList<>();
        for (Car car : carList) {
            if (car.getModel().equals(model)) {
                cars.add(car);
            }
        }
        return cars;
    }

    public ArrayList<Car> getCarsByColor(String color) {
        ArrayList<Car> cars = new ArrayList<>();
        for (Car car : carList) {
            if (car.getColor().equals(color)) {
                cars.add(car);
            }
        }
        return cars;
    }

    public ArrayList<Car> getCarsByAvailability(boolean availability) {
        return DB.readAvailableCarsList();
    }

    public static boolean updateCar(int carID, String name, String model, String color, boolean available, double chargesPerDay, boolean sunroof) {
        Car car = findCarByID(carID);
        if (car != null) {
            car.setName(name);
            car.setModel(model);
            car.setColor(color);
            car.setAvailable(available);
            car.setChargesPerDay(chargesPerDay);
            car.setSunroof(sunroof);
            System.out.println("Car updated successfully: " + car.getName());
            DB.updateCar(car);
            return true;
        } else {
            System.out.println("Update failed. Car with ID " + carID + " not found.");
            return false;
        }
    }

    public void displayAvailableCars() {
        for(Car car : carList) {
            if(car.isAvailable()) {
                car.displayCar();
            }
        }
    }

    public ArrayList<Car> getCarList() {
        return carList;
    }
}
