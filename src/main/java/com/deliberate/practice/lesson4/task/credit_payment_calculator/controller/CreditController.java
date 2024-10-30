package com.deliberate.practice.lesson4.task.credit_payment_calculator.controller;

import com.deliberate.practice.lesson4.task.credit_payment_calculator.dto.CreditRequest;
import com.deliberate.practice.lesson4.task.credit_payment_calculator.dto.CreditResponse;
import com.deliberate.practice.lesson4.task.credit_payment_calculator.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/credits")

public class CreditController {

    private final CreditService creditService;

    @PostMapping("/calculate")
    public ResponseEntity<CreditResponse> calculateRepayment(@RequestBody CreditRequest request) {
        double totalRepayment = creditService.calculateTotalRepayment(request);
        return ResponseEntity.ok(new CreditResponse(totalRepayment));
    }
}
