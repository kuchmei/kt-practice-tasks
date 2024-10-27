package com.deliberate.practice.lesson3.task.designpatterns.decorator;

/*
import com.deliberate.practice.lesson3.task.designpatterns.factory.CreditCardPayment;
import com.deliberate.practice.lesson3.task.designpatterns.factory.PayPalPayment;
import com.deliberate.practice.lesson3.task.designpatterns.factory.PaymentProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggingDecoratorTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restore() {
        System.setOut(originalOut);
    }

    @Test
    void testLoggingDecoratorWithCreditCard() {
        PaymentProcessor processor = new LoggingDecorator(new CreditCardPayment());
        processor.process();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Logging the payment"));
        assertTrue(output.contains("Processing payment with Credit Card"));
    }

    @Test
    void testLoggingDecoratorWithPayPal() {
        PaymentProcessor processor = new LoggingDecorator(new PayPalPayment());
        processor.process();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Logging the payment"));
        assertTrue(output.contains("Processing payment with PayPal"));
    }
}
*/
