package org.SkyPay.HotelResevation.application;

import org.SkyPay.HotelResevation.domaine.Booking;
import org.SkyPay.HotelResevation.domaine.Room;
import org.SkyPay.HotelResevation.domaine.User;
import org.SkyPay.HotelResevation.domaine.enums.RoomType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ListIterator;
import java.util.Optional;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class ServiceImpl implements IService {
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Booking> bookings = new ArrayList<>();

    @Override
    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        rooms.stream()
                .filter(r -> r.getNumber() == roomNumber)
                .findFirst()
                .ifPresentOrElse(
                        r -> r.updateRoom(roomType, roomPricePerNight),
                        () -> rooms.add(new Room(roomNumber, roomType, roomPricePerNight))
                );
    }

    @Override
    public void setUser(int userId, int balance) {
        users.stream()
                .filter(u -> u.getId() == userId)
                .findFirst()
                .ifPresentOrElse(
                        u -> u.setBalance(balance),
                        () -> users.add(new User(userId, balance))
                );
    }

    @Override
    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        checkIn = truncateTime(checkIn);
        checkOut = truncateTime(checkOut);

        if (!checkIn.before(checkOut)) {
            throw new IllegalArgumentException("Invalid date range: checkIn must be before checkOut.");
        }

        Optional<User> userOpt = users.stream().filter(u -> u.getId() == userId).findFirst();
        Optional<Room> roomOpt = rooms.stream().filter(r -> r.getNumber() == roomNumber).findFirst();

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found.");
        }
        if (roomOpt.isEmpty()) {
            throw new IllegalArgumentException("Room with number " + roomNumber + " not found.");
        }

        Room room = roomOpt.get();
        User user = userOpt.get();

        Date finalCheckOut = checkOut;
        Date finalCheckIn = checkIn;
        boolean isAvailable = bookings.stream()
                .filter(b -> b.getRoom().getNumber() == roomNumber)
                .noneMatch(b -> !(finalCheckOut.before(b.getCheckIn()) || finalCheckIn.after(b.getCheckOut())));

        if (!isAvailable) {
            throw new IllegalStateException("Room " + roomNumber + " is not available during the requested period.");
        }

        long nights = ChronoUnit.DAYS.between(
                checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                checkOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );
        int totalPrice = (int) (nights * room.getPricePerNight());

        if (user.getBalance() < totalPrice) {
            throw new IllegalStateException("User " + userId + " has insufficient balance.");
        }

        user.deductBalance(totalPrice);
        bookings.add(new Booking(user, room, checkIn, checkOut, totalPrice));
    }

    @Override
    public void printAll() {
        ListIterator<Room> roomIt = rooms.listIterator(rooms.size());
        while (roomIt.hasPrevious()) {
            System.out.println(roomIt.previous());
        }

        ListIterator<Booking> bookingIt = bookings.listIterator(bookings.size());
        while (bookingIt.hasPrevious()) {
            System.out.println(bookingIt.previous());
        }
    }

    @Override
    public void printAllUsers() {
        ListIterator<User> userIt = users.listIterator(users.size());
        while (userIt.hasPrevious()) {
            System.out.println(userIt.previous());
        }
    }

    private Date truncateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
