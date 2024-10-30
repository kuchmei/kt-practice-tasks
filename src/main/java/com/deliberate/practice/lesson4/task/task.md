# Credit Service Implementation

## Task Overview
This task involves creating a Credit Service that processes credit requests based on the input credit amount and term. The goal is to calculate the total amount to repay based on the duration of the credit using predefined interest rates.

### Key Objectives:
1. Implement the service logic to process credit requests and calculate the total amount to repay based on the term.
2. Use the Factory Method pattern to select the appropriate interest calculator.
3. Set up a REST endpoint to accept credit requests and return the calculated repayment amount.

## REST Endpoint
- **Endpoint**: `/api/credits/calculate`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
      "amount": 1000,
      "term": 2
    }
    ```
- **Response Body**:
    ```json
    {
      "totalRepayment": 1070.0
    }
    ```

## Logic Details
### Repayment Calculation Based on Term:
- **Up to 1 year**: Apply a 5% interest rate on the credit amount.
- **1-3 years**: Apply a 7% interest rate on the credit amount.
- **Above 3 years**: Apply a 10% interest rate on the credit amount.

### Example:
- If a user requests $1,000 for 1 year, the total repayment amount would be: $1,000 * 1.05 = $1,050.
- If a user requests $1,000 for 2 years, the total repayment amount would be: $1,000 * 1.07 = $1,070.
- If a user requests $1,000 for 5 years, the total repayment amount would be: $1,000 * 1.10 = $1,100.

## Steps to Implement

### 1. Define DTO Classes
- Create classes for the credit request and response. The request should include fields for the credit amount and term. The response should include the calculated total repayment.

### 2. Implement the REST Controller
- Create a controller (`CreditController`) with a POST endpoint to accept a credit request.
- Define methods in the controller that map to specific endpoints, e.g., `/api/credits/calculate`.
- Inject the necessary service(s) into the controller, delegating the logic to them.

### 3. Implement the `CreditService` Interface and Concrete Implementation
- Define an interface named `CreditService` that outlines a method for calculating the total repayment.
- Create a class implementing this interface (`CreditServiceImpl`) with the business logic for processing the credit requests.

### 4. Factory Method for Interest Calculation
- Create a factory class (`InterestCalculatorFactory`) that returns the appropriate interest calculator based on the term.

### 5. Define Interest Calculation Classes
- Implement separate classes for each interest calculation based on the term: short-term (up to 1 year), medium-term (1-3 years), and long-term (above 3 years).

## Optional Part (Challenging)

### 1. **Chain of Responsibility Pattern for Validation**
- **What to do**: Implement the Chain of Responsibility pattern to validate the credit request.
- **Purpose**: Use the pattern to validate the amount and term sequentially using separate validation classes.
- **Steps**:
    1. **Define a common interface** for validators with methods for validation and setting the next validator in the chain(`boolean validate(CreditRequest request)` and `void setNext(RequestValidator nextValidator)`).
    2. **Implement an abstract base class** to manage the chain logic, including passing the request to the next validator.
    3. **Create individual validation classes**:
        - **AmountValidator**: Validates that the credit amount is between 1 and 1,000,000. If the validation fails, throw an appropriate exception with an error message.
        - **TermValidator**: Validates that the term is between 1 and 30 years. If the validation fails, throw an appropriate exception with an error message.
    4. **Set up the chain** in a service class, linking the validators sequentially.
    5. **Integrate the validation service** into the main `CreditService` implementation to perform validation before processing.

### 2. **Logging with Decorator Pattern**
- **What to do**: Add a **LoggingDecorator** to the credit service and mark it with the `@Primary` annotation.
- **Purpose**: Log credit requests and their calculated responses to the console automatically.
- **Steps**:
    1. **Create a decorator class** that implements the `CreditService` interface and wraps an instance of the original service.
    2. **Mark the decorator class with `@Primary`** to prioritize it over other implementations.
    3. **Log the details** of the credit request (amount and term) and the calculated total repayment in the decorator.

**Example Log Message**: `Credit Request - Amount: $1000, Term: 2 years, Total Repayment: $1070.0`

## How to Test
1. **Run the Application**: Start the application and ensure the server is up.
2. **Send a POST Request**: Use Postman or a similar tool to send a POST request to `/api/credits/calculate` with the required JSON body.
3. **Check the Response**: Verify that the response contains the correctly calculated repayment amount based on the term.
4. **Test Logging**: Check the console for logging messages of incoming requests and calculated results.
5. **Test Validations**: Send invalid inputs and verify that appropriate error messages are returned, such as:
    - `"Invalid credit amount. Amount must be between 1 and 1,000,000."`
    - `"Invalid credit term. Term must be between 1 and 30 years."`
