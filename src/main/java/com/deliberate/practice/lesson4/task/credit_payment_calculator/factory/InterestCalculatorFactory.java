package com.deliberate.practice.lesson4.task.credit_payment_calculator.factory;

import com.deliberate.practice.lesson4.task.credit_payment_calculator.calculator.InterestCalculator;
import com.deliberate.practice.lesson4.task.credit_payment_calculator.calculator.LongTermCalculator;
import com.deliberate.practice.lesson4.task.credit_payment_calculator.calculator.MediumTermCalculator;
import com.deliberate.practice.lesson4.task.credit_payment_calculator.calculator.ShortTermCalculator;

public class InterestCalculatorFactory {
    public InterestCalculator getCalculator(int termInYears) {
        if (termInYears <= 1) {
            return new ShortTermCalculator();
        } else if (termInYears <= 3) {
            return new MediumTermCalculator();
        } else {
            return new LongTermCalculator();
        }
    }
}
