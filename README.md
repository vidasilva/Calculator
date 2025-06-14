# JavaFX Calculator Engine

This is a simple yet structured calculator application built using **JavaFX**. It serves as both a UI demonstration and a deeper dive into parsing and evaluating mathematical expressions using core Java. The logic follows classical compiler design concepts like tokenization, infix-to-postfix conversion (Shunting Yard), and stack-based evaluation.

## ✨ Features

- ✅ **JavaFX UI** with clickable buttons
- ✅ Tokenizer: parses user input into a structured list of tokens
- ✅ Shunting Yard algorithm to handle operator precedence and parentheses
- ✅ Postfix expression evaluation using a stack
- ✅ Support for:
  - Addition `+`
  - Subtraction `-`
  - Multiplication `×`
  - Division `÷`
  - Parentheses `( )`
  - Decimal numbers (`.`)
- ✅ UI auto-converts `*` to `×` and `/` to `÷` for consistent display

## ⚙️ Technologies

- Java 17+
- JavaFX
- Object-Oriented Design
- Classical algorithms (Shunting Yard, RPN Evaluation)

## 🧠 Architecture Overview

```plaintext
[Raw Input] --> [Tokenizer] --> [Infix Tokens]
                              ↓
                        [Shunting Yard]
                              ↓
                       [Postfix Tokens]
                              ↓
                    [Postfix Evaluator]
                              ↓
                         [Final Result]

```
                         
## 🚧 Features in Progress / To Do

- Unary operators: square (²), square root (√)
- Constants: π (Pi), e
- Function support: sin, cos, log
- Error messages: better UI feedback for invalid expressions
- Visual enhancements: highlight parentheses pairs, input validation
- History log: show previous expressions/results
- Keyboard shortcuts: navigation, backspace/delete behavior improvements

--

## 📁 Folder Structure

```plaintext
src/
├── app/
│   ├── controller/
│   │   └── PrimaryController.java
│   ├── logic/
│   │   ├── CalculatorEngine.java
│   │   └── Tokenizer.java
│   ├── model/
│   │   ├── Token.java
│   │   ├── Operator.java
│   │   └── Parenthesis.java
│   └── util/
│       └── TextFieldUtil.java

```

## 🎯 Why this project?

This project was built as a learning and portfolio piece to:

- Understand expression parsing
- Apply classical algorithms in a practical UI
- Improve JavaFX proficiency
- Show clean architecture, enum-driven logic, and modular design

## 🧠 Concepts Highlighted

- Tokenization
- Shunting Yard algorithm
- Reverse Polish Notation (RPN)
- Stack-based evaluation
- MVC-ish structure in JavaFX

## 👤 Author

Made by Vitor D.G. Silva

- [Github](https://github.com/vidasilva)
- [LinkedIn](https://www.linkedin.com/in/vidasilva/)
