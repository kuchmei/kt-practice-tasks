package com.deliberate.practice.lesson4.warmup;

import com.deliberate.practice.exception.ExerciseNotCompletedException;

import java.util.Arrays;

public class ArrayUtils {

    // Method to find the missing number in a sequence from 1 to totalCount
    // Method to find the missing number in a sequence from 1 to totalCount
    public static int findMissingNumberInSequence(int[] numbers, int totalCount) {
        if (numbers == null || numbers.length == 0) {
            return 1;
        }

        int expectedSum = totalCount * (totalCount + 1) / 2;
        int actualSum = Arrays.stream(numbers).sum();
        return expectedSum - actualSum;
    }

    // Method to find the missing number in a sequence that starts from a negative number
    public static int findMissingNumberInNegativeSequence(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }

        int min = Arrays.stream(numbers).min().orElse(0);
        int max = Arrays.stream(numbers).max().orElse(0);
        int expectedSum = 0;
        for (int i = min; i <= max; i++) {
            expectedSum += i;
        }
        int actualSum = Arrays.stream(numbers).sum();
        return expectedSum - actualSum;
    }
}
