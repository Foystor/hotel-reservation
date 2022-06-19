package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    // collections to store and retrieve a reservation
    private final Map<String, IRoom> mapOfRooms = new HashMap<>();    // key: room number  value: IRoom object
    private final Set<Reservation> reservationSet = new HashSet<>();
    private static ReservationService reservationService;

    private ReservationService() {}

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    /**
     * add a room
     * @param room a IRoom object
     */
    public void addRoom(IRoom room) {
        if (!mapOfRooms.containsKey(room.getRoomNumber())) {
            mapOfRooms.put(room.getRoomNumber(), room);
        }
    }


    /**
     * get a room
     * @param roomId the room number
     * @return a IRoom object
     */
    public IRoom getARoom(String roomId) {
        return mapOfRooms.get(roomId);
    }


    /**
     * reserve a room
     * @param customer the customer object
     * @param room the IRoom object
     * @param checkInDate check in date
     * @param checkOutDate check out date
     * @return a reservation object
     */
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationSet.add(reservation);

        return reservation;
    }


    /**
     * Get the collection of available rooms. When all rooms have been booked display recommended rooms.
     * @param checkInDate check in date
     * @param checkOutDate check out date
     * @return a list of available rooms
     */
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> returnRooms = new ArrayList<>();

        if (mapOfRooms.size() != reservationSet.size()) {
            Collection<IRoom> reservedRooms = new HashSet<>();
            for (Reservation reservation : reservationSet) {
                reservedRooms.add(reservation.getRoom());
            }
            for (IRoom room : mapOfRooms.values()) {
                if (!reservedRooms.contains(room)) returnRooms.add(room);
            }
        } else {
            // if all rooms are booked,
            // add seven days to the original dates to see if there is any availability
            returnRooms = findRecommendRooms(checkInDate,checkOutDate);
        }

        return returnRooms;
    }


    /**
     * find the recommendation rooms
     * @param checkIn the user input of check in date
     * @param checkOut the user input of check out date
     * @return a collection of recommendation rooms
     */
    Collection<IRoom> findRecommendRooms(Date checkIn, Date checkOut) {
        Collection<IRoom> rooms = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(checkIn);
        calendar.add(Calendar.DATE,7);
        Date checkInAdded = calendar.getTime();

        calendar.setTime(checkOut);
        calendar.add(Calendar.DATE,7);
        Date checkOutAdded = calendar.getTime();

        // check whether a room should be recommended
        for (Reservation reservation : reservationSet) {
            Date checkInDateOfReservation = reservation.getCheckInDate();
            Date checkOutDateOfReservation = reservation.getCheckOutDate();

            if (checkInAdded.after(checkOutDateOfReservation) || checkOutAdded.before(checkInDateOfReservation)) {
                rooms.add(reservation.getRoom());
            }
        }

        return rooms;
    }


    /**
     * get the reservation list of a specific customer
     * @param customer the customer object
     * @return the customer's reservation list
     */
    public Collection<Reservation> getCustomersReservation(Customer customer) {

        List<Reservation> reservationCollection = new LinkedList<>();

        for (Reservation reservation : reservationSet) {
            if (reservation.getCustomer() == customer) {
                reservationCollection.add(reservation);
            }
        }

        return reservationCollection;
    }


    /**
     * get all the reservations
     */
    public void printAllReservation() {
        if (reservationSet.size() > 0) {
            for (Reservation reservation : reservationSet) {
                System.out.println(reservation);
                System.out.println();
            }
        } else {
            System.out.println("None");
        }
    }

    /**
     * the map of rooms getter
     * @return the map of rooms
     */
    public Map<String, IRoom> getMapOfRooms() {
        return mapOfRooms;
    }


    /**
     * the set of reservations getter
     * @return the set of reservations
     */
    public Set<Reservation> getReservationSet() {
        return reservationSet;
    }
}
