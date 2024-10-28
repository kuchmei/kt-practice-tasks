package com.deliberate.practice.lesson3.task.designpatterns.decorator;


import com.deliberate.practice.lesson3.task.designpatterns.factory.PaymentProcessor;

public class LoggingDecorator implements PaymentProcessor {
    private final PaymentProcessor decoratedProcessor;

    public LoggingDecorator(PaymentProcessor decoratedProcessor) {
        this.decoratedProcessor = decoratedProcessor;
    }

    @Override
    public void process() {
        System.out.println("Logging the payment");
        decoratedProcessor.process();
    }
}