package com.deliberate.practice.lesson4.task.credit_payment_calculator.validator;

import com.deliberate.practice.lesson4.task.credit_payment_calculator.dto.CreditRequest;

public class TermValidator extends BaseValidator {
    @Override
    public boolean validate(CreditRequest request) {
        if (request.getTerm() < 1 || request.getTerm() > 30) {
            throw new IllegalArgumentException("Invalid credit term. Term must be between 1 and 30 years.");
        }
        return next(request);
    }
}
