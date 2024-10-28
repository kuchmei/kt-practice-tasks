package com.deliberate.practice.lesson3.task.designpatterns.command;


import com.deliberate.practice.lesson3.task.designpatterns.factory.PayPalPayment;

public class PayPalPaymentCommand implements PaymentCommand {
    private final PayPalPayment payment;

    public PayPalPaymentCommand(PayPalPayment payment) {
        this.payment = payment;
    }

    @Override
    public void execute() {
        System.out.println("Executing PayPal payment");
        payment.process();
    }

    @Override
    public void undo() {
        System.out.println("Undoing PayPal payment");
    }
}
