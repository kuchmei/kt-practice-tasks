package com.deliberate.practice.lesson3.task.designpatterns.factory;

public class PaymentService {
    public void processPayment(String paymentType) {
        try {
            PaymentProcessor processor = PaymentFactory.createProcessor(paymentType);
            processor.process();
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown payment type");
        }
    }
}