# BookingService Implementation Task

## Objective
Implement the `BookingService` class to manage seat reservations for multiple shows in a theater. The service must support concurrent booking, cancellation, and availability checking, ensuring thread safety and correctness.

---

## High-Level Design (HLD)

### 1. Method: `boolean bookSeats(String showId, List<String> seats, String user)`

**Purpose:**  
The `bookSeats` method is responsible for allowing a user to book multiple seats for a specific show. The method must ensure that all the requested seats exist and are not already booked. If any seat does not exist or is already booked, the booking should fail, returning `false`. Otherwise, the booking should succeed, returning `true`.

**Flow:**
1. **Synchronization:** Acquire a lock specific to the show to ensure thread safety.
2. **Validation:** Check if all requested seats exist in the show's seating arrangement.
3. **Conflict Detection:** Verify that none of the requested seats are already booked.
4. **Execution:** If all checks pass, book the seats by associating them with the user.
5. **Finalization:** Release the lock and return the booking result.

### 2. Method: `boolean cancelBooking(String showId, List<String> seats, String user)`

**Purpose:**  
The `cancelBooking` method allows a user to cancel their booking for multiple seats in a specific show. The method must ensure that the seats exist, were booked by the user, and are eligible for cancellation. If these conditions are met, the seats should be made available again, and the method should return `true`. If any condition is not met, the cancellation should fail, returning `false`.

**Flow:**
1. **Synchronization:** Acquire a lock specific to the show to ensure thread safety.
2. **Validation:** Check if all seats exist and were booked by the requesting user.
3. **Conflict Detection:** Ensure that the seats are booked by the user attempting to cancel.
4. **Execution:** If all checks pass, cancel the bookings and update availability.
5. **Finalization:** Release the lock and return the cancellation result.

### 3. Method: `Set<String> checkAvailability(String showId)`

**Purpose:**  
The `checkAvailability` method provides a way to check the currently available seats for a specific show. This method offers a read-only view of the available seats, allowing users to see which seats are still unbooked.

**Flow:**
1. **Data Access:** Retrieve the current set of available seats for the specified show.
2. **Return Data:** Provide the set of available seats without altering the internal state.

---

## Software Design Description (SDD)

### 1. Method: `boolean bookSeats(String showId, List<String> seats, String user)`

**Objective:**  
Ensure that the method only allows booking of seats that exist in the show and are not already booked.

**Steps:**

1. **Acquire Lock for the Show:**
    - Retrieve the lock for the given `showId` from the `showLocks` map.
    - Acquire the lock using `lock.lock()` to prevent other threads from modifying bookings for this show concurrently.

2. **Check Existence of Seats:**
    - Retrieve the set of available seats for the `showId` from the `shows` map.
    - Iterate over the `seats` list provided in the method arguments.
    - For each seat, check if it exists in the `availableSeats` set:
        - If any seat does not exist, return `false` immediately.

3. **Check if Seats are Already Booked:**
    - Retrieve or initialize the booking map for the show using `bookings.computeIfAbsent(showId, k -> new ConcurrentHashMap<>())`.
    - Iterate over the `seats` list again.
    - For each seat, check if it is already present in the `showBookings` map:
        - If any seat is already booked, return `false`.

4. **Book the Seats:**
    - If all seats exist and none are booked, iterate over the `seats` list once more.
    - For each seat:
        - Add it to the `showBookings` map, associating it with the `user`.
        - Remove the seat from the `availableSeats` set in the `shows` map.

5. **Release the Lock and Return Success:**
    - Release the lock using `lock.unlock()`.
    - Return `true` to indicate that the booking was successful.

### 2. Method: `boolean cancelBooking(String showId, List<String> seats, String user)`

**Objective:**  
Allow users to cancel their bookings for seats that exist in the show and that they have booked themselves.

**Steps:**

1. **Acquire Lock for the Show:**
    - Retrieve the lock for the given `showId` from the `showLocks` map.
    - Acquire the lock using `lock.lock()`.

2. **Check Existence and Ownership of Seats:**
    - Retrieve the booking map for the show using `bookings.get(showId)`.
    - If the `showBookings` map is `null`, return `false` (no bookings exist for this show).
    - Iterate over the `seats` list.
    - For each seat:
        - Check if the seat was booked by the `user`.
        - If the seat is not booked by the `user` or does not exist, return `false`.

3. **Cancel the Seats:**
    - If all seats were booked by the `user`, iterate over the `seats` list again.
    - For each seat:
        - Remove it from the `showBookings` map.
        - Add the seat back to the `availableSeats` set in the `shows` map.

4. **Release the Lock and Return Success:**
    - Release the lock using `lock.unlock()`.
    - Return `true` to indicate that the cancellation was successful.

### 3. Method: `Set<String> checkAvailability(String showId)`

**Objective:**  
Provide a way to check the available seats for a specific show.

**Steps:**

1. **Return the Set of Available Seats:**
    - Simply return the set of available seats for the given `showId` from the `shows` map.
    - No synchronization is needed as this operation is read-only and `ConcurrentHashMap` and `HashSet` are thread-safe for this use case.
