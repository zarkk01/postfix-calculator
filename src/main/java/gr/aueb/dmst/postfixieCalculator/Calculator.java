package gr.aueb.dmst.postfixieCalculator;

import java.util.Stack;


/**
 * The Calculator class provides methods for validating and calculating postfix expressions.
 */
public class Calculator {

    /**
     * Checks if the given postfix expression is valid.
     * A valid postfix expression should have exactly one more operand than operators.
     *
     * @param expressionToBeCalculated the postfix expression to be validated
     * @return true if the expression is valid, false otherwise
     */
    public static boolean isValidPostfix(Stack<Element> expressionToBeCalculated) {
        int counter = 0;
        // Iterate through the elements of the expression
        for (Element element : expressionToBeCalculated) {
            // If it is an operator, decrease the counter by 2
            if (!element.isNumber()) {
                counter -= 2;
                // If at any point the counter becomes negative, the expression is invalid
                if (counter < 0) {
                    return false;
                }
            }
            counter++;
        }
        // A valid postfix expression should have exactly one more operand than operators
        return counter == 1;
    }

    /**
     * Calculates the result of the given postfix expression using a stack.
     *
     * @param expressionToBeCalculated the postfix expression to be calculated
     * @return the result of the calculation
     * @throws ArithmeticException if a division by zero is attempted
     */
    public static Double calculateExpression(Stack<Element> expressionToBeCalculated) {
        Stack<Double> result = new Stack<>();
        for (Element element : expressionToBeCalculated) {
            if (element.isNumber()) {
                // If the element is a number, push it to the results stack
                result.push(element.getNumber().doubleValue());
            } else {
                // If the element is an operator, pop two numbers from the results stack and perform the operation
                double operand2 = result.pop();
                double operand1 = result.pop();
                switch (element.getOperator()) {
                    case '+':
                        result.push(operand1 + operand2);
                        break;
                    case '-':
                        result.push(operand1 - operand2);
                        break;
                    case '*':
                        result.push(operand1 * operand2);
                        break;
                    case '/':
                        // Check for division by zero and throw an exception if necessary
                        if (operand2 == 0) {
                            throw new ArithmeticException("Cannot divide by zero");
                        }
                        // If the division is valid, push the result to the results stack
                        result.push(operand1 / operand2);
                        break;
                }
            }
        }
        // The result of the calculation is the last number remaining in the results stack
        return result.pop();
    }
}