package com.deliberate.practice.lesson3.task.designpatterns.command;

import com.deliberate.practice.lesson3.task.designpatterns.factory.CreditCardPayment;

public class CreditCardPaymentCommand implements PaymentCommand {
    private final CreditCardPayment payment;

    public CreditCardPaymentCommand(CreditCardPayment payment) {
        this.payment = payment;
    }

    @Override
    public void execute() {
        System.out.println("Executing credit card payment");
        payment.process();
    }

    @Override
    public void undo() {
        System.out.println("Undoing credit card payment");
    }
}
