package app.logic;

import app.model.Operator;
import app.model.Token;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CalculatorEngine {

    public static double evaluate(List<Token> tokens) {
        List<Token> postfix = infixToPostfix(tokens);
        return evaluatePostfix(postfix);
    }

    private static List<Token> infixToPostfix(List<Token> tokens) {
        List<Token> output = new ArrayList<>();
        Deque<Token> operatorStack = new ArrayDeque<>();

        for (Token token : tokens) {
            switch (token.getType()) {
                case NUMBER ->
                    output.add(token);

                case OPERATOR -> {
                    Operator currentOp = Operator.fromSymbol(token.getValue())
                            .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + token.getValue()));

                    while (!operatorStack.isEmpty()) {
                        Token top = operatorStack.peek();

                        if (top.getType() != Token.Type.OPERATOR) {
                            break;
                        }

                        Operator topOp = Operator.fromSymbol(top.getValue())
                                .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + top.getValue()));

                        if (topOp.getPrecedence() >= currentOp.getPrecedence()) {
                            output.add(operatorStack.pop()); // "Ctrl + x | Ctrl + v" from the queue into the output list
                        } else {
                            break;
                        }
                    }
                    operatorStack.push(token);
                }

                case PARENTHESIS -> {
                    if (token.getValue().equals("(")) {
                        operatorStack.push(token);
                    } else if (token.getValue().equals(")")) {
                        while (!operatorStack.isEmpty() && !operatorStack.peek().getValue().equals("(")) {
                            output.add(operatorStack.pop());
                        }
                        if (operatorStack.isEmpty() || !operatorStack.peek().getValue().equals("(")) {
                            throw new IllegalArgumentException("Mismatched parentheses.");
                        }
                        operatorStack.pop(); // discard '(' from the queue
                    }
                }

                default ->
                    throw new IllegalArgumentException("Invalid token type: " + token.getType());
            }
        }

        // Pop any remaining operators
        while (!operatorStack.isEmpty()) {
            Token top = operatorStack.pop();
            if (top.getValue().equals("(") || top.getValue().equals(")")) {
                throw new IllegalArgumentException("Mismatched parentheses.");
            }
            output.add(top);
        }

        return output;
    }

    private static double evaluatePostfix(List<Token> tokens) {
        Deque<Double> stack = new ArrayDeque<>();

        for (Token token : tokens) {
            switch (token.getType()) {
                case NUMBER -> {
                    try {
                        stack.push(Double.valueOf(token.getValue()));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid number: " + token.getValue());
                    }
                }

                case OPERATOR -> {
                    Operator op = Operator.fromSymbol(token.getValue())
                            .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + token.getValue()));

                    // TO DO: Special case for unary operators (√, ²)
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Not enough operands for operator: " + op.getSymbol());
                    }

                    // LIFO STACK!
                    // Keep this in this EXACT same order
                    double b = stack.pop(); // "Cut and Paste into"
                    double a = stack.pop(); // "Cut and Paste into"
                    // ------------------------------------ >>
                    switch (op.getInternalSymbol()) {
                        case "+" ->
                            stack.push(a + b);
                        case "-" ->
                            stack.push(a - b);
                        case "*" ->
                            stack.push(a * b);
                        case "/" ->
                            stack.push(a / b);
                        default ->
                            throw new UnsupportedOperationException("Unsupported operator: " + op.getSymbol());
                    }
                }

                default ->
                    throw new IllegalArgumentException("Unexpected token type: " + token.getType());
            }
        }

        if (stack.size() != 1) {
            throw new IllegalStateException("Malformed expression: stack has " + stack.size() + " items left.");
        }

        return stack.pop();
    }
}
