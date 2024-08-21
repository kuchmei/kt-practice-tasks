package com.deliberate.practice.lesson2.task.optional;

import org.junit.jupiter.api.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdvancedBookingServiceTest {

    private AdvancedBookingService advancedBookingService;
    private ScheduledExecutorService scheduler;

    @BeforeEach
    public void setUp() {
        // Initialize the service with a few shows and seats
        Map<String, Set<String>> shows = new HashMap<>();
        shows.put("Show1", new HashSet<>(Arrays.asList("A1", "A2", "A3", "A4")));
        scheduler = Executors.newScheduledThreadPool(1);

        advancedBookingService = new AdvancedBookingService(shows, scheduler);
    }

    @AfterEach
    public void tearDown() {
        scheduler.shutdown();
    }

    @Test
    @Order(1)
    public void testBookSeatsWithWaitingList() {
        // Test booking seats and handling waiting list
        boolean success = advancedBookingService.bookSeatsWithWaitingList("Show1", List.of("A1"), "User1");
        assertTrue(success, "Booking a seat should succeed.");

        // Try to book the same seat with another user, should be added to waiting list
        success = advancedBookingService.bookSeatsWithWaitingList("Show1", List.of("A1"), "User2");
        assertFalse(success, "Booking a seat that is already booked should add the user to the waiting list.");
    }

    @Test
    @Order(2)
    public void testCancelBookingAndProcessWaitingList() {
        // Test canceling a booking and processing the waiting list
        advancedBookingService.bookSeatsWithWaitingList("Show1", List.of("A1"), "User1");

        // Add User2 to the waiting list
        boolean success = advancedBookingService.bookSeatsWithWaitingList("Show1", List.of("A1"), "User2");
        assertFalse(success, "Booking a seat that is already booked should add the user to the waiting list.");

        // Cancel the booking for User1
        success = advancedBookingService.cancelBookingWithWaitingList("Show1", List.of("A1"), "User1");
        assertTrue(success, "Canceling a booking should succeed.");

        // Verify that User2 has automatically booked the seat
        Set<String> availableSeats = advancedBookingService.checkAvailability("Show1");
        assertFalse(availableSeats.contains("A1"), "Seat A1 should now be booked by User2.");
    }

    @Test
    @Order(3)
    public void testBookSeatWithTimeout() throws InterruptedException {
        // Test booking a seat with a timeout
        boolean success = advancedBookingService.bookSeatsWithTimeout("Show1", List.of("A1"), "User1", 1, TimeUnit.SECONDS);
        assertTrue(success, "Booking a seat with a timeout should succeed.");

        // Wait for the timeout to expire
        Thread.sleep(1500);

        // Verify that the seat is available again
        Set<String> availableSeats = advancedBookingService.checkAvailability("Show1");
        assertTrue(availableSeats.contains("A1"), "Seat A1 should be available again after the timeout expires.");
    }

    @Test
    @Order(4)
    public void testConfirmBookingBeforeTimeout() throws InterruptedException {
        // Test confirming a booking before the timeout expires
        boolean success = advancedBookingService.bookSeatsWithTimeout("Show1", List.of("A1"), "User1", 5, TimeUnit.SECONDS);
        assertTrue(success, "Booking a seat with a timeout should succeed.");

        // Confirm the booking before the timeout expires
        success = advancedBookingService.confirmBooking("Show1", "A1", "User1");
        assertTrue(success, "Confirming the booking before timeout should succeed.");

        // Wait for the original timeout to expire
        Thread.sleep(6000);

        // Verify that the seat is still booked
        Set<String> availableSeats = advancedBookingService.checkAvailability("Show1");
        assertFalse(availableSeats.contains("A1"), "Seat A1 should still be booked after the original timeout expires.");
    }
}
