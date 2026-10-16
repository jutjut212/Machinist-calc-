import java.util.Stack;

public class CalculatorUtils {

    public static String infixToPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        String[] tokens = infix.split(" ");
        for (String token : tokens) {
            if (isNumeric(token)) {
                output.append(token).append(' ');
            } else if (token.equals("(")) {
                stack.push('(');
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop()).append(' ');
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(token.charAt(0)) <= precedence(stack.peek())) {
                    output.append(stack.pop()).append(' ');
                }
                stack.push(token.charAt(0));
            }
        }
        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(' ');
        }
        return output.toString();
    }

    public static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split(" ");
        for (String token : tokens) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                if (token.equals("sin") || token.equals("cos") || token.equals("tan")) {
                    double a = stack.pop();
                    switch (token) {
                        case "sin":
                            stack.push(Math.sin(Math.toRadians(a)));
                            break;
                        case "cos":
                            stack.push(Math.cos(Math.toRadians(a)));
                            break;
                        case "tan":
                            stack.push(Math.tan(Math.toRadians(a)));
                            break;
                    }
                } else {
                    double b = stack.pop();
                    double a = stack.pop();
                    switch (token.charAt(0)) {
                        case '+':
                            stack.push(a + b);
                            break;
                        case '-':
                            stack.push(a - b);
                            break;
                        case '*':
                            stack.push(a * b);
                            break;
                        case '/':
                            stack.push(a / b);
                            break;
                    }
                }
            }
        }
        return stack.pop();
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }
}