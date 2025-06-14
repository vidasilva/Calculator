package app.controller;

import app.logic.CalculatorEngine;
import app.logic.Tokenizer;
import app.model.Number;
import app.model.Operator;
import app.model.Token;
import app.util.TextFieldUtil;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

public class PrimaryController implements Initializable {

    @FXML
    private TextField inputTextField, outputTextField;
    @FXML
    private FlowPane clearButton, equalsButton;

    @FXML // function that calls appendToInput function
    private void handleButtonClick(MouseEvent event) {
        FlowPane source = (FlowPane) event.getSource();
        String value = (String) source.getUserData();
        appendToInput(value);
    }

    private final List<Token> currentTokens = new ArrayList<>(); // Working with tokens makes it easier to evaluate later

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TextFieldUtil.applySymbolSubstitution(inputTextField); // This 'sanitizes' the String's mathy symbols
        clearButton.setOnMouseClicked(eh -> inputTextField.clear()); // This (obviously) cleans the text-field
        equalsButton.setOnMouseClicked(eh -> evaluateExpression()); // This calls the evaluation function - or method e.e - below
    }

    // TO DO: tweak this later
    private void appendToInput(String value) {
        Optional<Number> maybeNum = Number.fromString(value);
        if (maybeNum.isPresent() || value.equals(".")) { // Try Number
            currentTokens.add(new Token(Token.Type.NUMBER, value)); // Handle Number
        } else {
            Optional<Operator> maybeOp = Operator.fromSymbol(value);
            if (maybeOp.isPresent()) { // Try Operator
                currentTokens.add(new Token(Token.Type.OPERATOR, value)); // Handle Operator
            } else if (value.equals("(") || value.equals(")")) { // Try Parenthesis
                Token token = new Token(Token.Type.PARENTHESIS, value); // Handle Parentheses
            } else {
                System.err.println("Unknown input: " + value);
            }
        }
        // OCD / UX friendly
        int caretPosition = inputTextField.getCaretPosition();
        inputTextField.insertText(caretPosition, value);
        inputTextField.positionCaret(caretPosition + value.length());

    }

    private void evaluateExpression() {
        try {
            String input = inputTextField.getText(); // Full expression as typed
            List<Token> tokens = Tokenizer.tokenize(input); // 'Sanitization' before evaluation of the expression
            double result = CalculatorEngine.evaluate(tokens); // Evaluation

            // UX Stuff
            outputTextField.setText(inputTextField.getText().trim());
            inputTextField.clear();
            int caretPosition = inputTextField.getCaretPosition();
            inputTextField.insertText(caretPosition, String.valueOf(result));
            inputTextField.positionCaret(caretPosition + String.valueOf(result).length());

        } catch (IllegalArgumentException ex) {
            outputTextField.setText("Error");
            System.err.println("Evaluation error: " + ex.getMessage());
        }
    }

}
