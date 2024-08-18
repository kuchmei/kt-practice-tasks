package com.deliberate.practice.lesson1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DynamicThreadPoolManager {
    private final int minThreads;
    private final int maxThreads;
    private final BlockingQueue<Runnable> taskQueue;
    private final List<Thread> threads;
    private boolean isRunning;

    public DynamicThreadPoolManager(int minThreads, int maxThreads) {
        this.minThreads = minThreads;
        this.maxThreads = maxThreads;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.threads = new ArrayList<>();
        this.isRunning = true;
    }

    public void start() {
        // TODO: Implement the logic to start the thread pool and dynamically manage threads

    }

    public void stop() {
        // TODO: Implement the logic to stop the thread pool and wait for all threads to finish
    }

    public void submitTask(Runnable task) {
        // TODO: Implement task submission logic
    }

    public ThreadPoolStatus monitorStatus() {
        // TODO: Implement status monitoring logic
        return new ThreadPoolStatus(threads.size(), taskQueue.size());
    }

    public record ThreadPoolStatus(int activeThreads, int taskQueueSize) {
    }
}

