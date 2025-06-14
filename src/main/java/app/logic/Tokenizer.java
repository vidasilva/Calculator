package app.logic;

import app.model.Operator;
import app.model.Token;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public static List<Token> tokenize(String input) throws IllegalArgumentException {
        List<Token> tokens = new ArrayList<>();
        StringBuilder numberBuffer = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                numberBuffer.append(c); // collect digits and dots
            } else {
                // Flush number if there is one
                if (numberBuffer.length() > 0) {
                    tokens.add(new Token(Token.Type.NUMBER, numberBuffer.toString()));
                    numberBuffer.setLength(0); // clear the buffer
                }

                if (c == '(' || c == ')') {
                    tokens.add(new Token(Token.Type.PARENTHESIS, String.valueOf(c)));
                } else {
                    String symbol = String.valueOf(c);
                    if (Operator.fromSymbol(symbol).isPresent()) {
                        tokens.add(new Token(Token.Type.OPERATOR, symbol));
                    } else if (Character.isWhitespace(c)) {
                        // ignore spaces
                    } else {
                        throw new IllegalArgumentException("Invalid character: " + c);
                    }
                }
            }
        }

        // Add any number left in the buffer
        if (numberBuffer.length() > 0) {
            tokens.add(new Token(Token.Type.NUMBER, numberBuffer.toString()));
        }

        return tokens;
    }
}
