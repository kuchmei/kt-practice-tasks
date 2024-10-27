package com.deliberate.practice.lesson3.task.designpatterns.chain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationServiceTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
    }

    @Test
    public void testCreditCardValidation() {
        ValidationService service = new ValidationService();
        service.validate("CREDIT_CARD");
        String output = outputStream.toString().trim();
        assertEquals("Validating Credit Card payment", output);
    }

    @Test
    public void testPayPalValidation() {
        ValidationService service = new ValidationService();
        service.validate("PAYPAL");
        String output = outputStream.toString().trim();
        assertEquals("Validating PayPal payment", output);
    }

    @Test
    public void testUnknownValidation() {
        ValidationService service = new ValidationService();
        service.validate("BITCOIN");
        String output = outputStream.toString().trim();
        assertEquals("Unknown payment type, skipping validation", output);
    }
}
