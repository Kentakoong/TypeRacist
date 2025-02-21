package dev.typeracist.typeracist.logic.gameScene;

import java.util.ArrayList;
import java.util.List;

public class TypingTracker {
    private List<String> words;
    private List<String> trackedWords;

    public TypingTracker(List<String> words) {
        this.words = words;
        this.trackedWords = new ArrayList<>();

        trackedWords.add("");
    }

    public void addCharacter(String character) {
        if (trackedWords.isEmpty()) {
            addNewWord();
        }

        appendCharacter(character);
    }

    public void addNewWord() {
        if (!trackedWords.isEmpty() && getLastTypedWord().isEmpty()) {
            return;
        }

        trackedWords.add("");
    }

    public void removeCharacter() {
        if (trackedWords.isEmpty() || trackedWords.size() == 1 && trackedWords.getFirst().isEmpty()) {
            return;
        }

        if (getLastTypedWord().isEmpty() && checkWord(getTypingTrackedPosition().wordPosition - 1))
            return;

        if (getLastTypedWord().isEmpty()) {
            trackedWords.removeLast();
        }

        removeLastCharacter();
    }

    public boolean checkWord(int index) {
        if (index < 0 || index >= words.size()) {
            throw new IllegalArgumentException("Invalid index");
        }

        if (index >= trackedWords.size()) {
            return false;
        }

        return trackedWords.get(index).equals(words.get(index));
    }

    public TypingTrackedPosition getTypingTrackedPosition() {
        assert !trackedWords.isEmpty();
        return new TypingTrackedPosition(trackedWords.size() - 1, trackedWords.getLast().length() - 1);
    }

    public String getLastTypedWord() {
        return trackedWords.getLast();
    }

    public List<String> getTrackedWords() {
        return trackedWords;
    }

    public List<String> getWords() {
        return words;
    }

//    private void renderDynamicColorText(int index, DynamicColorText dynamicColorText, Color baseColor, Color highlightCorrectColor, Color highlightWrongColor) {
//
//        String currentTypedWord = trackedWords.get(index);
//        String currentWord = words.get(index);
//
//        int dynamicTextLength = dynamicColorText.length();
//
//        if (currentTypedWord.length() >= currentWord.length() && dynamicTextLength != currentTypedWord.length()) {
//            dynamicColorText.setText(currentWord + currentTypedWord.substring(currentWord.length()));
//        }
//
//        int maxWordLength = Math.max(currentTypedWord.length(), currentWord.length());
//
//        assert dynamicColorText.length() == maxWordLength;
//        for (int i = 0; i < maxWordLength; i++) {
//            if (i >= currentWord.length()) {
//                dynamicColorText.highlightCharacter(i, Color.DARKRED);
//                continue;
//            }
//
//            if (i >= currentTypedWord.length()) {
//                dynamicColorText.highlightCharacter(i, baseColor);
//                continue;
//            }
//
//            if (currentTypedWord.charAt(i) == currentWord.charAt(i)) {
//                dynamicColorText.highlightCharacter(i, highlightCorrectColor);
//            } else {
//                dynamicColorText.highlightCharacter(i, highlightWrongColor);
//            }
//        }
//
//    }

    private void appendCharacter(String character) {
        trackedWords.set(trackedWords.size() - 1, getLastTypedWord().concat(character));
    }

    private void removeLastCharacter() {
        trackedWords.set(trackedWords.size() - 1, getLastTypedWord().substring(0, getLastTypedWord().length() - 1));
    }
}
