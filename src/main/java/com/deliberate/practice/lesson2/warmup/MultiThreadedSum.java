package com.deliberate.practice.lesson2.warmup;

public class MultiThreadedSum {

    /**
     * Calculates the sum of an array of integers using two threads.
     *
     * @param array The array of integers to sum.
     * @return The total sum of the array.
     */
    public int calculateSum(int[] array) {
        int mid = array.length / 2;

        SumTask task1 = new SumTask(array, 0, mid);
        SumTask task2 = new SumTask(array, mid, array.length);

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return task1.getSum() + task2.getSum();
    }

    private static class SumTask implements Runnable {
        private final int[] array;
        private final int start;
        private final int end;
        private int sum;

        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
        }

        public int getSum() {
            return sum;
        }
    }
}

