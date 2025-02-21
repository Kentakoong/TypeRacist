package dev.typeracist.typeracist.logic.gameScene;

public class TypingTrackedPosition {
    final static public int EMPTY_WORD_INDEX = -1;

    final public int wordPosition;
    final public int characterPosition;

    TypingTrackedPosition(int wordPosition, int characterPosition) {
        this.wordPosition = wordPosition;
        this.characterPosition = characterPosition;
    }
}
