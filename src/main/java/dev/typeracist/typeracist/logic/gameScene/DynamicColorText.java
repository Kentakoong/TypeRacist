package dev.typeracist.typeracist.logic.gameScene;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class DynamicColorText extends HBox {
    private final List<Text> characters;
    private final Color baseColor;

    public DynamicColorText(String text, Color baseColor) {
        super();

        this.characters = new ArrayList<>();
        this.baseColor = baseColor;

        setText(text);
    }

    public void setText(String text) {
        int oldLength = length();
        int newLength = text.length();

        // Remove excess characters
        if (newLength < oldLength) {
            characters.subList(newLength, oldLength).clear();
            getChildren().subList(newLength, oldLength).clear();
        }
        // Add missing characters
        else if (newLength > oldLength) {
            for (int i = oldLength; i < newLength; i++) {
                Text newText = new Text();
                newText.setFill(baseColor);
                characters.add(newText);
                getChildren().add(newText);
            }
        }

        assert text.length() == length();
        for (int i = 0; i < newLength; i++) {
            characters.get(i).setText(String.valueOf(text.charAt(i)));
        }
    }

    public void highlightCharacter(int index, Color highlightedColor) {
        if (index < 0 || index >= characters.size()) {
            throw new IllegalArgumentException("Invalid index");
        }
        characters.get(index).setFill(highlightedColor);
    }

    public void resetCharacterColor(int index) {
        if (index < 0 || index >= characters.size()) {
            throw new IllegalArgumentException("Invalid index");
        }
        characters.get(index).setFill(baseColor);
    }

    public void resetColors() {
        for (Text text : characters) {
            text.setFill(baseColor);
        }
    }

    public void resetColors(Color baseColor) {
        for (Text text : characters) {
            text.setFill(baseColor);
        }
    }

    public void setFont(Font font) {
        for (Text text : characters) {
            text.setFont(font);
        }
    }

    public Text getCharacter(int index) {
        if (index < 0 || index >= characters.size()) {
            throw new IllegalArgumentException("Invalid index");
        }
        return characters.get(index);
    }

    public int length() {
        return characters.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Text text : characters) {
            builder.append(text.getText());
        }

        return builder.toString();
    }
}
