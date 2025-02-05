import Admin.AdminPanel;
import User.email;
import Vehicle.car;
import Vehicle.motorbike;
import Vehicle.truck;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the system!");
        System.out.println("1. Admin Panel");
        System.out.println("2. Register a new user and add vehicles");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            AdminPanel.showAdminMenu();
            return;
        }

        try {
            System.out.println("Enter user details:");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Surname: ");
            String surname = scanner.nextLine();
            System.out.print("Email: ");
            String userEmail = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            email user = new email(0, name, surname, userEmail, password);
            user.saveToDatabase();

            // Ввод данных автомобиля
            System.out.println("\nEnter Car details:");
            System.out.print("Brand: ");
            String carBrand = scanner.nextLine();
            System.out.print("Color: ");
            String carColor = scanner.nextLine();
            System.out.print("Fuel Type: ");
            String carFuelType = scanner.nextLine();
            System.out.print("Seat Count: ");
            int carSeatCount = scanner.nextInt();
            System.out.print("Vehicle Count: ");
            int carVehicleCount = scanner.nextInt();
            scanner.nextLine(); // Очистка ввода

            car car1 = new car(0, carBrand, 0, true, carVehicleCount, carSeatCount, carColor, carFuelType);
            car1.saveToDatabase();

            System.out.println("\nEnter Motorbike details:");
            System.out.print("Brand: ");
            String bikeBrand = scanner.nextLine();
            System.out.print("Max Speed: ");
            int maxSpeed = scanner.nextInt();
            System.out.print("Engine Capacity: ");
            int engineCapacity = scanner.nextInt();
            System.out.print("Include Helmet? (true/false): ");
            boolean hasHelmet = scanner.nextBoolean();
            System.out.print("Vehicle Count: ");
            int bikeVehicleCount = scanner.nextInt();
            scanner.nextLine();

            motorbike bike = new motorbike(0, bikeBrand, 0, true, bikeVehicleCount, maxSpeed, hasHelmet, engineCapacity);
            bike.saveToDatabase();

            System.out.println("\nEnter Truck details:");
            System.out.print("Brand: ");
            String truckBrand = scanner.nextLine();
            System.out.print("Max Load (kg): ");
            int maxLoad = scanner.nextInt();
            System.out.print("Include Trailer? (true/false): ");
            boolean withTrailer = scanner.nextBoolean();
            System.out.print("Vehicle Count: ");
            int truckVehicleCount = scanner.nextInt();
            scanner.nextLine();

            truck truck1 = new truck(0, truckBrand, 0, true, truckVehicleCount, maxLoad, withTrailer);
            truck1.saveToDatabase();

            System.out.println("\nFetching data from the database...");

            car fetchedCar = (car) car.getFromDatabase(1);
            if (fetchedCar != null) {
                System.out.println("\nFetched Car:");
                fetchedCar.showVehicleInfo();
            }

            motorbike fetchedBike = motorbike.getFromDatabase(2);
            if (fetchedBike != null) {
                System.out.println("\nFetched Motorbike:");
                fetchedBike.showVehicleInfo();
            }

            truck fetchedTruck = truck.getFromDatabase(3);
            if (fetchedTruck != null) {
                System.out.println("\nFetched Truck:");
                fetchedTruck.showVehicleInfo();
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
