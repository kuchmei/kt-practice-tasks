package com.deliberate.practice.lesson4.task.credit_payment_calculator.validator;

import com.deliberate.practice.lesson4.task.credit_payment_calculator.dto.CreditRequest;

public class AmountValidator extends BaseValidator {
    @Override
    public boolean validate(CreditRequest request) {
        if (request.getAmount() < 1 || request.getAmount() > 1_000_000) {
            throw new IllegalArgumentException("Invalid credit amount. Amount must be between 1 and 1,000,000.");
        }
        return next(request);
    }
}
