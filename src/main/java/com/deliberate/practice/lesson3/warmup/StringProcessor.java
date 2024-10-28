package com.deliberate.practice.lesson3.warmup;

public class StringProcessor {

    /**
     * Reverses each word in the given input string.
     * Words are defined as sequences of non-space characters.
     *
     * @param input the input string
     * @return a new string with each word reversed
     */
    public static String reverseWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(new StringBuilder(word).reverse().toString()).append(" ");
            } else {
                result.append(" "); // Maintain spaces
            }
        }
        return result.toString().trim();
    }
}
