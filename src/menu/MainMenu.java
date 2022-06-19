package menu;

import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Reservation;
import util.Catcher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
                        findARoom();
                    }
                    case 2 -> {
                        System.out.println("See my reservation");
                        keepRunning = false;
                        seeMyReservation();
                    }
                    case 3 -> {
                        System.out.println("Create an account");
                        keepRunning = false;
                        createAccount();
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


    /**
     * enter the check in date and checkout date to find available rooms
     */
    public void findARoom() {
        boolean keepRunning = true;

        while (keepRunning) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

                // get the check in date
                System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2022");
                String checkInDateString = scanner.nextLine();
                // validate the date
                Catcher.validateDate(checkInDateString);
                Date checkInDate = formatter.parse(checkInDateString);
                // check if it is a past date
                if (checkInDate.before(new Date())) throw new IllegalArgumentException("The date is a past date!");

                // get the checkout date
                System.out.println("Enter CheckOut Date mm/dd/yyyy example 02/08/2022");
                String checkOutDateString = scanner.nextLine();
                // validate the date
                Catcher.validateDate(checkOutDateString);
                Date checkOutDate = formatter.parse(checkOutDateString);
                if (checkOutDate.before(checkInDate)) throw new IllegalArgumentException("The check in date is later than the checkout date!");

                Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate,checkOutDate);
                if (availableRooms == null || availableRooms.size() == 0) {
                    System.out.println("No Rooms!");
                    // back to the main menu
                    printMainMenu();
                } else {
                    for (IRoom room : availableRooms) {
                        System.out.println(room.toString());
                    }
                    // tell the customer if those are recommended rooms,
                    // otherwise, help customer reserve a room
                    if (adminResource.getAllRooms().size() != adminResource.getAllReservations().size()) {
                        reserveARoom(checkInDate,checkOutDate,availableRooms);
                    } else {
                        Calendar calendar = Calendar.getInstance();

                        calendar.setTime(checkInDate);
                        calendar.add(Calendar.DATE,7);
                        Date checkInAdded = calendar.getTime();

                        calendar.setTime(checkOutDate);
                        calendar.add(Calendar.DATE,7);
                        Date checkOutAdded = calendar.getTime();

                        System.out.println("(Recommended rooms from " + formatter.format(checkInAdded) + " to " + formatter.format(checkOutAdded) + ")");
                        printMainMenu();
                    }
                }
                keepRunning = false;
            } catch (Exception ex) {
                System.out.println("Invalid input: " + ex.getMessage());
            }
        }
    }


    /**
     * reserve a room
     * @param checkInDate
     * @param checkOutDate
     * @param availableRooms
     */
    public void reserveARoom(Date checkInDate, Date checkOutDate, Collection<IRoom> availableRooms) {
        boolean keepRunning = true;

        while (keepRunning) {
            try {
                System.out.println("Would you like to book a room? y/n");
                String bookSelection = scanner.nextLine();

                // if book a room
                if (bookSelection.equalsIgnoreCase("y")) {
                    System.out.println("Do you have an account? y/n");
                    String haveAccSelection = scanner.nextLine();
                    String email;

                    // if not have an account
                    if (haveAccSelection.equalsIgnoreCase("n")) {
                        email = createAccount();
                        // validate the email
                        Catcher.validateEmail(email);

                    } else if (haveAccSelection.equalsIgnoreCase("y")) {
                        System.out.println("Enter Email format: name@domain.com");
                        email = scanner.nextLine();
                        // validate the email
                        Catcher.validateEmail(email);
                        // check if the account exists
                        if (adminResource.getCustomer(email) == null) {
                            throw new IllegalArgumentException("This account does not exist!");
                        }
                    } else {
                        throw new IllegalArgumentException("Please enter y or n");
                    }

                    // reserve a room
                    System.out.println("What room number would you like to reserve");
                    String roomNumber = scanner.nextLine();
                    if (!availableRooms.contains(hotelResource.getRoom(roomNumber))) {
                        throw new IllegalArgumentException("Please enter the correct room number");
                    }

                    IRoom room = hotelResource.getRoom(roomNumber);
                    Reservation reservation = hotelResource.bookARoom(email,room,checkInDate,checkOutDate);
                    System.out.println(reservation.toString());

                } else if (!bookSelection.equalsIgnoreCase("n")) {
                    throw new IllegalArgumentException("Please enter y or n");
                }

                keepRunning = false;
                // back to the main menu
                printMainMenu();
            } catch (Exception ex) {
                System.out.println("Invalid input: " + ex.getMessage());
            }
        }
    }


    /**
     * print out all my reservations
     */
    public void seeMyReservation() {
        boolean keepRunning = true;

        while (keepRunning) {
            try {
                System.out.println("Enter Email format: name@domain.com");
                String email = scanner.nextLine();
                // validate the email
                Catcher.validateEmail(email);
                Collection<Reservation> myReservations = hotelResource.getCustomersReservations(email);

                if (myReservations != null && myReservations.size() > 0) {
                    for (Reservation reservation : myReservations) {
                        System.out.println(reservation);
                        System.out.println();
                    }
                } else {
                    System.out.println("None");
                }
                keepRunning = false;
                // back to the main menu
                printMainMenu();
            } catch (Exception ex) {
                System.out.println("Invalid input: " + ex.getMessage());
            }
        }
    }


    /**
     * create an account
     * @return email
     */
    public String createAccount() {
        boolean keepRunning = true;
        String email = null;

        while (keepRunning) {
            try {
                System.out.println("Enter first name");
                String firstName = scanner.nextLine();
                System.out.println("Enter last name");
                String lastName = scanner.nextLine();
                System.out.println("Enter Email format: name@domain.com");
                email = scanner.nextLine();

                // check if the email already exists
                if (adminResource.getCustomer(email) != null) {
                    throw new IllegalArgumentException("Duplicate email!");
                } else {
                    hotelResource.createACustomer(email, firstName, lastName);
                }

                keepRunning = false;
            } catch (Exception ex) {
                System.out.println("Invalid input: " + ex.getMessage());
            }
        }
        return email;
    }
}
