package app.model;

import java.util.Arrays;
import java.util.Optional;

public enum Parenthesis {
    LEFT("(", Type.LEFT),
    RIGHT(")", Type.RIGHT);

    public enum Type {
        LEFT, RIGHT
    }

    private final String symbol;
    private final Type type;

    Parenthesis(String symbol, Type type) {
        this.symbol = symbol;
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public Type getType() {
        return type;
    }

    public static Optional<Parenthesis> fromString(String symbol) {
        return Arrays.stream(values())
                .filter(p -> p.symbol.equals(symbol))
                .findFirst();
    }
}
