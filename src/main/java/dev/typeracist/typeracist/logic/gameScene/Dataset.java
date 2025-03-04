package dev.typeracist.typeracist.logic.gameScene;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Dataset {
    private final List<String> paragraphs;
    private final List<String> words;
    private final Random random = new Random();
    private LinkedHashMap<String, Double> rankedWords;
    private LinkedHashMap<String, Double> rankedParagraphs;

    public Dataset(String filePath) {
        paragraphs = readJsonFile(filePath);
        words = new ArrayList<>();
        rankedWords = new LinkedHashMap<>();
        rankedParagraphs = new LinkedHashMap<>();
    }

    public Dataset(List<String> paragraphs) {
        this.paragraphs = new ArrayList<>(paragraphs);
        this.words = new ArrayList<>();
        this.rankedWords = new LinkedHashMap<>();
        this.rankedParagraphs = new LinkedHashMap<>();
    }

    public Dataset(String filePath, boolean lowerCase, boolean noPunctuation, boolean noNumber) {
        paragraphs = readJsonFile(filePath);
        words = new ArrayList<>();
        rankedWords = new LinkedHashMap<>();
        rankedParagraphs = new LinkedHashMap<>();

        applyTransformations(lowerCase, noPunctuation, noNumber);
    }

    public Dataset(List<String> paragraphs, boolean lowerCase, boolean noPunctuation, boolean noNumber) {
        this.paragraphs = new ArrayList<>(paragraphs);
        this.words = new ArrayList<>();
        this.rankedWords = new LinkedHashMap<>();
        this.rankedParagraphs = new LinkedHashMap<>();

        applyTransformations(lowerCase, noPunctuation, noNumber);
    }

    private static List<String> readJsonFile(String filePath) {
        List<String> paragraphs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Dataset.class.getResourceAsStream(filePath))))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            JSONArray jsonArray = new JSONArray(content.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                paragraphs.add(jsonArray.getString(i));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load dataset from file: " + filePath, e);
        }
        return paragraphs;
    }

    public void initializeWithRanking() {
        words.clear();
        words.addAll(extractWords(paragraphs));
        rankedWords = rankWordsByTypingHardness(words);
        rankedParagraphs = rankParagraphsByTypingHardness(paragraphs);
    }

    public List<String> getWordsByScoreRange(double minScore, double maxScore) {
        return rankedWords.entrySet().stream()
                .filter(entry -> entry.getValue() >= minScore && entry.getValue() <= maxScore)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<List<String>> getParagraphsByScoreRange(double minScore, double maxScore) {
        return rankedParagraphs.entrySet().stream()
                .filter(entry -> entry.getValue() >= minScore && entry.getValue() <= maxScore)
                .map(entry -> Arrays.asList(entry.getKey().split("\\s+"))) // Convert to list of words
                .collect(Collectors.toList());
    }

    public String getRandomWordByScoreRange(double minScore, double maxScore) {
        List<String> wordsInRange = getWordsByScoreRange(minScore, maxScore);
        if (wordsInRange.isEmpty())
            return null;
        Random random = new Random();
        return wordsInRange.get(random.nextInt(wordsInRange.size()));
    }

    public List<String> getRandomParagraphByScoreRange(double minScore, double maxScore) {
        List<List<String>> paragraphsInRange = getParagraphsByScoreRange(minScore, maxScore);
        if (paragraphsInRange.isEmpty())
            return null;
        Random random = new Random();
        return paragraphsInRange.get(random.nextInt(paragraphsInRange.size()));
    }

    public List<String> getRandomWords(int count) {
        if (words.isEmpty())
            return Collections.emptyList();
        return random.ints(0, words.size())
                .distinct()
                .limit(count)
                .mapToObj(words::get)
                .collect(Collectors.toList());
    }

    public List<List<String>> getRandomParagraphs(int count) {
        if (paragraphs.isEmpty())
            return Collections.emptyList();
        return random.ints(0, paragraphs.size())
                .distinct()
                .limit(count)
                .mapToObj(index -> Arrays.asList(paragraphs.get(index).split("\\s+")))
                .collect(Collectors.toList());
    }

    public List<String> getRandomWordsByScoreRange(double minScore, double maxScore, int count) {
        List<String> wordsInRange = rankedWords.entrySet().stream()
                .filter(entry -> entry.getValue() >= minScore && entry.getValue() <= maxScore)
                .map(Map.Entry::getKey)
                .toList();

        if (wordsInRange.isEmpty())
            return Collections.emptyList();
        return random.ints(0, wordsInRange.size())
                .distinct()
                .limit(count)
                .mapToObj(wordsInRange::get)
                .collect(Collectors.toList());
    }

    public List<List<String>> getRandomParagraphsByScoreRange(double minScore, double maxScore, int count) {
        List<String> paragraphsInRange = rankedParagraphs.entrySet().stream()
                .filter(entry -> entry.getValue() >= minScore && entry.getValue() <= maxScore)
                .map(Map.Entry::getKey)
                .toList();

        if (paragraphsInRange.isEmpty())
            return Collections.emptyList();
        return random.ints(0, paragraphsInRange.size())
                .distinct()
                .limit(count)
                .mapToObj(index -> Arrays.asList(paragraphsInRange.get(index).split("\\s+")))
                .collect(Collectors.toList());
    }

    public String getRandomWord() {
        if (words.isEmpty())
            return null;
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public List<String> getRandomParagraph() {
        if (paragraphs.isEmpty())
            return null;
        Random random = new Random();
        String paragraph = paragraphs.get(random.nextInt(paragraphs.size()));
        return Arrays.asList(paragraph.split("\\s+")); // Convert to list of words
    }

    public double getMaxWordScore() {
        return rankedWords.isEmpty() ? 0.0 : Collections.max(rankedWords.values());
    }

    public double getMinWordScore() {
        return rankedWords.isEmpty() ? 0.0 : Collections.min(rankedWords.values());
    }

    public double getMaxParagraphScore() {
        return rankedParagraphs.isEmpty() ? 0.0 : Collections.max(rankedParagraphs.values());
    }

    public double getMinParagraphScore() {
        return rankedParagraphs.isEmpty() ? 0.0 : Collections.min(rankedParagraphs.values());
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public List<String> getWords() {
        return words;
    }

    public LinkedHashMap<String, Double> getRankedWords() {
        return rankedWords;
    }

    public LinkedHashMap<String, Double> getRankedParagraphs() {
        return rankedParagraphs;
    }

    public void addParagraph(String paragraph) {
        paragraphs.add(paragraph);
    }

    private void applyTransformations(boolean lowerCase, boolean noPunctuation, boolean noNumber) {
        if (lowerCase)
            paragraphs.replaceAll(String::toLowerCase);
        if (noPunctuation)
            paragraphs.replaceAll(s -> s.replaceAll("[^a-zA-Z0-9\\s]", ""));
        if (noNumber)
            paragraphs.replaceAll(s -> s.replaceAll("[0-9]", ""));
    }

    private List<String> extractWords(List<String> paragraphs) {
        HashSet<String> uniqueWords = new HashSet<>();
        String delimiters = "[\\s(),;.!?\"'<>:/\\[\\]{}|^&*_=+]+";
        String regex = "^[^a-zA-Z-]+|[^a-zA-Z-]+$";

        for (String paragraph : paragraphs) {
            for (String word : paragraph.split(delimiters)) {
                if (!word.isEmpty()) {
                    String cleanedWord = word.replaceAll(regex, "");
                    if (cleanedWord.contains("-")) {
                        cleanedWord = cleanedWord.replaceAll("^-|-$", "");
                    }
                    if (!cleanedWord.isEmpty()) {
                        uniqueWords.add(cleanedWord);
                    }
                }
            }
        }
        return new ArrayList<>(uniqueWords);
    }

    private LinkedHashMap<String, Double> rankWordsByTypingHardness(List<String> words) {
        return words.stream()
                .collect(Collectors.toMap(
                        word -> word,
                        TypingHardness::wordTypingHardness,
                        (a, b) -> a,
                        LinkedHashMap::new))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new));
    }

    private LinkedHashMap<String, Double> rankParagraphsByTypingHardness(List<String> paragraphs) {
        return paragraphs.stream()
                .collect(Collectors.toMap(
                        paragraph -> paragraph,
                        TypingHardness::paragraphTypingHardness,
                        (a, b) -> a,
                        LinkedHashMap::new))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new));
    }
}
