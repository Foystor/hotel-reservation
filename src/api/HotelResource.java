package api;

import service.CustomerService;
import service.ReservationService;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class HotelResource {

    private final ReservationService reservationService = ReservationService.getInstance();
    private final CustomerService customerService = CustomerService.getInstance();
    private static HotelResource hotelResource;

    private HotelResource() {};

    public static HotelResource getInstance() {
        if (hotelResource == null) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    /**
     * get a specific customer
     * @param email
     * @return customer object
     */
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }


    /**
     * create a customer
     * @param email
     * @param firstName
     * @param lastName
     */
    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }


    /**
     * get a room
     * @param roomNumber room number
     * @return IRoom object
     */
    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }


    /**
     * book a room
     * @param customerEmail
     * @param room IRoom object
     * @param checkInDate
     * @param checkOutDate
     * @return
     */
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){

        Customer customer = customerService.getMapOfCustomers().get(customerEmail);

        return reservationService.reserveARoom(customer,room,checkInDate,checkOutDate);
    }


    /**
     * get reservation list of a specific customer
     * @param customerEmail
     * @return a reservation list of a specific customer
     */
    public Collection<Reservation> getCustomersReservations(String customerEmail){

        Customer customer = customerService.getMapOfCustomers().get(customerEmail);

        return reservationService.getCustomersReservation(customer);
    }


    /**
     * get the collection of available rooms.
     * If there are no available rooms for the customer's date range,
     * a search will be performed that displays recommended rooms on alternative dates
     *
     * @param checkIn check in date
     * @param checkOut check out date
     * @return a list of available rooms
     */
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn,checkOut);
    }
}
