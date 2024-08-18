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
        // Validate input parameters
        if (minThreads < 0) {
            throw new IllegalArgumentException("Minimum threads cannot be negative");
        }
        if (maxThreads < 0) {
            throw new IllegalArgumentException("Maximum threads cannot be negative");
        }
        if (minThreads > maxThreads) {
            throw new IllegalArgumentException("Minimum threads cannot be greater than maximum threads");
        }

        this.minThreads = minThreads;
        this.maxThreads = maxThreads;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.running = new AtomicBoolean(false);
        this.activeThreads = 0;
    }

    synchronized void incrementActiveThreads() {
        activeThreads++;
        System.out.println("Incremented active threads: " + activeThreads);
    }

    synchronized void decrementActiveThreads() {
        if (activeThreads > minThreads) {
            activeThreads--;
            System.out.println("Decremented active threads: " + activeThreads);
        } else {
            System.out.println("Active threads count not decremented: " + activeThreads + " (minThreads: " + minThreads + ")");
        }
    }

    void createWorkerThread() {
        incrementActiveThreads();
        Thread worker = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started.");
            while (running.get() && !Thread.currentThread().isInterrupted()) {
                try {
                    Runnable task;
                    synchronized (this) {
                        task = taskQueue.poll(); // Use poll() to avoid blocking indefinitely
                        if (task == null) {
                            break; // Exit if no task is available
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " executing task.");
                    task.run();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    System.out.println(Thread.currentThread().getName() + " interrupted.");
                }
            }
            synchronized (this) {
                decrementActiveThreads();
                System.out.println(Thread.currentThread().getName() + " finished. Active threads: " + activeThreads);
                notifyAll();
            }
        }, "Worker-" + activeThreads);
        worker.start();
    }

    void adjustThreadCount() {
        int queueSize = taskQueue.size();
        System.out.println("Adjusting thread count. Current queue size: " + queueSize);

        if (queueSize > activeThreads && activeThreads < maxThreads) {
            System.out.println("Increasing thread count. Current active threads: " + activeThreads);
            createWorkerThread();
        } else if (queueSize == 0 && activeThreads > minThreads) {
            System.out.println("Decreasing thread count. Current active threads: " + activeThreads);
            decrementActiveThreads();
        }
    }

    void monitor() {
        while (running.get()) {
            synchronized (this) {
                adjustThreadCount();
            }
            try {
                Thread.sleep(1000);  // Monitor interval
            } catch (InterruptedException e) {
                // Log the interruption and break the loop to stop the monitor thread
                Thread.currentThread().interrupt();  // Re-interrupt the thread to maintain the interrupted status
                System.out.println("Monitor thread interrupted.");
                break;  // Exit the loop
            }
        }
    }

    public synchronized void start() {
        if (running.get()) {
            System.out.println("Thread pool is already running.");
            return;
        }

        running.set(true);
        System.out.println("Starting thread pool with minimum threads: " + minThreads);

        for (int i = 0; i < minThreads; i++) {
            createWorkerThread();
        }

        monitorThread = new Thread(this::monitor);
        monitorThread.start();
    }

    public synchronized void stop() {
        if (!running.get()) {
            System.out.println("Thread pool is not running.");
            return;
        }

        running.set(false);
        monitorThread.interrupt();
        System.out.println("Stopping thread pool and clearing task queue.");

        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getName().startsWith("Worker-")) {
                thread.interrupt();
                System.out.println("Interrupted " + thread.getName());
            }
        }
        taskQueue.clear();
        activeThreads = 0;
    }

    public synchronized void submitTask(Runnable task) {
        if (running.get()) {
            taskQueue.offer(task);
            System.out.println("Task submitted to the queue. Queue size: " + taskQueue.size());
            this.notify();
        } else {
            System.out.println("Cannot submit task. Thread pool is not running.");
        }
    }

    public synchronized int getActiveThreads() {
        System.out.println("Active threads: " + activeThreads);
        return activeThreads;
    }

    public synchronized int getTaskQueueSize() {
        System.out.println("Task queue size: " + taskQueue.size());
        return taskQueue.size();
    }

}
