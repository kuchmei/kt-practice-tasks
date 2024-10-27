package com.deliberate.practice.lesson3.task.designpatterns.chain;

public class ValidationService {
    public void validate(String paymentType) {
        if (paymentType.equals("CREDIT_CARD")) {
            System.out.println("Validating Credit Card payment");
        } else if (paymentType.equals("PAYPAL")) {
            System.out.println("Validating PayPal payment");
        } else {
            System.out.println("Unknown payment type, skipping validation");
        }
    }
}
