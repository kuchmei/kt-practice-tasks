package com.deliberate.practice.lesson3.task.designpatterns.command;

import com.deliberate.practice.lesson3.task.designpatterns.factory.CreditCardPayment;
import com.deliberate.practice.lesson3.task.designpatterns.factory.PayPalPayment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandPatternTest {
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
    void testExecuteCreditCardCommand() {
        PaymentCommand command = new CreditCardPaymentCommand(new CreditCardPayment());
        command.execute();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Executing credit card payment"));
        assertTrue(output.contains("Processing payment with Credit Card"));
    }

    @Test
    void testUndoCreditCardCommand() {
        PaymentCommand command = new CreditCardPaymentCommand(new CreditCardPayment());
        command.undo();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Undoing credit card payment"));
    }

    @Test
    void testExecutePayPalCommand() {
        PaymentCommand command = new PayPalPaymentCommand(new PayPalPayment());
        command.execute();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Executing PayPal payment"));
        assertTrue(output.contains("Processing payment with PayPal"));
    }

    @Test
    void testUndoPayPalCommand() {
        PaymentCommand command = new PayPalPaymentCommand(new PayPalPayment());
        command.undo();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Undoing PayPal payment"));
    }
}
