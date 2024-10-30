package com.deliberate.practice.lesson4.task.credit_payment_calculator.service;

import com.deliberate.practice.lesson4.task.credit_payment_calculator.calculator.InterestCalculator;
import com.deliberate.practice.lesson4.task.credit_payment_calculator.dto.CreditRequest;
import com.deliberate.practice.lesson4.task.credit_payment_calculator.factory.InterestCalculatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final InterestCalculatorFactory factory = new InterestCalculatorFactory();
    private final CreditValidationService validationService;

    public double calculateTotalRepayment(CreditRequest request) {
        validationService.validate(request);
        InterestCalculator calculator = factory.getCalculator(request.getTerm());
        return calculator.calculateRepayment(request.getAmount());
    }
}