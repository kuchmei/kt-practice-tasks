package com.deliberate.practice.lesson4.task.credit_payment_calculator.validator;

import com.deliberate.practice.lesson4.task.credit_payment_calculator.dto.CreditRequest;

public interface RequestValidator {
    boolean validate(CreditRequest request);

    void setNext(RequestValidator nextValidator);
}
