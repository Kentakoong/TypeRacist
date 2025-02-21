package dev.typeracist.typeracist.gui.gameScene;

import dev.typeracist.typeracist.logic.gameScene.CursorDynamicColorText;
import dev.typeracist.typeracist.logic.gameScene.DynamicColorText;
import dev.typeracist.typeracist.logic.gameScene.TypingTrackedPosition;
import dev.typeracist.typeracist.logic.gameScene.TypingTracker;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class TypingPane extends FlowPane {
    final static public Color DEFAULT_DYNAMIC_COLOR_TEXT_BASE_COLOR = Color.DARKGREY;
    final static public Color DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_CORRECT_COLOR = Color.GREEN;
    final static public Color DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_WRONG_COLOR = Color.RED;

    private final List<DynamicColorText> dynamicColorWords;
    private final TypingTracker typingTracker;

    private Font font;

    public TypingPane(List<String> words) {
        super();

        dynamicColorWords = new ArrayList<>();
        typingTracker = new TypingTracker(words);

        setHgap(11);
        setVgap(8);
        setAlignment(Pos.CENTER_LEFT);
        setPrefWrapLength(600);
        setFont(new Font(24));

        initializeTypingPaneTexts();

        setFocusTraversable(true);
        requestFocus();

        setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE -> typingTracker.addNewWord();
                case BACK_SPACE -> typingTracker.removeCharacter();
                default -> {
                    String input = event.getText();

                    if (!input.isEmpty() && input.codePointAt(0) >= 32 && input.codePointAt(0) < 127) {
                        System.out.println("Typed: " + input);
                        typingTracker.addCharacter(input);
                    }
                }
            }

            TypingTrackedPosition position = typingTracker.getTypingTrackedPosition();
            reRenderWord(position.wordPosition);

            if (dynamicColorWords.get(position.wordPosition) instanceof CursorDynamicColorText) {
                // should be last method to call, because need rendered pane to works.
                ((CursorDynamicColorText) dynamicColorWords.get(position.wordPosition)).setCursorPosition(position.characterPosition + 1);
            }
        });
    }

    private void initializeTypingPaneTexts() {
        for (int i = 0; i < typingTracker.getWords().size(); ++i) {
            DynamicColorText newDynamicColorWord = renderWord(i);

            dynamicColorWords.add(newDynamicColorWord);
            getChildren().add(newDynamicColorWord);
        }

        if (dynamicColorWords.getFirst() instanceof CursorDynamicColorText) {
            ((CursorDynamicColorText) dynamicColorWords.getFirst()).setCursorPosition(0);
        }
    }


    public DynamicColorText renderWord(int index, Color baseColor, Color highlightCorrectColor,
                                       Color highlightWrongColor) {
        if (index < 0 || index >= typingTracker.getWords().size()) {
            throw new IllegalArgumentException("Invalid index");
        }

        DynamicColorText newDynamicColorText = new CursorDynamicColorText(typingTracker.getWords().get(index), baseColor);
        newDynamicColorText.setFont(font);

        if (index >= typingTracker.getTrackedWords().size()) {
            return newDynamicColorText;
        }

        renderDynamicColorText(index, newDynamicColorText, baseColor, highlightCorrectColor, highlightWrongColor);

        return newDynamicColorText;
    }

    public DynamicColorText renderWord(int index) {
        return renderWord(
                index,
                DEFAULT_DYNAMIC_COLOR_TEXT_BASE_COLOR,
                DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_CORRECT_COLOR,
                DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_WRONG_COLOR);
    }

    public void reRenderWord(int index, Color baseColor, Color highlightCorrectColor, Color highlightWrongColor) {
        if (index < 0 || index >= typingTracker.getWords().size()) {
            throw new IllegalArgumentException("Invalid index");
        }

        DynamicColorText dynamicColorWord = dynamicColorWords.get(index);

        if (index >= typingTracker.getTrackedWords().size()) {
            dynamicColorWord.resetColors(baseColor);
            return;
        }

        renderDynamicColorText(index, dynamicColorWord, baseColor, highlightCorrectColor, highlightWrongColor);
    }

    public void reRenderWord(int index) {
        reRenderWord(
                index,
                DEFAULT_DYNAMIC_COLOR_TEXT_BASE_COLOR,
                DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_CORRECT_COLOR,
                DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_WRONG_COLOR
        );
    }

    public void setFont(Font font) {
        this.font = font;

        for (DynamicColorText dynamicColorWord : dynamicColorWords) {
            dynamicColorWord.setFont(font);
        }
    }

    protected void renderDynamicColorText(int index, DynamicColorText dynamicColorText, Color baseColor, Color highlightCorrectColor, Color highlightWrongColor) {
        String currentTypedWord = typingTracker.getTrackedWords().get(index);
        String currentWord = typingTracker.getWords().get(index);

        int dynamicTextLength = dynamicColorText.length();

        if (currentTypedWord.length() >= currentWord.length() && dynamicTextLength != currentTypedWord.length()) {
            dynamicColorText.setText(currentWord + currentTypedWord.substring(currentWord.length()));
            dynamicColorText.setFont(font);
        }

        int maxWordLength = Math.max(currentTypedWord.length(), currentWord.length());

        assert dynamicColorText.length() == maxWordLength;
        for (int i = 0; i < maxWordLength; i++) {
            if (i >= currentWord.length()) {
                dynamicColorText.highlightCharacter(i, Color.DARKRED);
                continue;
            }

            if (i >= currentTypedWord.length()) {
                dynamicColorText.highlightCharacter(i, baseColor);
                continue;
            }

            if (currentTypedWord.charAt(i) == currentWord.charAt(i)) {
                dynamicColorText.highlightCharacter(i, highlightCorrectColor);
            } else {
                dynamicColorText.highlightCharacter(i, highlightWrongColor);
            }
        }

    }
}

