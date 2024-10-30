package com.deliberate.practice.lesson4.task.credit_payment_calculator.dto;

public class CreditRequest {
    private double amount;
    private int term;

    // Getters and setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }
}
