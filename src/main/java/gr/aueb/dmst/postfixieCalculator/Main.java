package gr.aueb.dmst.postfixieCalculator;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<Element> stack = new Stack<>();
        boolean exit = false;
        do {
            System.out.println("You want to give a number or an operator (n/o): ");
            String input = scanner.nextLine();
            switch (input) {
                case "n":
                    System.out.println("Give a number: ");
                    int number = scanner.nextInt();
                    scanner.nextLine();
                    stack.push(new Element(number));
                    break;
                case "o":
                    System.out.println("Give an operator: ");
                    char operator = scanner.nextLine().charAt(0);
                    stack.push(new Element(operator));
                    break;
                case "exit":
                    exit = true;
            }
        } while (!exit);

        for (Element element : stack) {
            if (element.isNumber()) {
                System.out.println(element.getNumber());
            } else {
                System.out.println(element.getOperator());
            }
        }

        if (Calculator.isValidPostfix(stack)) {
            System.out.println("The expression is valid");
            System.out.println("The result is: " + Calculator.calculateExpression(stack));
        } else {
            System.out.println("The expression is not valid");
        }
    }
}
