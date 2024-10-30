package com.deliberate.practice.lesson4.task.credit_payment_calculator.calculator;

public class MediumTermCalculator implements InterestCalculator {
    @Override
    public double calculateRepayment(double amount) {
        return amount * 1.07;
    }
}
