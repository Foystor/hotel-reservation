package model;

import java.util.Objects;

public class Room implements IRoom {

    protected final String roomNumber;
    protected final Double roomPrice;
    protected final RoomType roomType;

    public Room(String roomNumber,Double roomPrice,RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return roomPrice;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return roomPrice == 0.0;
    }

    @Override
    public String toString() {
        String roomTypeDetail = (roomType == RoomType.SINGLE) ? "Single bed" : "Double bed";
        return "Room: " + roomNumber + " - " + "Type: " +  roomTypeDetail + " - " + "Price: $" + roomPrice + " per night";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber) && Objects.equals(roomPrice, room.roomPrice) && roomType == room.roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, roomPrice, roomType);
    }
}
