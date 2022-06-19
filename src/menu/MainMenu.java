package menu;

import api.AdminResource;
import api.HotelResource;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final AdminResource adminResource = AdminResource.getInstance();
    private final HotelResource hotelResource = HotelResource.getInstance();

    /**
     * print out the main menu
     */
    public void printMainMenu() {
        boolean keepRunning = true;

        while (keepRunning) {
            try {
                System.out.println("------------------------------------------------");
                System.out.println("1. Find and reserve a room");
                System.out.println("2. See my reservation");
                System.out.println("3. Create an account");
                System.out.println("4. Admin");
                System.out.println("5. Exit");
                System.out.println("------------------------------------------------");
                System.out.println("Please enter your selection");

                int selection = Integer.parseInt(scanner.nextLine());

                switch (selection) {
                    case 1 -> {
                        System.out.println("Find and reserve a room");
                        keepRunning = false;
                        //findARoom();
                    }
                    case 2 -> {
                        System.out.println("See my reservation");
                        keepRunning = false;
                        //seeMyReservation();
                    }
                    case 3 -> {
                        System.out.println("Create an account");
                        keepRunning = false;
                        //createAccount();
                        // back to the main menu
                        printMainMenu();
                    }
                    case 4 -> {
                        System.out.println("Admin menu");
                        keepRunning = false;
                        //new AdminMenu().printAdminMenu();
                    }
                    case 5 -> {
                        keepRunning = false;
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
