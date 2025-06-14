package app.model;

import java.util.Arrays;
import java.util.Optional;

public enum Number {
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9");

    private final String number;

    Number(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public static Optional<Number> fromString(String s) {
        return Arrays.stream(values())
                .filter(n -> n.getNumber().equals(s))
                .findFirst();
    }
}
