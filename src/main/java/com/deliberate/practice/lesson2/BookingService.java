package com.deliberate.practice.lesson2;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * BookingService manages seat reservations for multiple shows in a theater.
 * This class supports concurrent booking, cancellation, and availability checking.
 */
public class BookingService {
    private final Map<String, Set<String>> shows;  // Show ID -> Set of available seats
    private final Map<String, Map<String, String>> bookings;  // Show ID -> Seat -> User
    private final Map<String, Lock> showLocks;  // Show ID -> Lock for synchronization

    /**
     * Constructs a BookingService with a list of shows and their seat configurations.
     *
     * @param shows A map where the key is the show ID and the value is the set of available seats.
     */
    public BookingService(Map<String, Set<String>> shows) {
        this.shows = new ConcurrentHashMap<>(shows);
        this.bookings = new ConcurrentHashMap<>();
        this.showLocks = new ConcurrentHashMap<>();
        for (String showId : shows.keySet()) {
            showLocks.put(showId, new ReentrantLock());
        }
    }

    /**
     * Books seats for a specific show.
     *
     * @param showId The ID of the show.
     * @param seats  The list of seats to book.
     * @param user   The user making the booking.
     * @return true if the booking is successful, false if any seat is already booked.
     */
    public boolean bookSeats(String showId, List<String> seats, String user) {
        Lock lock = showLocks.get(showId);
        lock.lock();
        try {
            Set<String> availableSeats = shows.get(showId);
            for (String seat : seats) {
                if (!availableSeats.contains(seat)) {
                    return false;  // Seat does not exist in the show
                }
            }

            Map<String, String> showBookings = bookings.computeIfAbsent(showId, k -> new ConcurrentHashMap<>());
            for (String seat : seats) {
                if (showBookings.containsKey(seat)) {
                    return false;  // Seat already booked
                }
            }
            for (String seat : seats) {
                showBookings.put(seat, user);
                shows.get(showId).remove(seat);
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Cancels a booking for a specific show.
     *
     * @param showId The ID of the show.
     * @param seats  The list of seats to cancel.
     * @param user   The user cancelling the booking.
     * @return true if the cancellation is successful, false if any seat was not booked by the user.
     */
    public boolean cancelBooking(String showId, List<String> seats, String user) {
        Lock lock = showLocks.get(showId);
        lock.lock();
        try {
            Map<String, String> showBookings = bookings.get(showId);
            if (showBookings == null) return false;

            for (String seat : seats) {
                if (!user.equals(showBookings.get(seat))) {
                    return false;  // Seat was not booked by this user
                }
            }
            for (String seat : seats) {
                showBookings.remove(seat);
                shows.get(showId).add(seat);
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Checks the availability of seats for a specific show.
     *
     * @param showId The ID of the show.
     * @return A set of available seats.
     */
    public Set<String> checkAvailability(String showId) {
        return shows.get(showId);
    }
}
