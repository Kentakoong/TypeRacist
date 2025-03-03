package dev.typeracist.typeracist.logic.gameScene;

import dev.typeracist.typeracist.logic.characters.Enemy;

import java.util.HashMap;
import java.util.Map;

public class BattlePaneStateContext {
    final private Enemy enemy;
    final private Dataset dataset;
    final private Map<Integer, BattlePaneTurnContext> turnContext;

    private BattlePaneState currentState;
    private int currentTurn;

    public BattlePaneStateContext(Enemy enemy, Dataset dataset) {
        this.enemy = enemy;
        this.dataset = dataset;

        this.currentState = BattlePaneState.ENEMY_DESCRIPTION;
        this.turnContext = new HashMap<>();
        this.currentTurn = 1;

        setCurrentTurn(this.currentTurn);
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Dataset getDataset() {
        return dataset;
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

    public BattlePaneTurnContext getCurrentTurnContext() {
        return turnContext.get(currentTurn);
    }

    public void incrementTurn() {
        setCurrentTurn(currentTurn + 1);
    }
}