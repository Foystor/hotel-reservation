package menu;

import api.AdminResource;
import api.HotelResource;

import java.util.Scanner;

public class AdminMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final AdminResource adminResource = AdminResource.getInstance();
    private final HotelResource hotelResource = HotelResource.getInstance();

    /**
     * print out the admin menu
     */
    public void printAdminMenu() {
        boolean keepRunning = true;

        while (keepRunning) {
            try {
                System.out.println("------------------------------------------------");
                System.out.println("1. See all Customers");
                System.out.println("2. See all Rooms");
                System.out.println("3. See all Reservations");
                System.out.println("4. Add a Room");
                System.out.println("5. Back to Main Menu");
                System.out.println("------------------------------------------------");
                System.out.println("Please enter your selection");

                int selection = Integer.parseInt(scanner.nextLine());

                switch (selection) {
                    case 1 -> {
                        System.out.println("See all Customers");
                        keepRunning = false;
                        //seeAllCustomers();
                    }
                    case 2 -> {
                        System.out.println("See all Rooms");
                        keepRunning = false;
                        //seeAllRooms();
                    }
                    case 3 -> {
                        System.out.println("See all Reservations");
                        keepRunning = false;
                        //seeAllReservation();
                    }
                    case 4 -> {
                        System.out.println("Add a Room");
                        keepRunning = false;
                        //addARoom();
                    }
                    case 5 -> {
                        System.out.println("Back to Main Menu");
                        keepRunning = false;
                        new MainMenu().printMainMenu();
                        scanner.close();
                    }
                    default -> System.out.println("Please enter a number between 1 and 5");
                }
            } catch (Exception ex) {
                System.out.println("Invalid input: " + ex.getMessage());
            }
        }
    }
}
