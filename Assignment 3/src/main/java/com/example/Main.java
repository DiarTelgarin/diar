package com.example;

import Admin.AdminPanel;
import com.example.User.email;
import com.example.Vehicle.car;
import com.example.Vehicle.motorbike;
import com.example.Vehicle.truck;
import com.example.Vehicle.vehicleCatalog;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the system!");
            System.out.println("1. Admin Panel");
            System.out.println("2. Register a new user and add vehicles");
            System.out.println("3. Show available vehicles");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                AdminPanel.showAdminMenu();
            } else if (choice == 2) {
                registerUserAndAddVehicles(scanner);
            } else if (choice == 3) {
                vehicleCatalog.showAllVehicles();
            } else if (choice == 4) {
                System.out.println("Exiting system...");
                break;
            } else {
                System.out.println("Invalid choice! Try again.");
            }
        }

        scanner.close();
    }

    private static void registerUserAndAddVehicles(Scanner scanner) {
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

            email user = new email(0, name, surname, userEmail, password, false);
            user.saveToDatabase();

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
            scanner.nextLine();

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

            System.out.println("\nData saved successfully!");

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
