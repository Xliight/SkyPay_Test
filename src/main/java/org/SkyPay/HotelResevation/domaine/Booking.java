package org.SkyPay.HotelResevation.domaine;

import java.util.Date;

public class Booking {
    private final User user;
    private final Room room;
    private final Date checkIn;
    private final Date checkOut;
    private final int totalPrice;

    public Booking(User user, Room room, Date checkIn, Date checkOut, int totalPrice) {
        this.user = user;
        this.room = room;
        this.checkIn = new Date(checkIn.getTime());
        this.checkOut = new Date(checkOut.getTime());
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public Date getCheckIn() {
        return new Date(checkIn.getTime());
    }

    public Date getCheckOut() {
        return new Date(checkOut.getTime());
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "userId=" + user.getId() +
                ", roomNumber=" + room.getNumber() +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
