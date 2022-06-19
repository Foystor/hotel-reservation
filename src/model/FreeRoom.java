package model;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber,RoomType roomType){
        super(roomNumber,0.0, roomType);
    }

    @Override
    public String toString() {
        String roomTypeDetail = (roomType == RoomType.SINGLE) ? "Single bed" : "Double bed";
        return "Room: " + roomNumber + " - " + "Type: " +  roomTypeDetail + " - FREE!";
    }
}
