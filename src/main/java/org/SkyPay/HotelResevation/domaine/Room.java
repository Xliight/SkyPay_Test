package org.SkyPay.HotelResevation.domaine;

import org.SkyPay.HotelResevation.domaine.enums.RoomType;

public class Room {
    private final int number;
    private RoomType type;
    private int pricePerNight;

    public Room(int number, RoomType type, int pricePerNight) {
        this.number = number;
        this.type = type;
        this.pricePerNight = pricePerNight;
    }

    public int getNumber() {
        return number;
    }

    public RoomType getType() {
        return type;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void updateRoom(RoomType type, int pricePerNight) {
        this.type = type;
        this.pricePerNight = pricePerNight;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", type=" + type +
                ", pricePerNight=" + pricePerNight +
                '}';
    }
}
