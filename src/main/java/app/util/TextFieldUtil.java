package app.util;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class TextFieldUtil {

    public static void applySymbolSubstitution(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();

            switch (character) {
                case "*":
                    event.consume();
                    insertAtCaret(textField, "×");
                    break;
                case "/":
                    event.consume();
                    insertAtCaret(textField, "÷");
                    break;
                case "-":
                    event.consume();
                    insertAtCaret(textField, "−");
                    break;
            }
        });
    }

    private static void insertAtCaret(TextField textField, String replacement) {
        int caretPos = textField.getCaretPosition();
        textField.insertText(caretPos, replacement);
        textField.positionCaret(caretPos + replacement.length());
    }
}
