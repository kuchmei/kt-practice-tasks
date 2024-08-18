package com.deliberate.practice.lesson1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class DynamicThreadPoolManager {

    private final int minThreads;
    private final int maxThreads;
    private final BlockingQueue<Runnable> taskQueue;
    private final AtomicBoolean running;
    private int activeThreads;
    private Thread monitorThread;

    public DynamicThreadPoolManager(int minThreads, int maxThreads) {
        // TODO: Implement parameter validation (check if minThreads, maxThreads are valid)

        this.minThreads = minThreads;
        this.maxThreads = maxThreads;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.running = new AtomicBoolean(false);
        this.activeThreads = 0;
    }

    public synchronized void start() {
        // TODO: Implement the logic to start the thread pool
        // Example: Create initial worker threads and start the monitor thread
    }

    public synchronized void stop() {
        // TODO: Implement the logic to stop the thread pool
        // Example: Stop the monitor thread, interrupt worker threads, clear the task queue
    }

    public synchronized void submitTask(Runnable task) {
        // TODO: Implement the logic to submit a task to the pool
        // Example: Offer the task to the task queue and notify threads
    }

    public synchronized int getActiveThreads() {
        // TODO: Implement the logic to return the number of active threads
        return 0; // Replace with actual implementation
    }

    public synchronized int getTaskQueueSize() {
        // TODO: Implement the logic to return the size of the task queue
        return 0; // Replace with actual implementation
    }
}
