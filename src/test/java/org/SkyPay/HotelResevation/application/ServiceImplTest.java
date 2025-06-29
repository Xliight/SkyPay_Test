package org.SkyPay.HotelResevation.application;

import org.SkyPay.HotelResevation.domaine.enums.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ServiceImplTest {

    private IService service;
    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        service = new ServiceImpl();
        sdf = new SimpleDateFormat("yyyy-MM-dd");

        service.setRoom(101, RoomType.STANDARD, 100);
        service.setRoom(102, RoomType.JUNIOR, 150);

        service.setUser(1, 500);
        service.setUser(2, 200);
    }

    @Test
    void testSuccessfulBooking() throws Exception {
        Date checkIn = sdf.parse("2025-07-01");
        Date checkOut = sdf.parse("2025-07-05");

        assertDoesNotThrow(() -> service.bookRoom(1, 101, checkIn, checkOut));
    }

    @Test
    void testBookingInsufficientBalance() throws Exception {
        Date checkIn = sdf.parse("2025-07-10");
        Date checkOut = sdf.parse("2025-07-15");

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> service.bookRoom(2, 102, checkIn, checkOut));
        assertEquals("User 2 has insufficient balance.", ex.getMessage());
    }

    @Test
    void testBookingRoomUnavailable() throws Exception {
        Date firstCheckIn = sdf.parse("2025-07-01");
        Date firstCheckOut = sdf.parse("2025-07-05");

        service.bookRoom(1, 101, firstCheckIn, firstCheckOut);

        Date overlapCheckIn = sdf.parse("2025-07-03");
        Date overlapCheckOut = sdf.parse("2025-07-06");

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> service.bookRoom(1, 101, overlapCheckIn, overlapCheckOut));
        assertEquals("Room 101 is not available during the requested period.", ex.getMessage());
    }

    @Test
    void testInvalidDateRange() throws Exception {
        Date checkIn = sdf.parse("2025-08-10");
        Date checkOut = sdf.parse("2025-08-05");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.bookRoom(1, 102, checkIn, checkOut));
        assertEquals("Invalid date range: checkIn must be before checkOut.", ex.getMessage());
    }

    @Test
    void testUserNotFound() throws Exception {
        Date checkIn = sdf.parse("2025-07-01");
        Date checkOut = sdf.parse("2025-07-05");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.bookRoom(99, 101, checkIn, checkOut));
        assertEquals("User with ID 99 not found.", ex.getMessage());
    }

    @Test
    void testRoomNotFound() throws Exception {
        Date checkIn = sdf.parse("2025-07-01");
        Date checkOut = sdf.parse("2025-07-05");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.bookRoom(1, 999, checkIn, checkOut));
        assertEquals("Room with number 999 not found.", ex.getMessage());
    }
}