package com.deliberate.practice.lesson2.warmup;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiThreadedSumTest {

    @Test
    public void testCalculateSum() {
        MultiThreadedSum sumCalculator = new MultiThreadedSum();
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int result = sumCalculator.calculateSum(array);
        assertEquals(55, result, "The sum should be 55.");
    }
}
