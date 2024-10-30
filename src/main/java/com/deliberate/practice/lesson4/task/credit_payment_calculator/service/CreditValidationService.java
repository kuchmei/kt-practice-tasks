package com.deliberate.practice.lesson4.task.credit_payment_calculator.service;

import com.deliberate.practice.lesson4.task.credit_payment_calculator.dto.CreditRequest;
import com.deliberate.practice.lesson4.task.credit_payment_calculator.validator.AmountValidator;
import com.deliberate.practice.lesson4.task.credit_payment_calculator.validator.RequestValidator;
import com.deliberate.practice.lesson4.task.credit_payment_calculator.validator.TermValidator;
import org.springframework.stereotype.Service;

@Service
public class CreditValidationService {
    private final RequestValidator validatorChain;

    public CreditValidationService() {
        RequestValidator amountValidator = new AmountValidator();
        RequestValidator termValidator = new TermValidator();

        amountValidator.setNext(termValidator);

        this.validatorChain = amountValidator;
    }

    public void validate(CreditRequest request) {
        validatorChain.validate(request);
    }
}
