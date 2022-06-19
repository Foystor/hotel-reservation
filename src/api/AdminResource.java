package api;

import model.Reservation;
import service.CustomerService;
import service.ReservationService;
import model.Customer;
import model.IRoom;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AdminResource {

    private final ReservationService reservationService = ReservationService.getInstance();
    private final CustomerService customerService = CustomerService.getInstance();
    private static AdminResource adminResource;

    private AdminResource() {};

    public static AdminResource getInstance() {
        if (adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    /**
     * find a specific customer
     * @param email the email of the customer
     * @return customer object
     */
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }


    /**
     * add a room
     * @param rooms a room list to add
     */
    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }


    /**
     * get all the rooms
     * @return a list of rooms
     */
    public Collection<IRoom> getAllRooms() {
        return reservationService.getMapOfRooms().values();
    }


    /**
     * get all the reservations
     * @return
     */
    public Set<Reservation> getAllReservations() {
        return reservationService.getReservationSet();
    }


    /**
     * get all the customers
     * @return a list of customers
     */
    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }


    /**
     * print out all the reservations
     */
    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
