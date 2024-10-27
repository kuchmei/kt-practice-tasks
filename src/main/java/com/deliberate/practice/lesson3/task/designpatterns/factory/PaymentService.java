package com.deliberate.practice.lesson3.task.designpatterns.factory;

public class PaymentService {
    public void processPayment(String paymentType) {
        if (paymentType.equals("CREDIT_CARD")) {
            System.out.println("Processing payment with Credit Card");
        } else if (paymentType.equals("PAYPAL")) {
            System.out.println("Processing payment with PayPal");
        } else {
            System.out.println("Unknown payment type");
        }
    }
}
