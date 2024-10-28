package com.deliberate.practice.lesson3.task.designpatterns.chain;

public class PayPalValidation implements PaymentValidator {

    private PaymentValidator nextValidator;

    @Override
    public void validate(String paymentType) {
        if (paymentType.equals("PAYPAL")) {
            System.out.println("Validating PayPal payment");
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

