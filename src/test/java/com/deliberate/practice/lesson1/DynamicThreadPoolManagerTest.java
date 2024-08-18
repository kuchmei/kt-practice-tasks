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
        poolManager = new DynamicThreadPoolManager(minThreads, maxThreads);
    }

    @AfterEach
    void tearDown() {
        if (poolManager != null) {
            poolManager.stop();
        }
    }

    @Test
    void testInitialization() {
        assertThrows(IllegalStateException.class, () -> {
            poolManager.monitorStatus();
        });
    }

    @Test
    void testTaskSubmission() {
        assertThrows(IllegalStateException.class, () -> {
            Runnable dummyTask = () -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };
            poolManager.submitTask(dummyTask);
        });
    }

    @Test
    void testDynamicScaling() {
        assertThrows(IllegalStateException.class, () -> {
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

            Thread.sleep(1000);  // Give some time for threads to potentially scale

            ThreadPoolStatus status = poolManager.monitorStatus();
            assertTrue(status.activeThreads() >= minThreads);
            assertTrue(status.activeThreads() <= maxThreads);
        });
    }

    @Test
    void testLoadBalancing() {
        assertThrows(IllegalStateException.class, () -> {
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

            Thread.sleep(1000);  // Give some time for tasks to be processed

            ThreadPoolStatus status = poolManager.monitorStatus();
            assertTrue(status.activeThreads() >= minThreads);
        });
    }

    @Test
    void testPoolShutdown() {
        assertThrows(IllegalStateException.class, () -> {
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

            ThreadPoolStatus status = poolManager.monitorStatus();
            assertEquals(0, status.activeThreads());
        });
    }
}
