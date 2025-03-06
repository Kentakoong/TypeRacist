package dev.typeracist.typeracist.logic.game.typing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TypingHardness {
    private static final Set<Character> LEFT_HAND = new HashSet<>();
    private static final Set<Character> RIGHT_HAND = new HashSet<>();
    private static final Set<Character> TOP_ROW = new HashSet<>();
    private static final Set<Character> BOTTOM_ROW = new HashSet<>();
    private static final Set<Character> NUMBERS = new HashSet<>();
    private static final Set<Character> SPECIAL_CHARS = new HashSet<>();
    private static final Set<Character> PINKY_KEYS = new HashSet<>();

    static {
        for (char c : "qwertasdfgzxcvb".toCharArray())
            LEFT_HAND.add(c);
        for (char c : "yuiophjklnm".toCharArray())
            RIGHT_HAND.add(c);
        for (char c : "qwertyuiop1234567890".toCharArray())
            TOP_ROW.add(c);
        for (char c : "zxcvbnm".toCharArray())
            BOTTOM_ROW.add(c);
        for (char c : "1234567890".toCharArray())
            NUMBERS.add(c);
        for (char c : "`~!@#$%^&*()-_=+[{]};:'\",<.>/?\\|".toCharArray())
            SPECIAL_CHARS.add(c);
        for (char c : "qazp;~!@_+{}[]|\\".toCharArray())
            PINKY_KEYS.add(c);
    }

    public static double paragraphTypingHardness(String paragraph) {
        double score = 0;

        String[] words = paragraph.split("\\s+");

        for (String word : words) {
            score += wordTypingHardness(word.strip());
        }

        return score / words.length;
    }

    public static double paragraphTypingHardness(List<String> paragraph) {
        double score = 0;

        for (String word : paragraph) {
            score += wordTypingHardness(word.strip());
        }

        return score / paragraph.size();
    }

    public static double paragraphTypingHardness(List<String> paragraph, int wordLimit) {
        double score = 0;

        int limit = Math.min(wordLimit, paragraph.size());

        if (limit == 0)
            return 0;

        for (int i = 0; i < limit; i++) {
            score += wordTypingHardness(paragraph.get(i).strip());
        }

        return score / limit;
    }

    public static double paragraphTypingHardness(String paragraph, int wordLimit) {
        double score = 0;
        String[] words = paragraph.split("\\s+");

        int limit = Math.min(wordLimit, words.length);

        if (limit == 0)
            return 0;

        for (int i = 0; i < limit; i++) {
            score += wordTypingHardness(words[i].strip());
        }

        return score / limit;
    }

    public static double wordTypingHardness(String word) {
        double score = word.length(); // Base difficulty: length of word
        Character prevChar = null;
        String prevHand = null;
        int repeatCount = 0; // Track consecutive same-character repetitions

        for (char ch : word.toCharArray()) {
            boolean shiftNeeded = Character.isUpperCase(ch) || "!@#$%^&*()_+{}:\"<>?".indexOf(ch) != -1;
            if (shiftNeeded)
                score += 1.5; // Shift key adds more difficulty

            if (NUMBERS.contains(ch))
                score += 1; // Numbers are slightly harder to type
            if (SPECIAL_CHARS.contains(ch))
                score += 2; // Special characters are awkward to type
            if (PINKY_KEYS.contains(Character.toLowerCase(ch)))
                score += 1; // Pinky finger is weaker

            // Determine row difficulty
            if (BOTTOM_ROW.contains(Character.toLowerCase(ch)))
                score += 1; // Bottom row is harder
            if (TOP_ROW.contains(Character.toLowerCase(ch)))
                score -= 0.1; // Top row is easier

            // Determine hand usage
            String hand = LEFT_HAND.contains(Character.toLowerCase(ch)) ? "left"
                    : RIGHT_HAND.contains(Character.toLowerCase(ch)) ? "right" : null;

            // Penalize for not alternating hands
            if (prevHand != null && prevHand.equals(hand))
                score += 0.5;

            // Check for same-character repetition
            if (prevChar != null && prevChar == ch) {
                repeatCount++;
                score -= Math.min(0.5 * Math.min(repeatCount, 2), 1.5); // Reduce difficulty for repeated letters
            } else {
                repeatCount = 0; // Reset if different character is typed
            }

            prevChar = ch;
            prevHand = hand;
        }

        return Math.max(Math.round(score * 100.0) / 100.0, 1.0); // Ensure score doesn't go negative
    }
}