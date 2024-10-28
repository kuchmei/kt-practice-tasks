package com.deliberate.practice.lesson3.task.designpatterns.factory;

public class PayPalPayment implements PaymentProcessor {
    @Override
    public void process() {
        System.out.println("Processing payment with PayPal");
    }
}
