import java.util.Stack;
import java.util.Scanner;

class Calculator {
    private Stack<Integer> numbers;
    private Stack<Character> operators;

    public Calculator() {
        numbers = new Stack<>();
        operators = new Stack<>();
    }

    public int evaluateExpression(String expression) {
        char[] tokens = expression.toCharArray();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ') {
                continue;
            }

            if (Character.isDigit(tokens[i])) {
                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && (Character.isDigit(tokens[i]) || tokens[i] == '.')) {
                    sb.append(tokens[i]);
                    i++;
                }
                i--;
                try {
                    numbers.push(Integer.parseInt(sb.toString()));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number format");
                }
            } else if (tokens[i] == '(') {
                operators.push(tokens[i]);
            } else if (tokens[i] == ')') {
                while (operators.peek() != '(') {
                    if (operators.isEmpty() || numbers.size() < 2) {
                        throw new IllegalArgumentException("Invalid expression");
                    }
                    evaluateTopOperator();
                }
                operators.pop(); // Pop the opening parenthesis
            } else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                while (!operators.isEmpty() && hasPrecedence(tokens[i], operators.peek())) {
                    if (numbers.size() < 2) {
                        throw new IllegalArgumentException("Invalid expression");
                    }
                    evaluateTopOperator();
                }
                operators.push(tokens[i]);
            } else {
                throw new IllegalArgumentException("Invalid character in the expression");
            }
        }

        while (!operators.isEmpty()) {
            if (operators.isEmpty() || numbers.size() < 2) {
                throw new IllegalArgumentException("Invalid expression");
            }
            evaluateTopOperator();
        }

        if (numbers.size() != 1 || !operators.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return numbers.pop();
    }

    private boolean hasPrecedence(char operator1, char operator2) {
        if (operator2 == '(' || operator2 == ')') {
            return false;
        }
        return (operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-');
    }

    private void evaluateTopOperator() {
        char operator = operators.pop();
        int operand2 = numbers.pop();
        int operand1 = numbers.pop();
        int result = 0;

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
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                result = operand1 / operand2;
                break;
        }

        numbers.push(result);
    }
}

