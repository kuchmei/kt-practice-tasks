package com.deliberate.practice.lesson4.task.credit_payment_calculator.decorator;

import com.deliberate.practice.lesson4.task.credit_payment_calculator.dto.CreditRequest;
import com.deliberate.practice.lesson4.task.credit_payment_calculator.service.CreditService;
import com.deliberate.practice.lesson4.task.credit_payment_calculator.service.CreditServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


@Component
@Primary
public class LoggingDecorator implements CreditService {
    private final CreditServiceImpl creditService;

    public LoggingDecorator(CreditServiceImpl creditService) {
        this.creditService = creditService;
    }

    @Override
    public double calculateTotalRepayment(CreditRequest request) {
        double totalRepayment = creditService.calculateTotalRepayment(request);
        System.out.println("Credit Request - Amount: $" + request.getAmount() + ", Term: " + request.getTerm() + " years, Total Repayment: $" + totalRepayment);
        return totalRepayment;
    }
}
