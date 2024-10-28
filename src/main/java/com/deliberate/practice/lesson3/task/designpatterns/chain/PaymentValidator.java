package com.deliberate.practice.lesson3.task.designpatterns.chain;

public interface PaymentValidator {

    void validate(String paymentType);

    void setNextValidator(PaymentValidator paymentValidator);
}
