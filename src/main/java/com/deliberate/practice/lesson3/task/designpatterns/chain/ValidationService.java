package com.deliberate.practice.lesson3.task.designpatterns.chain;

public class ValidationService {
    private final ValidationChain validationChain;

    public ValidationService() {
        PaymentValidator creditCardValidator = new CreditCardValidation();
        PaymentValidator payPalValidator = new PayPalValidation();

        creditCardValidator.setNextValidator(payPalValidator);

        this.validationChain = new ValidationChain(creditCardValidator);
    }

    public void validate(String paymentType) {
        validationChain.validate(paymentType);
    }
}