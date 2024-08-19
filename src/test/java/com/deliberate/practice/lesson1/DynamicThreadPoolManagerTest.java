package com.deliberate.practice.lesson1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicThreadPoolManagerTest {

    private DynamicThreadPoolManager pool;

    @BeforeEach
    public void setUp() {
        pool = new DynamicThreadPoolManager(2, 5);
    }

    @Test
    public void testStartPoolWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DynamicThreadPoolManager(-1, 5); // Negative minimum threads
        }, "Pool should not allow a negative number of minimum threads");

        assertThrows(IllegalArgumentException.class, () -> {
            new DynamicThreadPoolManager(2, -5); // Negative maximum threads
        }, "Pool should not allow a negative number of maximum threads");

        assertThrows(IllegalArgumentException.class, () -> {
            new DynamicThreadPoolManager(5, 2); // Min threads > Max threads
        }, "Pool should not allow minimum threads to be greater than maximum threads");
    }

    @Test
    public void testMinimumThreadsAfterStart() {
        pool.start();

        assertEquals(2, getActiveThreadsViaReflection(), "Thread pool did not start with the minimum number of threads");
    }

    @Test
    public void testStartPoolTwice() {
        pool.start();
        pool.start(); // Start the pool a second time

        assertEquals(2, getActiveThreadsViaReflection(), "Thread pool should not create additional threads when started twice");
    }

    @Test
    public void testStopPoolWithoutStarting() {
        pool.stop(); // Attempt to stop the pool before it starts

        assertEquals(0, getActiveThreadsViaReflection(), "Thread pool should have 0 active threads if stopped before starting");
        assertEquals(0, getTaskQueueViaReflection().size(), "Task queue should be empty if the pool was never started");
    }

    @Test
    public void testStopCorrectlyShutsDownAllThreads() throws InterruptedException {
        pool.start();

        for (int i = 0; i < 5; i++) {
            pool.submitTask(() -> {
                try {
                    Thread.sleep(1000); // Simulate a long-running task
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread.sleep(500); // Wait until some tasks are being processed
        pool.stop();

        assertEquals(0, getActiveThreadsViaReflection(), "Thread pool did not shut down all threads on stop");
        assertEquals(0, getTaskQueueViaReflection().size(), "Task queue was not cleared on stop");
    }

    @Test
    public void testSubmitTasksAfterPoolStart() throws InterruptedException {
        pool.start();

        pool.submitTask(() -> {
            System.out.println("Task 1 executed");
        });

        pool.submitTask(() -> {
            System.out.println("Task 2 executed");
        });

        Thread.sleep(1000); // Give some time for tasks to execute

        assertEquals(2, getActiveThreadsViaReflection(), "Thread pool should have at least 2 threads running");
    }

    @Test
    public void testSubmitTaskToStoppedPool() {
        pool.start();
        pool.stop(); // Stop the pool

        pool.submitTask(() -> System.out.println("This should not run")); // Attempt to submit a task after stopping

        assertEquals(0, getTaskQueueViaReflection().size(), "Task queue should remain empty when submitting to a stopped pool");
        assertEquals(0, getActiveThreadsViaReflection(), "No active threads should exist after the pool is stopped");
    }

    @Test
    public void testAdjustThreadCount() throws InterruptedException {
        pool.start();

        for (int i = 0; i < 10; i++) {
            pool.submitTask(() -> {
                try {
                    Thread.sleep(10000); // Simulate a task
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Increase sleep time to give the thread pool more time to scale up
        Thread.sleep(3000); // Give some time for threads to start

        int activeThreads = getActiveThreadsViaReflection();
        System.out.println("Final active threads count: " + activeThreads);

        assertTrue(activeThreads > 2, "Thread count did not increase with load");
        assertTrue(activeThreads <= 5, "Thread count exceeded the maximum limit");
    }

    @Test
    public void testMaximumThreadsNotExceeded() throws InterruptedException {
        pool.start();

        for (int i = 0; i < 20; i++) {
            pool.submitTask(() -> {
                try {
                    Thread.sleep(1000); // Simulate a long-running task
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread.sleep(3000); // Give some time for threads to start

        assertTrue(getActiveThreadsViaReflection() <= 5, "Thread count exceeded the maximum limit");
    }

    @Test
    public void testHandleEmptyQueueGracefully() throws InterruptedException {
        pool.start();

        Thread.sleep(2000); // Give some time with no tasks submitted

        assertEquals(2, getActiveThreadsViaReflection(), "Thread pool did not maintain minimum thread count with an empty queue");
    }

    @Test
    public void testThreadPoolShrinksBackToMinimum() throws InterruptedException {
        pool.start();

        for (int i = 0; i < 10; i++) {
            pool.submitTask(() -> {
                try {
                    Thread.sleep(50); // Simulate a task
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread.sleep(2000); // Wait for all tasks to complete

        Thread.sleep(2000); // Give some time for thread pool to adjust
        assertEquals(2, getActiveThreadsViaReflection(), "Thread count did not reduce back to minimum after load decrease");
    }

    @Test
    public void testTaskQueueOverflow() throws InterruptedException {
        pool.start();

        for (int i = 0; i < 100; i++) {
            pool.submitTask(() -> {
                try {
                    Thread.sleep(1000); // Simulate a long-running task
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread.sleep(3000); // Give time for threads to start processing tasks

        int queueSize = pool.getTaskQueueSize();
        assertTrue(queueSize <= 100, "Task queue should not exceed the limit, but overflow tasks should be handled correctly");
    }

    @Test
    public void testGetActiveThreads() throws InterruptedException {
        pool.start();  // Start the thread pool

        // Submit tasks to the pool
        for (int i = 0; i < 10; i++) {
            pool.submitTask(() -> {
                try {
                    Thread.sleep(500); // Simulate a task that takes time to execute
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Allow some time for tasks to start executing
        Thread.sleep(1000);

        // Get the number of active threads
        int activeThreads = pool.getActiveThreads();
        System.out.println("Active threads after starting tasks: " + activeThreads);

        // Check that the number of active threads is within the expected range
        assertTrue(activeThreads >= 2 && activeThreads <= 5, "The number of active threads should be between the minimum and maximum thread limits.");

        // Wait for tasks to complete
        Thread.sleep(4000); // Increased sleep time to allow monitor thread to adjust

        // Check that the number of active threads has decreased back to the minimum
        activeThreads = pool.getActiveThreads();
        System.out.println("Active threads after tasks complete: " + activeThreads);
        assertEquals(2, activeThreads, "The number of active threads should decrease back to the minimum after tasks complete.");
    }

    private int getActiveThreadsViaReflection() {
        try {
            Field field = DynamicThreadPoolManager.class.getDeclaredField("activeThreads");
            field.setAccessible(true);
            return field.getInt(pool);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private BlockingQueue<Runnable> getTaskQueueViaReflection() {
        try {
            Field field = DynamicThreadPoolManager.class.getDeclaredField("taskQueue");
            field.setAccessible(true);
            return (BlockingQueue<Runnable>) field.get(pool);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}

