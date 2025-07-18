package app.model;

import java.util.Arrays;
import java.util.Optional;

public enum Operator {
    ADD("+", "+", 2, 2),
    SUBTRACT("−", "-", 2, 2),
    MULTIPLY("×", "*", 3, 2),
    DIVIDE("÷", "/", 3, 2),
    POWER("^", "pow", 4, 2);

    private final String symbol;
    private final String internalSymbol;
    private final int precedence;
    private final int arity;

    Operator(String symbol, String internalSymbol, int precedence, int arity) {
        this.symbol = symbol;
        this.internalSymbol = internalSymbol;
        this.precedence = precedence;
        this.arity = arity;
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

    public int getArity() {
        return arity;
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
