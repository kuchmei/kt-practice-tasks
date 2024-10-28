package com.deliberate.practice.lesson3.task.designpatterns.factory;

public class PaymentFactory {

    public static PaymentProcessor createProcessor(String type) {
        switch (type) {
            case "CREDIT_CARD":
                return new CreditCardPayment();
            case "PAYPAL":
                return new PayPalPayment();
            default:
                throw new IllegalArgumentException("Unknown payment type");
        }
    }
}
