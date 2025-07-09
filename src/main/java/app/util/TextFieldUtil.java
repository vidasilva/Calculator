package app.util;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class TextFieldUtil {

    public static void applySymbolSubstitution(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();

            switch (character) {
                case "*" -> {
                    event.consume();
                    insertAtCaret(textField, "×");
                }
                case "/" -> {
                    event.consume();
                    insertAtCaret(textField, "÷");
                }
                case "-" -> {
                    event.consume();
                    insertAtCaret(textField, "−");
                }
                case "**2" -> {
                    event.consume();
                    insertAtCaret(textField, "²");
                }
            }
        });
    }

    private static void insertAtCaret(TextField textField, String replacement) {
        int caretPos = textField.getCaretPosition();
        textField.insertText(caretPos, replacement);
        textField.positionCaret(caretPos + replacement.length());
    }
}
