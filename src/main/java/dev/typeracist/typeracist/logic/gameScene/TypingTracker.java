package dev.typeracist.typeracist.logic.gameScene;

import java.util.ArrayList;
import java.util.List;

public class TypingTracker {
    private final List<String> words;
    private final List<String> trackedWords;
    private long startTime;
    private long totalElapsedTime;
    private boolean isRunning;

    public TypingTracker(List<String> words) {
        this.words = words;
        this.trackedWords = new ArrayList<>();
        this.trackedWords.add(""); // Ensure at least one word is tracked
        this.startTime = -1;
        this.totalElapsedTime = 0;
        this.isRunning = false;
    }

    public void start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
        }
    }

    public void pause() {
        if (isRunning) {
            totalElapsedTime += System.currentTimeMillis() - startTime;
            startTime = -1;
            isRunning = false;
        }
    }

    public void stop() {
        startTime = -1;
        totalElapsedTime = 0;
        isRunning = false;
        trackedWords.clear();
        trackedWords.add("");
    }

    public void addCharacter(String character) {
        if (!isRunning) start(); // Auto-start if not running

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
        if (trackedWords.isEmpty() || (trackedWords.size() == 1 && trackedWords.getFirst().isEmpty())) {
            return;
        }
        if (getLastTypedWord().isEmpty()) {
            trackedWords.removeLast();
        } else {
            removeLastCharacter();
        }
    }

    public boolean checkWord(int index) {
        if (index < 0 || index >= words.size() || index >= trackedWords.size()) {
            return false;
        }
        return trackedWords.get(index).equals(words.get(index));
    }

    public double calculateCorrectWPM() {
        long elapsedTime = getElapsedTime();
        if (elapsedTime == 0) return 0.0;

        int correctWordCount = 0;
        for (int i = 0; i < Math.min(words.size(), trackedWords.size()); i++) {
            if (checkWord(i)) {
                correctWordCount++;
            }
        }

        System.out.println(correctWordCount);
        return (correctWordCount * 60000.0) / elapsedTime;
    }

    public double calculateRawWPM() {
        long elapsedTime = getElapsedTime();
        if (elapsedTime == 0) return 0.0;

        int totalTypedWords = trackedWords.size();
        return (totalTypedWords * 60000.0) / elapsedTime;
    }

    
    public TypingTrackedPosition getTypingTrackedPosition() {
        assert !trackedWords.isEmpty();
        return new TypingTrackedPosition(trackedWords.size() - 1, trackedWords.getLast().length() - 1);
    }

    private long getElapsedTime() {
        if (isRunning) {
            return totalElapsedTime + (System.currentTimeMillis() - startTime);
        }
        return totalElapsedTime;
    }

    public List<String> getTrackedWords() {
        return trackedWords;
    }

    public List<String> getWords() {
        return words;
    }

    private String getLastTypedWord() {
        return trackedWords.getLast();
    }

    private void appendCharacter(String character) {
        trackedWords.set(trackedWords.size() - 1, getLastTypedWord().concat(character));
    }

    private void removeLastCharacter() {
        String lastWord = getLastTypedWord();
        trackedWords.set(trackedWords.size() - 1, lastWord.substring(0, lastWord.length() - 1));
    }
}
