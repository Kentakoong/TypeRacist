package dev.typeracist.typeracist.gui.gameScene;

import dev.typeracist.typeracist.logic.gameScene.CursorDynamicColorText;
import dev.typeracist.typeracist.logic.gameScene.DynamicColorText;
import dev.typeracist.typeracist.logic.gameScene.TypingTrackedPosition;
import dev.typeracist.typeracist.logic.gameScene.TypingTracker;
import dev.typeracist.typeracist.utils.EventProcessor;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
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

    protected final List<DynamicColorText> dynamicColorWords;
    protected final HashMap<Integer, List<DynamicColorText>> rowMap;
    protected final TypingTracker typingTracker;

    protected int maxVisibleRows;
    protected int currentTopRow;
    protected int triggerScrollRowRelativeTopCurrentTopRow;

    protected Color baseColor;
    protected Color highlightCorrectColor;
    protected Color highlightWrongColor;
    protected Color highlightOutOfWordColor;
    protected Font font;

    protected boolean firstTypeHandled;
    protected EventHandler<KeyEvent> onFirstTypeHandler;
    protected EventProcessor<KeyEvent> onTypeProcessor;

    public TypingPane(List<String> words) {
        super();

        dynamicColorWords = new ArrayList<>();
        typingTracker = new TypingTracker(words);
        rowMap = new HashMap<>();
        maxVisibleRows = 3;
        currentTopRow = 0;
        triggerScrollRowRelativeTopCurrentTopRow = 2;
        firstTypeHandled = false;
        baseColor = DEFAULT_DYNAMIC_COLOR_TEXT_BASE_COLOR;
        highlightCorrectColor = DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_CORRECT_COLOR;
        highlightWrongColor = DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_WRONG_COLOR;
        highlightOutOfWordColor = DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_OUTOFWORD_COLOR;

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

        super.setOnKeyPressed(event -> {

            if (onTypeProcessor != null) {
                event = onTypeProcessor.process(event);
            }

            if (!firstTypeHandled) {
                firstTypeHandled = true;
                if (onFirstTypeHandler != null) {
                    onFirstTypeHandler.handle(event);
                }
            }

            switch (event.getCode()) {
                case SPACE -> typingTracker.addNewWord();
                case BACK_SPACE -> typingTracker.removeCharacter();
                default -> {
                    String input = event.getText();

                    if (!input.isEmpty() && input.codePointAt(0) >= 32 && input.codePointAt(0) < 127) {
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
                ((CursorDynamicColorText) dynamicColorWords.get(position.wordPosition))
                        .setCursorPosition(position.characterPosition + 1);
            }
        });
    }

    public void setOnFirstType(EventHandler<KeyEvent> handler) {
        onFirstTypeHandler = handler;
    }

    public void setOnType(EventProcessor<KeyEvent> processor) {
        onTypeProcessor = processor;
    }

    public void setHighlightColors(Color baseColor, Color correctColor, Color wrongColor, Color outOfWordColor) {
        this.baseColor = baseColor;
        this.highlightCorrectColor = correctColor;
        this.highlightWrongColor = wrongColor;
        this.highlightOutOfWordColor = outOfWordColor;
    }

    public Color getBaseColor() {
        return baseColor != null ? baseColor : DEFAULT_DYNAMIC_COLOR_TEXT_BASE_COLOR;
    }

    public Color getHighlightCorrectColor() {
        return highlightCorrectColor != null ? highlightCorrectColor
                : DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_CORRECT_COLOR;
    }

    public Color getHighlightWrongColor() {
        return highlightWrongColor != null ? highlightWrongColor : DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_WRONG_COLOR;
    }

    public Color getHighlightOutOfWordColor() {
        return highlightOutOfWordColor != null ? highlightOutOfWordColor
                : DEFAULT_DYNAMIC_COLOR_TEXT_HIGHLIGHT_OUTOFWORD_COLOR;
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

    public TypingTracker getTypingTracker() {
        return typingTracker;
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

    protected void initializeTypingPaneTexts() {
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

    protected void updateRowMappings() {
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

    protected void updateRowVisibility() {
        updateRowMappings();

        dynamicColorWords.forEach(word -> {
            word.setManaged(false);
            word.setVisible(false);
        });

        for (int i = currentTopRow; i < currentTopRow + maxVisibleRows; i++) {
            if (rowMap.containsKey(i)) {
                for (DynamicColorText word : rowMap.get(i)) {
                    word.setManaged(true);
                    word.setVisible(true);
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
                baseColor,
                highlightCorrectColor,
                highlightWrongColor,
                highlightOutOfWordColor);
    }

    protected DynamicColorText renderWord(int index, Color baseColor, Color highlightCorrectColor,
            Color highlightWrongColor, Color highlightOutOfWordColor) {
        if (index < 0 || index >= typingTracker.getWords().size()) {
            throw new IllegalArgumentException("Invalid index");
        }

        DynamicColorText newDynamicColorText = new CursorDynamicColorText(typingTracker.getWords().get(index),
                baseColor);
        newDynamicColorText.setFont(font);

        if (index >= typingTracker.getTrackedWords().size()) {
            return newDynamicColorText;
        }

        renderDynamicColorText(index, newDynamicColorText, baseColor, highlightCorrectColor, highlightWrongColor,
                highlightOutOfWordColor);

        return newDynamicColorText;
    }

    protected void reRenderWord(int index) {
        reRenderWord(
                index,
                baseColor,
                highlightCorrectColor,
                highlightWrongColor,
                highlightOutOfWordColor);
    }

    protected void reRenderWord(int index, Color baseColor, Color highlightCorrectColor, Color highlightWrongColor,
            Color hightlightOutofWordColor) {
        if (index < 0 || index >= typingTracker.getWords().size()) {
            throw new IllegalArgumentException("Invalid index");
        }

        DynamicColorText dynamicColorWord = dynamicColorWords.get(index);
        renderDynamicColorText(index, dynamicColorWord, baseColor, highlightCorrectColor, highlightWrongColor,
                hightlightOutofWordColor);
    }

    protected void renderDynamicColorText(int index, DynamicColorText dynamicColorText, Color baseColor,
            Color highlightCorrectColor, Color highlightWrongColor, Color hightlightOutofWordColor) {
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
            // todo: find a better way to ensure that overflow word do not cause a row limit
            // overflow
            if (i >= currentWord.length()) {
                updateRowVisibility(); // needed to be fixed
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
