package org.SkyPay.HotelResevation;

import org.SkyPay.HotelResevation.application.IService;
import org.SkyPay.HotelResevation.application.ServiceImpl;
import org.SkyPay.HotelResevation.domaine.enums.RoomType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        IService service = new ServiceImpl();

        // Date formatter to parse date strings
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Setup rooms
            service.setRoom(101, RoomType.STANDARD, 100);
            service.setRoom(102, RoomType.JUNIOR, 150);

            // Setup users
            service.setUser(1, 500);  // User with balance 500
            service.setUser(2, 200);  // User with balance 200

            // Bookings - correct case
            Date checkIn1 = sdf.parse("2025-07-01");
            Date checkOut1 = sdf.parse("2025-07-05");

            service.bookRoom(1, 101, checkIn1, checkOut1);
            System.out.println("User 1 booked room 101 successfully.");

            // Booking with insufficient balance
            Date checkIn2 = sdf.parse("2025-07-10");
            Date checkOut2 = sdf.parse("2025-07-15");

            try {
                service.bookRoom(2, 102, checkIn2, checkOut2);
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());  // Expected: User 2 has insufficient balance.
            }

            // Booking overlapping dates for same room - should fail
            try {
                service.bookRoom(1, 101, sdf.parse("2025-07-03"), sdf.parse("2025-07-06"));
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());  // Expected: Room 101 is not available during the requested period.
            }

            // Booking with invalid date range (checkIn after checkOut)
            try {
                service.bookRoom(1, 102, sdf.parse("2025-08-10"), sdf.parse("2025-08-05"));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());  // Expected: Invalid date range
            }

            // Print all users, rooms and bookings
            System.out.println("\nAll users:");
            service.printAllUsers();

            System.out.println("\nAll rooms and bookings:");
            service.printAll();

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
