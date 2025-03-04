package dev.typeracist.typeracist.utils;

import dev.typeracist.typeracist.logic.gameScene.Dataset;

import java.util.List;

@FunctionalInterface
public interface DatasetWordsExtractor {
    List<String> extractWord(Dataset dataset);
}
