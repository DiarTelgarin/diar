import system.RentalSystem;
import vehicles.Car;
import vehicles.Motorbike;
import vehicles.Vehicle;

public class Main {
    public static void main(String[] args) {
        RentalSystem rentalSystem = new RentalSystem();

        Car car1 = new Car("C001", "Toyota", 5, "Camry 80");
        Car car2 = new Car("C002", "BMW", 4, "M5 F10");
        Motorbike bike1 = new Motorbike("B001", "Yamaha", 150, true);
        Motorbike bike2 = new Motorbike("B002", "Harley", 1200, false);

        rentalSystem.addVehicle(car1);
        rentalSystem.addVehicle(car2);
        rentalSystem.addVehicle(bike1);
        rentalSystem.addVehicle(bike2);

        System.out.println("All vehicles in the system:");
        rentalSystem.displayAllVehicles();

        System.out.println("Searching for vehicle ID 'C001':");
        Vehicle searchedVehicle = rentalSystem.searchVehicleByID("C001");
        if (searchedVehicle != null) {
            searchedVehicle.displayDetails();
        } else {
            System.out.println("Vehicle not found.");
        }

        System.out.println("Renting vehicle ID 'C001':");
        if (rentalSystem.rentVehicle("C001")) {
            System.out.println("Vehicle rented successfully.");
        } else {
            System.out.println("Vehicle could not be rented.");
        }

        System.out.println("Returning vehicle ID 'C001':");
        if (rentalSystem.returnVehicle("C001")) {
            System.out.println("Vehicle returned successfully.");
        } else {
            System.out.println("Vehicle could not be returned.");
        }

        System.out.println("Displaying rental count for vehicle 'C001':");
        System.out.println("C001 has been rented " + car1.getRentalCount() + " times.");
    }
}