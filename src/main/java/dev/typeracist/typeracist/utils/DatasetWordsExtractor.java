package dev.typeracist.typeracist.utils;

import java.util.List;

import dev.typeracist.typeracist.logic.game.dataset.Dataset;

@FunctionalInterface
public interface DatasetWordsExtractor {
    List<String> extractWord(Dataset dataset);
}
