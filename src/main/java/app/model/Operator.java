package app.model;

import java.util.Arrays;
import java.util.Optional;

public enum Operator {
    ADD("+", "+", 1),
    SUBTRACT("−", "-", 1),
    MULTIPLY("×", "*", 2),
    DIVIDE("÷", "/", 2),
    PERCENT("%", "%", 2),
    SQUARE("²", "**2", 3),
    POWER("^", "^", 3),
    ROOT("√", "sqrt", 3);

    private final String symbol;
    private final String internalSymbol;
    private final int precedence;

    Operator(String symbol, String internalSymbol, int precedence) {
        this.symbol = symbol;
        this.internalSymbol = internalSymbol;
        this.precedence = precedence;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getInternalSymbol() {
        return internalSymbol;
    }

    public int getPrecedence() {
        return precedence;
    }

    public static Optional<Operator> fromSymbol(String symbol) {
        return Arrays.stream(values())
                .filter(op -> op.symbol.equals(symbol))
                .findFirst();
    }

    public static Optional<Operator> fromInternal(String internal) {
        return Arrays.stream(values())
                .filter(op -> op.internalSymbol.equals(internal))
                .findFirst();
    }
}
