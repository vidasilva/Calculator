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

    private final List<Token> currentTokens = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextFieldUtil.applySymbolSubstitution(inputTextField);
        clearButton.setOnMouseClicked(eh -> inputTextField.clear());
        equalsButton.setOnMouseClicked(eh -> evaluateExpression());
    }

    @FXML
    private void handleButtonClick(MouseEvent event) {
        FlowPane source = (FlowPane) event.getSource();
        String value = (String) source.getUserData();
        appendToInput(value);
    }

    private void appendToInput(String value) {
        Token token = determineToken(value);
        if (token != null) {
            currentTokens.add(token);
        } else {
            System.err.println("Unknown input: " + value);
        }

        insertTextIntoInput(value);
    }

    private Token determineToken(String value) {
        if (Number.fromString(value).isPresent() || value.equals(".")) {
            return new Token(Token.Type.NUMBER, value);
        }
        if (Operator.fromSymbol(value).isPresent()) {
            return new Token(Token.Type.OPERATOR, value);
        }
        if (value.equals("(") || value.equals(")")) {
            return new Token(Token.Type.PARENTHESIS, value);
        }
        if (value.equals("√") || value.equals("²") || value.equals("^")) {
            return new Token(Token.Type.FUNCTION, value);
        }
        return null;
    }

    private void insertTextIntoInput(String value) {
        int caretPosition = inputTextField.getCaretPosition();
        inputTextField.insertText(caretPosition, value);
        inputTextField.positionCaret(caretPosition + value.length());
    }

    private void evaluateExpression() {
        try {
            String input = inputTextField.getText();
            List<Token> tokens = Tokenizer.tokenize(input);
            double result = CalculatorEngine.evaluate(tokens);

            outputTextField.setText(input.trim());
            inputTextField.clear();
            insertTextIntoInput(String.valueOf(result));
        } catch (IllegalArgumentException ex) {
            outputTextField.setText("Error");
            System.err.println("Evaluation error: " + ex.getMessage());
        }
    }
}
