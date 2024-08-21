# AdvancedBookingService Implementation Task

## Objective
Extend the `BookingService` class to create an `AdvancedBookingService` that adds support for waiting lists and booking timeouts without modifying the existing methods.

## High-Level Design

### 1. Waiting List Feature
- **Purpose:** Implement a waiting list for each seat in a show. If a user attempts to book an already occupied seat, they are added to a waiting list. When the seat becomes available, the first user in the queue automatically books it.
- **Components:**
  - `Map<String, Map<String, Queue<String>>> waitingLists;` - A map of show IDs to seat IDs and their respective waiting lists.
  - Add `bookSeatsWithWaitingList` to handle waiting list management.
  - Add `cancelBookingWithWaitingList` to process the waiting list when a seat is canceled.

### 2. Booking Timeout Feature
- **Purpose:** Add a timeout mechanism to bookings. If a user does not confirm their booking within a specified period, the seat automatically becomes available again.
- **Components:**
  - `ScheduledExecutorService scheduler;` - A scheduler to handle booking timeouts.
  - Add `bookSeatsWithTimeout` method that schedules a task to release the seat after a timeout if not confirmed.
  - Add `confirmBooking` method to allow users to confirm their booking before the timeout expires.

## Software Design Description

### 1. `AdvancedBookingService` Class
- **Extends:** `BookingService`
- **Fields:**
  - `Map<String, Map<String, Queue<String>>> waitingLists;`
  - `ScheduledExecutorService scheduler;`
- **Methods:**
  - `boolean bookSeatsWithTimeout(String showId, List<String> seats, String user, long timeout, TimeUnit timeUnit)`
  - `boolean bookSeatsWithWaitingList(String showId, List<String> seats, String user)`
  - `boolean confirmBooking(String showId, String seat, String user)`
  - `boolean cancelBookingWithWaitingList(String showId, List<String> seats, String user)`
  - `void processWaitingList(String showId, String seat)`

### 2. Testing
- Implement tests for the waiting list and timeout features in `AdvancedBookingServiceTest`.
- Verify that the seat is rebooked for the next user in the waiting list when it becomes available.
- Verify that bookings with timeouts are released after the timeout expires if not confirmed.
- Ensure that `confirmBooking` prevents the timeout from releasing the seat if the user confirms in time.
