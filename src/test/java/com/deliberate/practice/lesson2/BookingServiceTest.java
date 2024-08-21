package com.deliberate.practice.lesson2;

import com.deliberate.practice.lesson2.task.BookingService;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingServiceTest {

    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        // Initialize the service with a few shows and seats
        Map<String, Set<String>> shows = new HashMap<>();
        shows.put("Show1", new HashSet<>(Arrays.asList("A1", "A2", "A3", "A4")));
        shows.put("Show2", new HashSet<>(Arrays.asList("B1", "B2", "B3", "B4")));
        shows.put("Show3", new HashSet<>(Arrays.asList("C1", "C2", "C3", "C4")));

        bookingService = new BookingService(shows);
    }

    @Test
    @Order(1)
    public void testBookSingleSeatSuccess() {
        boolean success = bookingService.bookSeats("Show1", List.of("A1"), "User1");
        assertTrue(success, "Booking a single seat should succeed.");
    }

    @Test
    @Order(2)
    public void testBookMultipleSeatsSuccess() {
        boolean success = bookingService.bookSeats("Show1", Arrays.asList("A1", "A2"), "User1");
        assertTrue(success, "Booking multiple seats should succeed.");
    }

    @Test
    @Order(3)
    public void testBookSeatAlreadyBooked() {
        bookingService.bookSeats("Show1", List.of("A1"), "User1");
        boolean success = bookingService.bookSeats("Show1", List.of("A1"), "User2");
        assertFalse(success, "Booking a seat that is already booked should fail.");
    }

    @Test
    @Order(4)
    public void testBookMultipleSeatsWithOneAlreadyBooked() {
        bookingService.bookSeats("Show1", List.of("A1"), "User1");
        boolean success = bookingService.bookSeats("Show1", Arrays.asList("A1", "A2"), "User2");
        assertFalse(success, "Booking multiple seats where one is already booked should fail.");
    }

    @Test
    @Order(5)
    public void testBookMultipleSeatsWithOneNotExisted() {
        boolean success = bookingService.bookSeats("Show1", Arrays.asList("A1", "D2"), "User2");
        assertFalse(success, "Booking multiple seats where one is already booked should fail.");
    }

    @Test
    @Order(6)
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testConcurrentBookSeats() throws InterruptedException {
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        final boolean[] bookingResults = new boolean[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int userId = i;
            final int index = i;
            executorService.submit(() -> {
                bookingResults[index] = bookingService.bookSeats("Show1", Arrays.asList("A1"), "User" + userId);
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();

        // We expect exactly one successful booking
        int successfulBookings = 0;
        for (boolean result : bookingResults) {
            if (result) {
                successfulBookings++;
            }
        }

        // Only one thread should have successfully booked the seat
        assertEquals(1, successfulBookings, "Only one user should have successfully booked seat A1.");
    }

    @Test
    @Order(7)
    public void testCancelBookingSuccess() {
        bookingService.bookSeats("Show1", List.of("A1"), "User1");
        boolean success = bookingService.cancelBooking("Show1", List.of("A1"), "User1");
        assertTrue(success, "Canceling a booking should succeed.");
    }

    @Test
    @Order(8)
    public void testCancelBookingFailWrongUser() {
        bookingService.bookSeats("Show1", List.of("A1"), "User1");
        boolean success = bookingService.cancelBooking("Show1", List.of("A1"), "User2");
        assertFalse(success, "Canceling a booking by a different user should fail.");
    }

    @Test
    @Order(9)
    public void testCancelBookingFailNotBooked() {
        boolean success = bookingService.cancelBooking("Show1", List.of("A1"), "User1");
        assertFalse(success, "Canceling a seat that was not booked should fail.");
    }

    @Test
    @Order(10)
    public void testCancelMultipleSeatsSuccess() {
        bookingService.bookSeats("Show1", Arrays.asList("A1", "A2"), "User1");
        boolean success = bookingService.cancelBooking("Show1", Arrays.asList("A1", "A2"), "User1");
        assertTrue(success, "Canceling multiple seats should succeed.");
    }

    @Test
    @Order(11)
    public void testCancelMultipleSeatsPartialFailure() {
        bookingService.bookSeats("Show1", List.of("A1"), "User1");
        bookingService.bookSeats("Show1", List.of("A2"), "User2");
        boolean success = bookingService.cancelBooking("Show1", Arrays.asList("A1", "A2"), "User1");
        assertFalse(success, "Canceling multiple seats where one was booked by another user should fail.");
    }

    @Test
    @Order(12)
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testConcurrentCancelBooking() throws InterruptedException {
        bookingService.bookSeats("Show1", Arrays.asList("A1"), "User1");

        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        final boolean[] cancellationResults = new boolean[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executorService.submit(() -> {
                cancellationResults[index] = bookingService.cancelBooking("Show1", Arrays.asList("A1"), "User1");
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();

        // We expect exactly one successful cancellation
        int successfulCancellations = 0;
        for (boolean result : cancellationResults) {
            if (result) {
                successfulCancellations++;
            }
        }

        // Only one thread should have successfully canceled the seat
        assertEquals(1, successfulCancellations, "Only one user should have successfully canceled seat A1.");
    }

    @Test
    @Order(13)
    public void testCheckAvailabilityInitial() {
        Set<String> availableSeats = bookingService.checkAvailability("Show1");
        assertEquals(new HashSet<>(Arrays.asList("A1", "A2", "A3", "A4")), availableSeats, "Initial seat availability should match the configured seats.");
    }

    @Test
    @Order(14)
    public void testCheckAvailabilityAfterBooking() {
        bookingService.bookSeats("Show1", Arrays.asList("A1"), "User1");
        Set<String> availableSeats = bookingService.checkAvailability("Show1");
        assertEquals(new HashSet<>(Arrays.asList("A2", "A3", "A4")), availableSeats, "Seat A1 should not be available after booking.");
    }

    @Test
    @Order(15)
    public void testCheckAvailabilityAfterCancellation() {
        bookingService.bookSeats("Show1", Arrays.asList("A1"), "User1");
        bookingService.cancelBooking("Show1", Arrays.asList("A1"), "User1");
        Set<String> availableSeats = bookingService.checkAvailability("Show1");
        assertEquals(new HashSet<>(Arrays.asList("A1", "A2", "A3", "A4")), availableSeats, "Seat A1 should be available again after cancellation.");
    }

    @Test
    @Order(16)
    public void testCheckAvailabilityAfterMultipleBookingsAndCancellations() {
        bookingService.bookSeats("Show1", Arrays.asList("A1", "A2"), "User1");
        bookingService.bookSeats("Show1", Arrays.asList("A3"), "User2");
        bookingService.cancelBooking("Show1", Arrays.asList("A1"), "User1");
        Set<String> availableSeats = bookingService.checkAvailability("Show1");
        assertEquals(new HashSet<>(Arrays.asList("A1", "A4")), availableSeats, "Availability should reflect multiple bookings and cancellations.");
    }

    @Test
    @Order(17)
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testConcurrentCheckAvailability() throws InterruptedException {
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                Set<String> availableSeats = bookingService.checkAvailability("Show1");
                System.out.println("Available seats: " + availableSeats);
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();

        // Ensure no changes have been made to the seat availability
        Set<String> availableSeats = bookingService.checkAvailability("Show1");
        assertEquals(new HashSet<>(Arrays.asList("A1", "A2", "A3", "A4")), availableSeats, "All seats should still be available.");
    }
}
