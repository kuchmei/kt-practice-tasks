package com.deliberate.practice.lesson4.task.credit_payment_calculator.validator;

import com.deliberate.practice.lesson4.task.credit_payment_calculator.dto.CreditRequest;

public abstract class BaseValidator implements RequestValidator {
    private RequestValidator nextValidator;

    @Override
    public void setNext(RequestValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

    protected boolean next(CreditRequest request) {
        if (nextValidator == null) {
            return true; // No more validators in the chain
        }
        return nextValidator.validate(request);
    }
}
