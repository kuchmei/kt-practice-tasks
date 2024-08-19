package com.deliberate.practice.exception;

public class ExerciseNotCompletedException extends RuntimeException {

    public ExerciseNotCompletedException() {
        super("Exercise not completed. Please provide implementation.");
    }

}
