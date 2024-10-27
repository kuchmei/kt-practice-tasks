package com.deliberate.practice.lesson3.warmup;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringProcessorTest {

    @Test
    public void testReverseWords() {
        // Normal cases
        assertEquals("avaJ si emosewa", StringProcessor.reverseWords("Java is awesome"));
        assertEquals("gnimmargorP avaJ", StringProcessor.reverseWords("Programming Java"));

        // Edge cases
        assertEquals("", StringProcessor.reverseWords("")); // Empty string
        assertEquals("drow", StringProcessor.reverseWords("word")); // Single word
        assertEquals("", StringProcessor.reverseWords("  ")); // Only spaces

        // Special characters
        assertEquals("!avaJ", StringProcessor.reverseWords("Java!")); // Word with special character at the end
        assertEquals("321 654", StringProcessor.reverseWords("123 456")); // Numbers as words
    }

}