package com.deliberate.practice.lesson4.task.credit_payment_calculator.dto;

public class CreditResponse {
    private double totalRepayment;

    // Constructor
    public CreditResponse(double totalRepayment) {
        this.totalRepayment = totalRepayment;
    }

    // Getter
    public double getTotalRepayment() {
        return totalRepayment;
    }
}
