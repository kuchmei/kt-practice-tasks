package com.deliberate.practice.lesson1;

import com.deliberate.practice.exception.ExerciseNotCompletedException;

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
        // Example: if (minThreads < 0) { throw new IllegalArgumentException("Minimum threads cannot be negative"); }

        // Initialize fields
        this.minThreads = minThreads;
        this.maxThreads = maxThreads;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.running = new AtomicBoolean(false);
        this.activeThreads = 0;
    }

    synchronized void incrementActiveThreads() {
        // TODO: Increment the number of active threads.
        // - Increase activeThreads by 1.
        // - Print a message indicating the number of active threads.
    }

    synchronized void decrementActiveThreads() {
        // TODO: Decrement the number of active threads.
        // - Ensure activeThreads does not go below minThreads.
        // - If activeThreads is greater than minThreads, decrease it by 1.
        // - Print a message indicating the number of active threads.
    }

    void createWorkerThread() {
        // TODO: Increment the activeThreads count using incrementActiveThreads().
        // TODO: Create a new Thread that:
        // - Continuously checks for new tasks in the taskQueue.
        // - If a task is found, execute it.
        // - If interrupted or if no tasks are found, exit the loop.
        // TODO: Start the new worker thread.
    }

    void adjustThreadCount() {
        // TODO: Implement the logic to adjust the number of worker threads.
        // - If the taskQueue size > activeThreads and activeThreads < maxThreads, create new worker threads.
        // - If the taskQueue is empty and activeThreads > minThreads, reduce the number of active threads.
    }

    void monitor() {
        // TODO: Implement the logic for the monitor thread.
        // - This thread should continuously run while the pool is running.
        // - Periodically check the size of the taskQueue.
        // - Adjust the number of active worker threads by calling adjustThreadCount().
        // - If the monitor thread is interrupted, it should gracefully exit.
    }

    public synchronized void start() {
        // TODO: Implement the logic to start the thread pool.
        // - Check if the pool is already running using the running flag.
        // - If running, print a message and return.
        // - Set the running flag to true.
        // - Create initial worker threads using createWorkerThread().
        // - Start a monitor thread that will handle adjusting the thread count.
    }

    public synchronized void stop() {
        // TODO: Implement the logic to stop the thread pool.
        // - Check if the pool is running.
        // - If not running, print a message and return.
        // - Set the running flag to false.
        // - Interrupt the monitor thread.
        // - Interrupt all worker threads.
        // - Clear the task queue.
        // - Reset activeThreads to 0.
    }

    public synchronized void submitTask(Runnable task) {
        // TODO: Implement the logic to submit a task to the pool.
        // - Check if the pool is running.
        // - If running, add the task to the taskQueue.
        // - Notify waiting worker threads that there is a new task available.
        // - If not running, print a message and do not accept the task.
    }

    public synchronized int getActiveThreads() {
        // TODO: Return the number of active threads.
        throw new ExerciseNotCompletedException();
    }

    public synchronized int getTaskQueueSize() {
        // TODO: Return the size of the task queue.
        throw new ExerciseNotCompletedException();
    }
}