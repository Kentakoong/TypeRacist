package dev.typeracist.typeracist.utils;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.*;

public class KeyConverter {
    private static boolean isWindow = System.getProperty("os.name").toLowerCase().startsWith("windows");

    // Method to convert key code to actual character considering CapsLock and Shift
    public static String convertKeyToText(KeyEvent event) {
        if (!isWindow) {
            return event.getText();
        }

        if (event.getText().length() != 1) {
            return "";
        }

        if (event.getText().isEmpty()) {
            return "";
        }

        KeyCode keyCode = event.getCode();
        boolean isShiftPressed = event.isShiftDown();
        boolean isCapsLockOn = isCapsLockActive();

        // Handle letter keys
        if (keyCode.isLetterKey()) {
            char keyChar = keyCode.getName().charAt(0);

            if (isCapsLockOn && !isShiftPressed) {
                keyChar = Character.toUpperCase(keyChar);
            } else if (isShiftPressed && !isCapsLockOn) {
                keyChar = Character.toUpperCase(keyChar);
            } else {
                keyChar = Character.toLowerCase(keyChar);
            }

            return String.valueOf(keyChar);
        }

        // Handle special characters and numbers
        if (keyCode.isDigitKey() || keyCode == KeyCode.NUMPAD0 || keyCode == KeyCode.NUMPAD1 ||
                keyCode == KeyCode.NUMPAD2 || keyCode == KeyCode.NUMPAD3 || keyCode == KeyCode.NUMPAD4 ||
                keyCode == KeyCode.NUMPAD5 || keyCode == KeyCode.NUMPAD6 || keyCode == KeyCode.NUMPAD7 ||
                keyCode == KeyCode.NUMPAD8 || keyCode == KeyCode.NUMPAD9) {

            // Handle numbers based on shift
            if (isShiftPressed) {
                switch (keyCode) {
                    case DIGIT1:
                        return "!";
                    case DIGIT2:
                        return "@";
                    case DIGIT3:
                        return "#";
                    case DIGIT4:
                        return "$";
                    case DIGIT5:
                        return "%";
                    case DIGIT6:
                        return "^";
                    case DIGIT7:
                        return "&";
                    case DIGIT8:
                        return "*";
                    case DIGIT9:
                        return "(";
                    case DIGIT0:
                        return ")";
                    case MINUS:
                        return "_";
                    case EQUALS:
                        return "+";
                    case OPEN_BRACKET:
                        return "{";
                    case CLOSE_BRACKET:
                        return "}";
                    case BACK_SLASH:
                        return "|";
                    case SEMICOLON:
                        return ":";
                    case QUOTE:
                        return "\"";
                    case COMMA:
                        return "<";
                    case PERIOD:
                        return ">";
                    case SLASH:
                        return "?";
                    default:
                        return keyCode.getName();
                }
            } else {
                return keyCode.getName();
            }
        }

        // Handle symbol characters like <, >, and others
        switch (keyCode) {
            case SEMICOLON:
                return isShiftPressed ? ":" : ";";
            case SLASH:
                return isShiftPressed ? "?" : "/";
            case BACK_SLASH:
                return isShiftPressed ? "|" : "\\";
            case COMMA:
                return isShiftPressed ? "<" : ",";
            case PERIOD:
                return isShiftPressed ? ">" : ".";
            case QUOTE:
                return isShiftPressed ? "\"" : "'";
            case LEFT_PARENTHESIS:
                return isShiftPressed ? "(" : "9";  // Assuming key layout where '(' is Shift+9
            case RIGHT_PARENTHESIS:
                return isShiftPressed ? ")" : "0";  // Assuming key layout where ')' is Shift+0
            default:
                return keyCode.getChar();
        }
    }

    // Check if Caps Lock is active
    private static boolean isCapsLockActive() {
        return Toolkit.getDefaultToolkit().getLockingKeyState(java.awt.event.KeyEvent.VK_CAPS_LOCK);
    }
}
