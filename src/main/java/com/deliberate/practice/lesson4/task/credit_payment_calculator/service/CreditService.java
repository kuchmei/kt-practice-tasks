package com.deliberate.practice.lesson4.task.credit_payment_calculator.service;

import com.deliberate.practice.lesson4.task.credit_payment_calculator.dto.CreditRequest;

public interface CreditService {

    double calculateTotalRepayment(CreditRequest request);
}
