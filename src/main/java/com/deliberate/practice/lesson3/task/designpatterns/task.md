# Project: Design Patterns

## Overview
This project demonstrates the implementation of different design patterns in Java, including `Factory Method`, `Chain of Responsibility`, `Decorator`, and `Command`. The tasks are divided into different levels of difficulty to help developers improve their skills in writing flexible and maintainable code.

---

## Task 1: Implement `Factory Method`
1. **Goal**: Create a flexible mechanism to instantiate different payment processors.
2. **Steps**:
    - Create an interface `PaymentProcessor` with a `process()` method.
    - Implement classes `CreditCardPayment` and `PayPalPayment` which implement the `PaymentProcessor` interface.
    - Add a class `PaymentFactory` with a `createProcessor(String type)` method that returns the appropriate processor based on the type (`"CREDIT_CARD"`, `"PAYPAL"`).
    - **Update `PaymentService`** to use `PaymentFactory`.

3. **Verification**:
    - Ensure that `PaymentService` correctly instantiates and calls the `process()` method for `"CREDIT_CARD"` and `"PAYPAL"` types.
    - Handle unknown types by throwing an `IllegalArgumentException` and printing `"Unknown payment type"`.

## Task 2: Implement `Chain of Responsibility`
1. **Goal**: Create a mechanism to validate different types of payments sequentially.
2. **Steps**:
    - Create an interface `PaymentValidator` with methods `validate(String paymentType)` and `setNextValidator(PaymentValidator nextValidator)`.
    - Implement classes `CreditCardValidation` and `PayPalValidation` which implement `PaymentValidator`.
    - Create a `ValidationChain` class to handle the chain.
    - **Refactor `ValidationService`** to use `ValidationChain`.

3. **Verification**:
    - Ensure that validation checks are performed correctly for `"CREDIT_CARD"` and `"PAYPAL"` types.
    - Check behavior for unknown payment types.

### Testing for Factory Method and Chain of Responsibility:
- **`PaymentServiceTest.java`**: Tests if the correct payment processor is created and processes payments for `"CREDIT_CARD"` and `"PAYPAL"` types. It also checks for handling unknown payment types.
- **`ValidationServiceTest.java`**: Tests if the validation service correctly validates different payment types using the chain of responsibility.

---

## Task 3: Implement `Decorator`
1. **Goal**: Dynamically add logging and notification features to payment processing using decorators.
2. **Steps**:
    - Create a `LoggingDecorator` class that logs payment processing and wraps another `PaymentProcessor`.
3. **Verification**:
    - Ensure decorators can be used separately or together to extend existing processors.

## Task 4: Implement `Command`
1. **Goal**: Implement a mechanism to execute and undo payments using commands.
2. **Steps**:
    - Create a `PaymentCommand` interface with `execute()` and `undo()` methods.
    - Implement `CreditCardPaymentCommand` and `PayPalPaymentCommand` classes that implement `PaymentCommand`.
    - Implement logic in `execute()` to process payments and in `undo()` to cancel them.

3. **Verification**:
    - Ensure each command correctly executes and undoes payment processing.

### Testing for Decorator and Command:
- **`LoggingDecoratorTest.java`**: Tests if the logging decorator adds the correct logging messages to the payment processing.
- **`CommandPatternTest.java`**: Tests if commands execute and undo the correct processing actions for credit card and PayPal payments.

---
