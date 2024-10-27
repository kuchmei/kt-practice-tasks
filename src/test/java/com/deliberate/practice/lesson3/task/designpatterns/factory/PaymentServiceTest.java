package com.deliberate.practice.lesson3.task.designpatterns.factory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentServiceTest {
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
    public void testCreditCardPayment() {
        PaymentService service = new PaymentService();
        service.processPayment("CREDIT_CARD");
        String output = outputStream.toString().trim();
        assertEquals("Processing payment with Credit Card", output);
    }

    @Test
    public void testPayPalPayment() {
        PaymentService service = new PaymentService();
        service.processPayment("PAYPAL");
        String output = outputStream.toString().trim();
        assertEquals("Processing payment with PayPal", output);
    }

    @Test
    public void testUnknownPayment() {
        PaymentService service = new PaymentService();
        service.processPayment("BITCOIN");
        String output = outputStream.toString().trim();
        assertEquals("Unknown payment type", output);
    }
}
