package dev.typeracist.typeracist.logic;

import java.util.ArrayList;
import java.util.List;

public class SceneHistoryManager {
    private final List<String> sceneHistory;

    public SceneHistoryManager() {
        sceneHistory = new ArrayList<>();
    }

    public void addSceneToHistory(String sceneName) {
        sceneHistory.add(sceneName);
    }

    public String getPreviousScene() {
        if (sceneHistory.size() < 2) {
            return null;
        }
        sceneHistory.removeLast();
        return sceneHistory.getLast();
    }

    public boolean hasPreviousScene() {
        return sceneHistory.size() > 1;
    }

    public void clearHistory() {
        sceneHistory.clear();
    }
}