package dev.typeracist.typeracist.logic.gameScene;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier.*;
import dev.typeracist.typeracist.logic.global.GameLogic;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BattlePaneStateManager {
    private final BattlePane battlePane;
    private final BattlePaneStateContext context;
    private final Map<BattlePaneState, BasePaneModifier> stateModifiers = new HashMap<>();
    private final Stack<BasePaneModifier> modifierStack = new Stack<>();
    private BasePaneModifier currentModifier;

    public BattlePaneStateManager(BattlePane battlePane, BattlePaneStateContext context) {
        this.battlePane = battlePane;
        this.context = context;
        initializeStateModifiers();
    }

    private void initializeStateModifiers() {
        stateModifiers.put(BattlePaneState.PLAYER_BEFORE_ATTACK_ITEM_SELECTION, new PlayerItemSelectionPaneModifier(battlePane, context));
        stateModifiers.put(BattlePaneState.PLAYER_BEFORE_DEFENSE_ITEM_SELECTION, new PlayerItemSelectionPaneModifier(battlePane, context));
        stateModifiers.put(BattlePaneState.ENEMY_DESCRIPTION, new EnemyDescriptionPaneModifier(battlePane, context));
        stateModifiers.put(BattlePaneState.PLAYER_ATTACK, new PlayerAttackPaneModifier(battlePane, context));
        stateModifiers.put(BattlePaneState.PLAYER_ATTACK_RESULT,
                new PlayerAttackResultPaneModifier(battlePane, context));
        stateModifiers.put(BattlePaneState.ENEMY_BEFORE_ATTACK, new EnemyBeforeAttackPaneModifier(battlePane, context));
        stateModifiers.put(BattlePaneState.PLAYER_DEFENSE, new PlayerDefensePaneModifier(battlePane, context));
        stateModifiers.put(BattlePaneState.PLAYER_DEFENSE_RESULT,
                new PlayerDefenseResultPaneModifier(battlePane, context));
        stateModifiers.put(BattlePaneState.GAME_WIN, new GameWinPaneModifier(battlePane, context));
        stateModifiers.put(BattlePaneState.GAME_LOSE, new GameLosePaneModifier(battlePane, context));
    }

    public void transitionToState(BattlePaneState newState) {
        context.setCurrentState(newState);

        handleSpecialStateTransition(newState);

        BasePaneModifier modifier = stateModifiers.get(newState);
        if (modifier != null) {
            modifier.setManager(this);
            modifierStack.push(modifier);
            processNextModifier();
        }
    }

    private void handleSpecialStateTransition(BattlePaneState newState) {
        switch (newState) {
            case GAME_WIN:
                System.out.println("Game won! Handle any win logic here.");
                break;
            case GAME_LOSE:
                System.out.println("Game lost! Handle any lose logic here.");
                break;
            case PLAYER_ATTACK_RESULT:
                System.out.println("Processing player attack result.");
                break;
            default:
                // No special handling needed for other states
                break;
        }
    }

    private void processNextModifier() {
        Platform.runLater(() -> {
            if (!modifierStack.isEmpty()) {
                currentModifier = modifierStack.pop(); // Pop from the stack (LIFO behavior)
                currentModifier.initialize(this);
            } else {
                currentModifier = null;
            }
        });
    }

    public void notifyModifierComplete(BasePaneModifier modifier) {
        if (modifier == currentModifier || currentModifier == null) {
            currentModifier = null;
            determineNextState(modifier);
            processNextModifier();
        }
    }

    private void determineNextState(BasePaneModifier completedModifier) {
        BattlePaneState currentState = context.getCurrentState();

        switch (currentState) {
            case ENEMY_DESCRIPTION -> transitionToState(BattlePaneState.PLAYER_BEFORE_ATTACK_ITEM_SELECTION);
            case PLAYER_BEFORE_ATTACK_ITEM_SELECTION -> transitionToState(BattlePaneState.PLAYER_ATTACK);
            case PLAYER_ATTACK -> transitionToState(BattlePaneState.PLAYER_ATTACK_RESULT);
            case PLAYER_ATTACK_RESULT -> {
                if (context.getEnemy().getHp().isDead()) {
                    transitionToState(BattlePaneState.GAME_WIN);
                } else {
                    transitionToState(BattlePaneState.ENEMY_BEFORE_ATTACK);
                }
            }
            case ENEMY_BEFORE_ATTACK -> transitionToState(BattlePaneState.PLAYER_BEFORE_DEFENSE_ITEM_SELECTION);
            case PLAYER_BEFORE_DEFENSE_ITEM_SELECTION -> transitionToState(BattlePaneState.PLAYER_DEFENSE);
            case PLAYER_DEFENSE -> transitionToState(BattlePaneState.PLAYER_DEFENSE_RESULT);
            case PLAYER_DEFENSE_RESULT -> {
                if (GameLogic.getInstance().getSelectedCharacter().getHp().isDead()) {
                    transitionToState(BattlePaneState.GAME_LOSE);
                } else {
                    context.incrementTurn();
                    transitionToState(BattlePaneState.ENEMY_DESCRIPTION);
                }
            }
        }
    }

    public BattlePaneStateContext getContext() {
        return context;
    }
}
