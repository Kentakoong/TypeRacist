package dev.typeracist.typeracist.gui.gameScene;

import dev.typeracist.typeracist.logic.gameScene.CursorDynamicColorText;
import dev.typeracist.typeracist.logic.gameScene.DynamicColorText;
import dev.typeracist.typeracist.logic.gameScene.TypingTrackedPosition;
import dev.typeracist.typeracist.logic.gameScene.TypingTracker;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TypingPane extends FlowPane {
    final static public Color DEFAULT_DYNAMIC_COLOR_TEXT_BASE_COLOR = Color.DARKGREY;
    final static public Color DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_CORRECT_COLOR = Color.GREEN;
    final static public Color DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_WRONG_COLOR = Color.RED;
    final static public Color DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_OUTOFWORD_COLOR = Color.DARKRED;

    private final List<DynamicColorText> dynamicColorWords;
    private final HashMap<Integer, List<DynamicColorText>> rowMap;
    private final TypingTracker typingTracker;

    private int maxVisibleRows = 3;
    private int currentTopRow = 0;
    private int triggerScrollRowRelativeTopCurrentTopRow = 2;
    private Font font;

    public TypingPane(List<String> words) {
        super();

        dynamicColorWords = new ArrayList<>();
        typingTracker = new TypingTracker(words);
        rowMap = new HashMap<>();

        setHgap(11);
        setVgap(8);
        setAlignment(Pos.CENTER_LEFT);
        setPrefWrapLength(600);
        setFont(new Font(24));

        initializeTypingPaneTexts();
        updateRowVisibility();

        widthProperty().addListener((obs, oldWidth, newWidth) -> {
            updateRowVisibility();

            TypingTrackedPosition position = typingTracker.getTypingTrackedPosition();

            int currentWordRow = getRowOfWord(position.wordPosition);
            if (currentWordRow >= currentTopRow + triggerScrollRowRelativeTopCurrentTopRow) {
                scrollDown();
            } else if (currentWordRow < currentTopRow) {
                scrollUp();
            }
        });

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


            int currentWordRow = getRowOfWord(position.wordPosition);
            if (currentWordRow >= currentTopRow + triggerScrollRowRelativeTopCurrentTopRow) {
                scrollDown();
            }

            if (dynamicColorWords.get(position.wordPosition) instanceof CursorDynamicColorText) {
                ((CursorDynamicColorText) dynamicColorWords.get(position.wordPosition)).setCursorPosition(position.characterPosition + 1);
            }
        });
    }


    public int getMaxVisibleRows() {
        return maxVisibleRows;
    }

    public void setMaxVisibleRows(int maxVisibleRows) {
        this.maxVisibleRows = maxVisibleRows;
        updateRowVisibility();
    }

    public int getTriggerScrollRowRelativeTopCurrentTopRow() {
        return triggerScrollRowRelativeTopCurrentTopRow;
    }

    public void setTriggerScrollRowRelativeTopCurrentTopRow(int triggerScrollRowRelativeTopCurrentTopRow) {
        this.triggerScrollRowRelativeTopCurrentTopRow = triggerScrollRowRelativeTopCurrentTopRow;
    }

    public void setFont(Font font) {
        this.font = font;

        for (DynamicColorText dynamicColorWord : dynamicColorWords) {
            dynamicColorWord.setFont(font);
        }
    }

    public void scrollDown() {
        currentTopRow++;
        updateRowVisibility();
    }

    public void scrollUp() {
        if (currentTopRow > 0) {
            currentTopRow--;
            updateRowVisibility();
        }
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

        updateRowVisibility();
    }

    private void updateRowMappings() {
        rowMap.clear();

        dynamicColorWords.forEach(word -> {
            word.setManaged(true);
            word.setVisible(true);
        });

        layout();

        List<Double> rowPositions = getChildren().stream()
                .filter(Node::isVisible)
                .map(node -> node.getBoundsInParent().getMinY())
                .distinct()
                .sorted()
                .toList();

        for (DynamicColorText word : dynamicColorWords) {
            double yPos = word.getBoundsInParent().getMinY();

            int rowIndex = rowPositions.indexOf(yPos);
            if (rowIndex >= 0) {
                rowMap.computeIfAbsent(rowIndex, k -> new ArrayList<>()).add(word);
            }
        }
    }

    private void updateRowVisibility() {
        updateRowMappings();

        dynamicColorWords.forEach(word -> {
            word.setManaged(false);
            word.setVisible(false);
        });

        for (int i = currentTopRow; i < currentTopRow + maxVisibleRows; i++) {
            System.out.println("interested: " + i);
            if (rowMap.containsKey(i)) {
                for (DynamicColorText word : rowMap.get(i)) {
                    word.setManaged(true);
                    word.setVisible(true);
                    System.out.println(i + "-" + word.toString());
                }
            }
        }
    }

    protected int getRowOfWord(int wordIndex) {
        if (wordIndex < 0 || wordIndex >= dynamicColorWords.size()) {
            return -1;
        }

        DynamicColorText word = dynamicColorWords.get(wordIndex);

        for (int rowIdx : rowMap.keySet()) {
            if (rowMap.get(rowIdx).contains(word)) {
                return rowIdx;
            }
        }

        return -1;
    }

    protected DynamicColorText renderWord(int index) {
        return renderWord(
                index,
                DEFAULT_DYNAMIC_COLOR_TEXT_BASE_COLOR,
                DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_CORRECT_COLOR,
                DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_WRONG_COLOR,
                DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_OUTOFWORD_COLOR
        );
    }

    protected DynamicColorText renderWord(int index, Color baseColor, Color highlightCorrectColor,
                                          Color highlightWrongColor, Color highlightOutOfWordColor) {
        if (index < 0 || index >= typingTracker.getWords().size()) {
            throw new IllegalArgumentException("Invalid index");
        }

        DynamicColorText newDynamicColorText = new CursorDynamicColorText(typingTracker.getWords().get(index), baseColor);
        newDynamicColorText.setFont(font);

        if (index >= typingTracker.getTrackedWords().size()) {
            return newDynamicColorText;
        }

        renderDynamicColorText(index, newDynamicColorText, baseColor, highlightCorrectColor, highlightWrongColor, highlightOutOfWordColor);

        return newDynamicColorText;
    }

    protected void reRenderWord(int index) {
        reRenderWord(
                index,
                DEFAULT_DYNAMIC_COLOR_TEXT_BASE_COLOR,
                DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_CORRECT_COLOR,
                DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_WRONG_COLOR,
                DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_OUTOFWORD_COLOR
        );
    }

    protected void reRenderWord(int index, Color baseColor, Color highlightCorrectColor, Color highlightWrongColor, Color hightlightOutofWordColor) {
        if (index < 0 || index >= typingTracker.getWords().size()) {
            throw new IllegalArgumentException("Invalid index");
        }

        DynamicColorText dynamicColorWord = dynamicColorWords.get(index);
        renderDynamicColorText(index, dynamicColorWord, baseColor, highlightCorrectColor, highlightWrongColor, hightlightOutofWordColor);
    }

    protected void renderDynamicColorText(int index, DynamicColorText dynamicColorText, Color baseColor, Color highlightCorrectColor, Color highlightWrongColor, Color hightlightOutofWordColor) {
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
                dynamicColorText.highlightCharacter(i, hightlightOutofWordColor);
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
