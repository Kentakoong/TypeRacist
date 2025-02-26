package dev.typeracist.typeracist.logic.gameScene;

import java.util.*;

public class DatasetManager {
    private final Map<String, Dataset> datasets;

    public DatasetManager() {
        this.datasets = new HashMap<>();
    }

    public void addDataset(String datasetName, Dataset dataset) {
        datasets.put(datasetName, dataset);
    }

    public Collection<Dataset> getAllDataSets() {
        return datasets.values();
    }

    public Dataset getDataSet(String name) {
        return datasets.get(name);
    }

    public double getMaxWordScore() {
        return datasets.values().stream()
                .mapToDouble(Dataset::getMaxWordScore)
                .max()
                .orElse(0.0);
    }

    public double getMinWordScore() {
        return datasets.values().stream()
                .mapToDouble(Dataset::getMinWordScore)
                .min()
                .orElse(0.0);
    }

    public double getMaxParagraphScore() {
        return datasets.values().stream()
                .mapToDouble(Dataset::getMaxParagraphScore)
                .max()
                .orElse(0.0);
    }

    public double getMinParagraphScore() {
        return datasets.values().stream()
                .mapToDouble(Dataset::getMinParagraphScore)
                .min()
                .orElse(0.0);
    }

    public String getRandomWordByScoreRange(double minScore, double maxScore) {
        List<String> wordsInRange = new ArrayList<>();
        for (Dataset dataset : datasets.values()) {
            wordsInRange.addAll(dataset.getWordsByScoreRange(minScore, maxScore));
        }
        return wordsInRange.isEmpty() ? null : wordsInRange.get(new Random().nextInt(wordsInRange.size()));
    }

    public List<String> getRandomParagraphByScoreRange(double minScore, double maxScore) {
        List<List<String>> paragraphsInRange = new ArrayList<>();
        for (Dataset dataset : datasets.values()) {
            paragraphsInRange.addAll(dataset.getParagraphsByScoreRange(minScore, maxScore));
        }
        return paragraphsInRange.isEmpty() ? null : paragraphsInRange.get(new Random().nextInt(paragraphsInRange.size()));
    }

    public List<String> getWordsByScoreRange(double minScore, double maxScore) {
        List<String> wordsInRange = new ArrayList<>();
        for (Dataset dataset : datasets.values()) {
            wordsInRange.addAll(dataset.getWordsByScoreRange(minScore, maxScore));
        }
        return wordsInRange;
    }

    public List<List<String>> getParagraphsByScoreRange(double minScore, double maxScore) {
        List<List<String>> paragraphsInRange = new ArrayList<>();
        for (Dataset dataset : datasets.values()) {
            paragraphsInRange.addAll(dataset.getParagraphsByScoreRange(minScore, maxScore));
        }
        return paragraphsInRange;
    }

    public String getRandomWord() {
        List<String> allWords = new ArrayList<>();
        for (Dataset dataset : datasets.values()) {
            allWords.addAll(dataset.getWords());
        }
        return allWords.isEmpty() ? null : allWords.get(new Random().nextInt(allWords.size()));
    }

    public List<String> getRandomParagraph() {
        List<String> allParagraphs = new ArrayList<>();
        for (Dataset dataset : datasets.values()) {
            allParagraphs.addAll(dataset.getParagraphs());
        }
        if (allParagraphs.isEmpty()) return null;
        return Arrays.asList(allParagraphs.get(new Random().nextInt(allParagraphs.size())).split("\\s+"));
    }
}
