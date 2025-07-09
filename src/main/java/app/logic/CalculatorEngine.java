package app.logic;

import app.model.Function;
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
        List<Token> output = new ArrayList<>(); // RPN List
        Deque<Token> operatorStack = new ArrayDeque<>(); // Operator LIFO Stack 

        for (Token token : tokens) { // Loop through the input
            switch (token.getType()) {
                case NUMBER -> // If it's a number, add to RPN List
                    output.add(token);

                case OPERATOR -> { // If it's an operator, check for precedence before pushing into the LIFO stack
                    Operator currentOp = Operator.fromSymbol(token.getValue())
                            .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + token.getValue()));

                    while (!operatorStack.isEmpty()) {
                        Token top = operatorStack.peek(); // Check the head token of the stack

                        if (top.getType() != Token.Type.OPERATOR) {
                            break;
                        }

                        Operator topOp = Operator.fromSymbol(top.getValue())
                                .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + top.getValue()));

                        if (topOp.getPrecedence() >= currentOp.getPrecedence()) { // If the head's precedence is more or equals to the current operator's ...
                            output.add(operatorStack.pop()); // Pop it from the stack into the list
                        } else { // Or else 
                            break; // break the loop ...
                        }
                    }
                    operatorStack.push(token); // ... and push the operator straight into the stack
                }

                case PARENTHESIS -> { // If it's a Parenthesis, check for it's type(left || right)
                    if (token.getValue().equals("(")) { // if it's left
                        operatorStack.push(token); // push it into stack
                    } else if (token.getValue().equals(")")) { // if it's right
                        while (!operatorStack.isEmpty() && !operatorStack.peek().getValue().equals("(")) { // while stack isn't empty and it's head isn't left parenthesis
                            output.add(operatorStack.pop()); // pop the stack's head into the list
                        }
                        if (operatorStack.isEmpty() || !operatorStack.peek().getValue().equals("(")) { // if stack's empty or it's head is left parenthesis
                            throw new IllegalArgumentException("Mismatched parentheses."); // throw exception
                        }
                        operatorStack.pop(); // default -> discard '(' from the stack
                    }
                }

                case FUNCTION -> {
                    operatorStack.add(token);
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
        Deque<Double> stack = new ArrayDeque<>(); // solution 

        for (Token token : tokens) { // for every token in the input queue
            switch (token.getType()) {
                case NUMBER -> { // if it's a number ...
                    try {
                        stack.push(Double.valueOf(token.getValue())); // ... push it into the stack
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid number: " + token.getValue());
                    }
                }

                case OPERATOR -> {
                    Operator op = Operator.fromSymbol(token.getValue())
                            .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + token.getValue()));

                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Not enough operands for operator: " + op.getSymbol());
                    }

                    double b = stack.pop(); // assign the stack's current head as the 2nd operand 
                    double a = stack.pop(); // assign the stack's current head as the 1st operand

                    switch (op.getInternalSymbol()) { // push result into solution stack
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

                case FUNCTION -> {
                    Function fun = Function.fromSymbol(token.getValue())
                            .orElseThrow(() -> new IllegalArgumentException("Unknown function: " + token.getValue()));

                    int arity = fun.getArity();
                    if (stack.size() < arity) {
                        throw new IllegalArgumentException("Not enough arguments for function: " + fun.getSymbol());
                    }

                    double[] args = new double[arity];
                    for (int i = arity - 1; i >= 0; i--) {
                        args[i] = stack.pop();
                    }

                    double result = fun.evaluate(args);
                    stack.push(result);
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
