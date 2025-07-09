 package app.model;

import java.util.Arrays;
import java.util.Optional;

public enum Function {
    SQUARE("²", "**2", 4, 1),
    ROOT("√", "sqrt", 4, 1);

    private final String symbol;
    private final String internalSymbol;
    private final int precedence;
    private final int arity;

    Function(String symbol, String internalSymbol, int precedence, int arity) {
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

    public static Optional<Function> fromSymbol(String symbol) {
        return Arrays.stream(values())
                .filter(op -> op.symbol.equals(symbol))
                .findFirst();
    }

    public static Optional<Function> fromInternal(String internal) {
        return Arrays.stream(values())
                .filter(op -> op.internalSymbol.equals(internal))
                .findFirst();
    }

    public double evaluate(double... args) {
        return switch (this) {
            case SQUARE -> {
                if (args.length != 1) {
                    throw new IllegalArgumentException("SQUARE requires 1 argument.");
                }
                yield args[0] * args[0];
            }
            case ROOT -> {
                if (args.length != 1) {
                    throw new IllegalArgumentException("ROOT requires 1 argument.");
                }
                yield Math.sqrt(args[0]);
            }
        };
    }
}
