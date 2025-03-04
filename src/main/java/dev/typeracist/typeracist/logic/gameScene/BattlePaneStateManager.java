package dev.typeracist.typeracist.logic.gameScene;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier.*;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurnState;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.logic.inventory.item.WhirlwindDagger;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattlePaneStateManager {
    private final BattlePane battlePane;
    private final BattlePaneStateContext context;
    private final Map<BattlePaneState, BasePaneModifier> stateModifiers = new HashMap<>();
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
            currentModifier = modifier;
            processCurrentModifier();
        }
    }

    private void handleSpecialStateTransition(BattlePaneState newState) {
        switch (newState) {
            case GAME_WIN:
                int droppedCoin = context.getEnemy().getDropCoin();
                int droppedEXP = context.getEnemy().getDropXP();

                GameLogic.getInstance().getSelectedCharacter().gainCoin(droppedCoin);
                if (GameLogic.getInstance().getSelectedCharacter().getXp().gainXP(droppedEXP)) {
                    GameLogic.getInstance().getSceneManager().showBreadcrumb(
                            "Level Up",
                            "level " + GameLogic.getInstance().getSelectedCharacter().getXp().getLevel(),
                            5000
                    );
                } else {
                    GameLogic.getInstance().getSceneManager().showBreadcrumb(
                            "Gain " + droppedEXP + " XP and " + droppedCoin + " Coins",
                            "XP: " + GameLogic.getInstance().getSelectedCharacter().getXp().getXp() + " / " + GameLogic.getInstance().getSelectedCharacter().getXp().getExpToLvlUp() + ", Coins: " + GameLogic.getInstance().getSelectedCharacter().getCoin(),
                            5000
                    );
                }

                EventHandler<? super KeyEvent> keyPressEvent = battlePane.getOnKeyPressed();

                battlePane.setOnKeyPressed(keyEvent -> {
                    battlePane.setOnKeyPressed(keyPressEvent);
                    GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP);
                });

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

    private void processCurrentModifier() {
        Platform.runLater(() -> {
            if (currentModifier != null) {
                currentModifier.initialize(this);
            }
        });
    }

    public void notifyModifierComplete(BasePaneModifier modifier) {
        if (modifier == currentModifier) {
            currentModifier = null;
            determineNextState(modifier);
        }
    }

    private void determineNextState(BasePaneModifier completedModifier) {
        BattlePaneState currentState = context.getCurrentState();

        switch (currentState) {
            case ENEMY_DESCRIPTION -> transitionToState(BattlePaneState.PLAYER_BEFORE_ATTACK_ITEM_SELECTION);
            case PLAYER_BEFORE_ATTACK_ITEM_SELECTION -> {
                transitionToState(BattlePaneState.PLAYER_ATTACK);
                applyItem(ActivateOnTurnState.BEFORE_ATTACK);
            }
            case PLAYER_ATTACK -> {
                transitionToState(BattlePaneState.PLAYER_ATTACK_RESULT);
            }
            case PLAYER_ATTACK_RESULT -> {
                if (context.getEnemy().getHp().isDead()) {
                    transitionToState(BattlePaneState.GAME_WIN);
                } else {
                    transitionToState(BattlePaneState.ENEMY_BEFORE_ATTACK);
                }
            }
            case ENEMY_BEFORE_ATTACK -> {
                transitionToState(BattlePaneState.PLAYER_BEFORE_DEFENSE_ITEM_SELECTION);
            }
            case PLAYER_BEFORE_DEFENSE_ITEM_SELECTION -> {
                transitionToState(BattlePaneState.PLAYER_DEFENSE);

                applyItem(ActivateOnTurnState.BEFORE_DEFENSE);
                for (Item item : context.getCurrentTurnContext().getItemsUsed()) {
                    if (item instanceof WhirlwindDagger) {
                        GameLogic.getInstance().getSceneManager().showBreadcrumb(
                                "WhirlwindDagger passive is activated",
                                context.getEnemy().getName() + " is stunned for 1 turn.",
                                3000
                        );
                        transitionToState(BattlePaneState.PLAYER_DEFENSE_RESULT);
                    }
                }
            }
            case PLAYER_DEFENSE -> {
                transitionToState(BattlePaneState.PLAYER_DEFENSE_RESULT);
            }
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

    private void applyItem(ActivateOnTurnState currentState) {
        List<Item> itemsUsed = new ArrayList<>(context.getCurrentTurnContext().getItemsUsed());
        context.getCurrentTurnContext().getItemsUsed().clear();

        for (Item item : itemsUsed) {
            if (((ActivateOnTurn) item).getActivateOnTurnState() == currentState || ((ActivateOnTurn) item).getActivateOnTurnState() == ActivateOnTurnState.BOTH) {
                ((ActivateOnTurn) item).activate(battlePane);
            } else {
                context.getCurrentTurnContext().getItemsUsed().add(item);
            }
        }

        battlePane.updateHealthBars();
    }

    public BattlePaneStateContext getContext() {
        return context;
    }
}