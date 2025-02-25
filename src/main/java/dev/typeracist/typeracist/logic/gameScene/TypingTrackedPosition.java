package dev.typeracist.typeracist.logic.gameScene;

public class TypingTrackedPosition {
    final public int wordPosition;
    final public int characterPosition;

    TypingTrackedPosition(int wordPosition, int characterPosition) {
        this.wordPosition = wordPosition;
        this.characterPosition = characterPosition;
    }
}
