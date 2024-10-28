package com.deliberate.practice.lesson3.task.designpatterns.chain;

public class ValidationChain {
    private final PaymentValidator firstValidator;

    public ValidationChain(PaymentValidator firstValidator) {
        this.firstValidator = firstValidator;
    }

    public void validate(String paymentType) {
        firstValidator.validate(paymentType);
    }
}
