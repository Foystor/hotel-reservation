package menu;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
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
                        seeAllCustomers();
                    }
                    case 2 -> {
                        System.out.println("See all Rooms");
                        keepRunning = false;
                        seeAllRooms();
                    }
                    case 3 -> {
                        System.out.println("See all Reservations");
                        keepRunning = false;
                        seeAllReservation();
                    }
                    case 4 -> {
                        System.out.println("Add a Room");
                        keepRunning = false;
                        addARoom();
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


    /**
     * print out all the customers
     */
    public void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();

        if (customers != null && customers.size() > 0) {
            for (Customer customer : customers) {
                System.out.println(customer.toString());
            }
        } else {
            System.out.println("None");
        }
        // back to the admin menu
        printAdminMenu();
    }


    /**
     * print out all the rooms
     */
    public void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (rooms != null && rooms.size() > 0) {
            for (IRoom room : rooms) {
                System.out.println(room.toString());
            }
        } else {
            System.out.println("None");
        }
        // back to the admin menu
        printAdminMenu();
    }


    /**
     * print out all the reservation
     */
    public void seeAllReservation() {
        adminResource.displayAllReservations();
        // back to the admin menu
        printAdminMenu();
    }


    /**
     * add a room
     */
    public void addARoom() {
        boolean keepRunning = true;
        List<IRoom> rooms = new LinkedList<>();

        while (keepRunning) {
            try {
                // add a room to the list
                rooms.add(createARoom(rooms));
                System.out.println("Would you like to add another room? y/n");
                String selection = scanner.nextLine();

                if (selection.equalsIgnoreCase("y")) {
                    continue;
                } else if (selection.equalsIgnoreCase("n")) {
                    // add the rooms
                    adminResource.addRoom(rooms);
                    keepRunning = false;
                    // back to the admin menu
                    printAdminMenu();
                } else {
                    throw new IllegalArgumentException("Please enter y or n");
                }
            } catch (Exception ex) {
                System.out.println("Invalid Input: " + ex.getMessage());
            }
        }
    }


    /**
     * create a room
     * @param rooms the room list created by this turn
     * @return IRoom object
     */
    private IRoom createARoom(Collection<IRoom> rooms) {
        boolean keepRunning = true;
        String roomNumber = null;
        Double roomPrice = null;
        RoomType roomType = null;

        while (keepRunning) {
            try {
                System.out.println("Enter room number");
                try {
                    String input = scanner.nextLine();
                    Integer.parseInt(input);
                    roomNumber = input;
                } catch (Exception ex) {
                    throw new IllegalArgumentException("Please enter a number");
                }

                // check if the room already exists
                for (IRoom room : rooms) {
                    if (room.getRoomNumber().equals(roomNumber)) throw new IllegalArgumentException("Room already exists!");
                }
                if (hotelResource.getRoom(roomNumber) != null) throw new IllegalArgumentException("Room already exists!");

                System.out.println("Enter price per night");
                try {
                    roomPrice = Double.parseDouble(scanner.nextLine());
                } catch (Exception ex) {
                    throw new IllegalArgumentException("Please enter a price");
                }

                System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                int roomTypeNum = Integer.parseInt(scanner.nextLine());
                if (roomTypeNum == 1) {
                    roomType = RoomType.SINGLE;
                } else if (roomTypeNum == 2) {
                    roomType = RoomType.DOUBLE;
                } else {
                    throw new IllegalArgumentException("Please enter 1 or 2");
                }

                keepRunning = false;
            } catch (Exception ex) {
                System.out.println("Invalid Input: " + ex.getMessage());
            }
        }

        if (roomPrice == 0.0) return new FreeRoom(roomNumber, roomType);

        return new Room(roomNumber, roomPrice, roomType);
    }
}
