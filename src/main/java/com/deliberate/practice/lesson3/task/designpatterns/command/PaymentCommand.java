package com.deliberate.practice.lesson3.task.designpatterns.command;

public interface PaymentCommand {
    void execute();
    void undo();
}