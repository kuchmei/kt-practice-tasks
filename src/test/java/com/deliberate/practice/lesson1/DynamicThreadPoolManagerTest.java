package com.deliberate.practice.lesson1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.deliberate.practice.lesson1.DynamicThreadPoolManager.*;
import static org.junit.jupiter.api.Assertions.*;

class DynamicThreadPoolManagerTest {

    private DynamicThreadPoolManager poolManager;
    private final int minThreads = 2;
    private final int maxThreads = 5;

    @BeforeEach
    void setUp() {
        // Using the builder to create an instance of DynamicThreadPoolManager
        poolManager = builder()
                .minThreads(minThreads)
                .maxThreads(maxThreads)
                .build();
    }

    @AfterEach
    void tearDown() {
        if (poolManager != null) {
            poolManager.stop();
        }
    }

    @Test
    void testInitialization() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            poolManager.monitorStatus();
        });
        assertEquals("ThreadPool is not running", exception.getMessage());
    }

    @Test
    void testTaskSubmission() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            Runnable dummyTask = () -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };
            poolManager.submitTask(dummyTask);
        });
        assertEquals("ThreadPool is not running", exception.getMessage());
    }

    @Test
    void testDynamicScaling() {
        poolManager.start();

        Runnable dummyTask = () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        for (int i = 0; i < 20; i++) {
            poolManager.submitTask(dummyTask);
        }

        try {
            Thread.sleep(2000);  // Give some time for threads to potentially scale
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ThreadPoolStatus status = poolManager.monitorStatus();
        assertTrue(status.getActiveThreads() >= minThreads);
        assertTrue(status.getActiveThreads() <= maxThreads);
    }

    @Test
    void testLoadBalancing() {
        poolManager.start();

        Runnable quickTask = () -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable slowTask = () -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        for (int i = 0; i < 5; i++) {
            poolManager.submitTask(quickTask);
        }

        for (int i = 0; i < 5; i++) {
            poolManager.submitTask(slowTask);
        }

        try {
            Thread.sleep(2000);  // Give some time for tasks to be processed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ThreadPoolStatus status = poolManager.monitorStatus();
        assertTrue(status.getActiveThreads() >= minThreads);
    }

    @Test
    void testPoolShutdown() {
        poolManager.start();

        Runnable dummyTask = () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        poolManager.submitTask(dummyTask);
        poolManager.stop();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            poolManager.monitorStatus();
        });
        assertEquals("ThreadPool is not running", exception.getMessage());
    }
}
