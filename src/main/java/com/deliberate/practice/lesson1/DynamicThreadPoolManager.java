package com.deliberate.practice.lesson1;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Builder
public class DynamicThreadPoolManager {
    private final int minThreads;
    private final int maxThreads;

    @Builder.Default
    private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

    @Builder.Default
    private final List<WorkerThread> threads = new ArrayList<>();

    @Builder.Default
    private final AtomicInteger activeThreadCount = new AtomicInteger(0);

    @Builder.Default
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private volatile boolean isRunning;

    public synchronized void start() {
        // TODO: Implement the logic to start the thread pool and dynamically manage threads
    }

    public synchronized void stop() {
        // TODO: Implement the logic to stop the thread pool and wait for all threads to finish
    }

    public void submitTask(Runnable task) {
        // TODO: Implement task submission logic
    }

    public ThreadPoolStatus monitorStatus() {
        // TODO: Implement status monitoring logic
        return new ThreadPoolStatus(activeThreadCount.get(), taskQueue.size());
    }

    private synchronized void adjustThreadPoolSize() {
        // TODO: Implement logic to adjust the number of threads based on the task queue size
    }

    private void addThread() {
        // TODO: Implement logic to add a new worker thread
    }

    private void removeThread() {
        // TODO: Implement logic to remove a worker thread
    }

    @Getter
    public static class ThreadPoolStatus {
        private final int activeThreads;
        private final int taskQueueSize;

        public ThreadPoolStatus(int activeThreads, int taskQueueSize) {
            this.activeThreads = activeThreads;
            this.taskQueueSize = taskQueueSize;
        }
    }

    private static class WorkerThread extends Thread {
        private final BlockingQueue<Runnable> taskQueue;
        private final AtomicInteger activeThreadCount;
        private volatile boolean isRunning;

        public WorkerThread(BlockingQueue<Runnable> taskQueue, AtomicInteger activeThreadCount) {
            this.taskQueue = taskQueue;
            this.activeThreadCount = activeThreadCount;
            this.isRunning = true;
        }

        @Override
        public void run() {
            // TODO: Implement worker thread execution logic
        }

        public void shutdown() {
            // TODO: Implement worker thread shutdown logic
        }
    }
}
