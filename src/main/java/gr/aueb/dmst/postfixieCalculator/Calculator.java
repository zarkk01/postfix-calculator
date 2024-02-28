package gr.aueb.dmst.postfixieCalculator;

import java.util.Stack;

public class Calculator {

    public static boolean isValidPostfix(Stack<Element> expressionToBeCalculated) {
        int counter = 0;
        for (Element element : expressionToBeCalculated) {
            if (!element.isNumber()) {
                counter -= 2;
                if (counter < 0) {
                    return false;
                }
            }
            counter++;
        }
        return counter == 1;
    }

    public static Double calculateExpression(Stack<Element> expressionToBeCalculated) {
        Stack<Double> results = new Stack<>();
        for (Element element : expressionToBeCalculated) {
            if (element.isNumber()) {
                results.push(element.getNumber().doubleValue());
            } else {
                double operand2 = results.pop();
                double operand1 = results.pop();
                switch (element.getOperator()) {
                    case '+':
                        results.push(operand1 + operand2);
                        break;
                    case '-':
                        results.push(operand1 - operand2);
                        break;
                    case '*':
                        results.push(operand1 * operand2);
                        break;
                    case '/':
                        if (operand2 == 0) {
                            throw new ArithmeticException("Cannot divide by zero");
                        }
                        results.push(operand1 / operand2);
                        break;
                }
            }
        }
        return results.pop();
    }
}
