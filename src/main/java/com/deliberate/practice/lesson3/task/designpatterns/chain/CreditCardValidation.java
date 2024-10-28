package com.deliberate.practice.lesson3.task.designpatterns.chain;

public class CreditCardValidation implements PaymentValidator {

    private PaymentValidator nextValidator;

    @Override
    public void validate(String paymentType) {
        if (paymentType.equals("CREDIT_CARD")) {
            System.out.println("Validating Credit Card payment");
        } else if (nextValidator != null) {
            nextValidator.validate(paymentType);
        } else {
            System.out.println("Unknown payment type, skipping validation");
        }
    }

    @Override
    public void setNextValidator(PaymentValidator paymentValidator) {
        this.nextValidator = paymentValidator;
    }
}
