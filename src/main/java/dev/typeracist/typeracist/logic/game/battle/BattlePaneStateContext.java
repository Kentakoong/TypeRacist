package dev.typeracist.typeracist.logic.game.battle;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.game.dataset.Dataset;
import dev.typeracist.typeracist.utils.DatasetWordsExtractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattlePaneStateContext {
    final private Enemy enemy;
    final private Dataset dataset;
    final private Map<Integer, BattlePaneTurnContext> turnContext;
    final private DatasetWordsExtractor datasetWordsExtractor;
    private long typingMaxTime;

    private BattlePaneState currentState;
    private int currentTurn;

    public BattlePaneStateContext(Enemy enemy, long typingMaxTime, Dataset dataset, DatasetWordsExtractor datasetWordsExtractor) {
        this.enemy = enemy;
        this.typingMaxTime = typingMaxTime;
        this.dataset = dataset;
        this.datasetWordsExtractor = datasetWordsExtractor;

        this.currentState = BattlePaneState.ENEMY_DESCRIPTION;
        this.turnContext = new HashMap<>();
        this.currentTurn = 1;

        setCurrentTurn(this.currentTurn);
    }

    public BattlePaneStateContext(BattlePaneStateContext other) {
        this.enemy = other.enemy;
        this.typingMaxTime = other.typingMaxTime;
        this.dataset = other.dataset;
        this.datasetWordsExtractor = other.datasetWordsExtractor;

        this.currentState = other.currentState;
        this.turnContext = new HashMap<>(other.turnContext);
        this.currentTurn = other.currentTurn;
    }

    public Map<Integer, BattlePaneTurnContext> getTurnContext() {
        return turnContext;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public List<String> extractDataset() {
        return datasetWordsExtractor.extractWord(dataset);
    }

    public BattlePaneState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(BattlePaneState currentState) {
        this.currentState = currentState;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        if (!turnContext.containsKey(currentTurn)) {
            turnContext.put(currentTurn, new BattlePaneTurnContext());
        }

        this.currentTurn = currentTurn;
    }

    public BattlePaneTurnContext getTurnContext(int turn) {
        return turnContext.get(turn);
    }

    public BattlePaneTurnContext ensureExistsGetTurnContext(int turn) {
        if (!turnContext.containsKey(turn)) {
            turnContext.put(turn, new BattlePaneTurnContext());
        }

        return turnContext.get(turn);
    }

    public BattlePaneTurnContext getCurrentTurnContext() {
        return turnContext.get(currentTurn);
    }

    public void incrementTurn() {
        setCurrentTurn(currentTurn + 1);
    }

    public long getTypingMaxTime() {
        return typingMaxTime;
    }

    public void setTypingMaxTime(long typingMaxTime) {
        this.typingMaxTime = typingMaxTime;
    }
}