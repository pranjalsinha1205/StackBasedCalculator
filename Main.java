import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPlease use brackets '(' or ')' in case of BODMAS calculations.\nThe calculator might give wrong answers otherwise.\n\nEnter the expression: ");
        String expression = scanner.nextLine();

        Calculator calculator = new Calculator();

        try {
            int result = calculator.evaluateExpression(expression);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
