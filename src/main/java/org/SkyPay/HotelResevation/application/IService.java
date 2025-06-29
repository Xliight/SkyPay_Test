package org.SkyPay.HotelResevation.application;

import org.SkyPay.HotelResevation.domaine.enums.RoomType;

import java.util.Date;

public interface IService {
    void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight);
    void setUser(int userId, int balance);
    void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut);
    void printAll();
    void printAllUsers();
}
