package com.deliberate.practice.lesson4.warmup;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayUtilsTest {

    @Test
    public void testFindMissingNumberInSequence() {
        // Test cases with different missing numbers
        assertEquals(3, ArrayUtils.findMissingNumberInSequence(new int[]{1, 2, 4, 5}, 5));
        assertEquals(1, ArrayUtils.findMissingNumberInSequence(new int[]{2, 3, 4, 5}, 5));
        assertEquals(5, ArrayUtils.findMissingNumberInSequence(new int[]{1, 2, 3, 4}, 5));
        assertEquals(2, ArrayUtils.findMissingNumberInSequence(new int[]{1, 3, 4, 5}, 5));
    }

    @Test
    public void testFindMissingNumberInSequenceEdgeCases() {
        // Test when the array is empty and totalCount is 1
        assertEquals(1, ArrayUtils.findMissingNumberInSequence(new int[]{}, 1));

        // Test with a large number range
        assertEquals(100, ArrayUtils.findMissingNumberInSequence(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
                51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
                71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
                91, 92, 93, 94, 95, 96, 97, 98, 99}, 100));
    }

    @Test
    public void testFindMissingNumberInNegativeSequence() {
        // Test cases with negative numbers
        assertEquals(-1, ArrayUtils.findMissingNumberInNegativeSequence(new int[]{-3, -2, 0, 1}));
        assertEquals(-2, ArrayUtils.findMissingNumberInNegativeSequence(new int[]{-3, -1, 0, 1}));
        assertEquals(-5, ArrayUtils.findMissingNumberInNegativeSequence(new int[]{-6, -4, -3, -2, -1, 0, 1}));
    }
}
