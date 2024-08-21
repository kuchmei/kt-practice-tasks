package com.deliberate.practice.lesson2.task.optional;

import com.deliberate.practice.exception.ExerciseNotCompletedException;
import com.deliberate.practice.lesson2.task.main.BookingService;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * AdvancedBookingService extends the BookingService to add additional features like waiting lists and booking timeouts.
 */
public class AdvancedBookingService extends BookingService {
    private final Map<String, Map<String, Queue<String>>> waitingLists;  // Show ID -> Seat ID -> Waiting list of users
    private final ScheduledExecutorService scheduler;  // Scheduler for managing booking timeouts

    /**
     * Constructs an AdvancedBookingService with a list of shows, seat configurations, and a scheduler.
     *
     * @param shows     A map where the key is the show ID and the value is the set of available seats.
     * @param scheduler A ScheduledExecutorService for managing timeouts.
     */
    public AdvancedBookingService(Map<String, Set<String>> shows, ScheduledExecutorService scheduler) {
        super(shows);
        this.scheduler = scheduler;
        this.waitingLists = new ConcurrentHashMap<>();
        // Add code to initialize waiting lists for each seat (if necessary)
    }

    /**
     * Books seats for a specific show and adds the user to the waiting list if the seats are already booked.
     *
     * @param showId The ID of the show.
     * @param seats  The list of seats to book.
     * @param user   The user making the booking.
     * @return true if the booking is successful or the user is added to the waiting list, false otherwise.
     */
    public boolean bookSeatsWithWaitingList(String showId, List<String> seats, String user) {
        boolean success = super.bookSeats(showId, seats, user);
        if (!success) {
            for (String seat : seats) {
                waitingLists
                        .computeIfAbsent(showId, k -> new ConcurrentHashMap<>())
                        .computeIfAbsent(seat, k -> new ConcurrentLinkedQueue<>())
                        .add(user);
            }
        }
        return success;
    }

    /**
     * Books a seat for the next user in the waiting list if the seat becomes available.
     *
     * @param showId The ID of the show.
     * @param seat   The seat to assign to the next user in the waiting list.
     */
    private void processWaitingList(String showId, String seat) {
        Queue<String> waitingList = waitingLists.getOrDefault(showId, new ConcurrentHashMap<>()).get(seat);
        if (waitingList != null && !waitingList.isEmpty()) {
            String nextUser = waitingList.poll();
            if (nextUser != null) {
                super.bookSeats(showId, List.of(seat), nextUser);
            }
        }
    }

    /**
     * Cancels a booking and notifies the next user in the waiting list if the seat was already booked.
     *
     * @param showId The ID of the show.
     * @param seats  The list of seats to cancel.
     * @param user   The user cancelling the booking.
     * @return true if the cancellation is successful, false if any seat was not booked by the user.
     */
    public boolean cancelBookingWithWaitingList(String showId, List<String> seats, String user) {
        boolean success = super.cancelBooking(showId, seats, user);
        if (success) {
            for (String seat : seats) {
                processWaitingList(showId, seat);
            }
        }
        return success;
    }

    /**
     * Books seats for a specific show with a timeout. If the booking is not confirmed within the timeout period, the seat becomes available again.
     *
     * @param showId   The ID of the show.
     * @param seats    The list of seats to book.
     * @param user     The user making the booking.
     * @param timeout  The timeout duration.
     * @param timeUnit The unit of time for the timeout.
     * @return true if the booking is successful, false if any seat is already booked.
     */
    public boolean bookSeatsWithTimeout(String showId, List<String> seats, String user, long timeout, TimeUnit timeUnit) {
        boolean success = super.bookSeats(showId, seats, user);
        if (success) {
            for (String seat : seats) {
                scheduler.schedule(() -> {
                    synchronized (this) {
                        if (bookings.get(showId).get(seat).equals(user)) {
                            super.cancelBooking(showId, List.of(seat), user);
                            processWaitingList(showId, seat);
                        }
                    }
                }, timeout, timeUnit);
            }
        }
        return success;
    }

    /**
     * Confirms the booking for a specific seat before the timeout expires.
     *
     * @param showId The ID of the show.
     * @param seat   The seat to confirm.
     * @param user   The user confirming the booking.
     * @return true if the confirmation is successful, false otherwise.
     */
    public boolean confirmBooking(String showId, String seat, String user) {
        synchronized (this) {
            if (bookings.get(showId).get(seat).equals(user)) {
                scheduler.shutdownNow(); // Cancel the timeout task
                return true;
            }
        }
        return false;
    }
}
