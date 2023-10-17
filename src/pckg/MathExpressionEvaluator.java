package pckg;

import java.util.Stack;

public class MathExpressionEvaluator {
    public static Double evaluateExpression(String expression) {
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder operand = new StringBuilder();
                operand.append(ch);

                // Read the complete operand
                while (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                    operand.append(expression.charAt(i + 1));
                    i++;
                }

                operandStack.push(Double.parseDouble(operand.toString()));
            } else if (ch == '(') {
                operatorStack.push(ch);
            } else if (ch == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    performOperation(operandStack, operatorStack);
                }
                operatorStack.pop(); // Discard the '('
            } else if (isOperator(ch)) {
                while (!operatorStack.isEmpty() && hasHigherPrecedence(ch, operatorStack.peek())) {
                    performOperation(operandStack, operatorStack);
                }
                operatorStack.push(ch);
            }
        }

        while (!operatorStack.isEmpty()) {
            performOperation(operandStack, operatorStack);
        }

        return operandStack.pop();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }

    private static boolean hasHigherPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        } else if ((op1 == '^') && (op2 != '^')) {
            return false;
        } else if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        } else {
            return true;
        }
    }

    private static void performOperation(Stack<Double> operandStack, Stack<Character> operatorStack) {
        char operator = operatorStack.pop();
        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();
        double result = 0.0;

        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                result = operand1 / operand2;
                break;
            case '^':
                result = Math.pow(operand1, operand2);
                break;
        }

        operandStack.push(result);
    }

}