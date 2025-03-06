package dev.typeracist.typeracist.logic.gameScene;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane.InfoPaneModifierType;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier.*;
import dev.typeracist.typeracist.logic.characters.*;
import dev.typeracist.typeracist.logic.characters.skills.SkillWithProbability;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.logic.global.SaveManager;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurnState;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import dev.typeracist.typeracist.utils.TurnOwnership;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BattlePaneStateManager {
    private final BattlePane battlePane;
    private final BattlePaneStateContext context;
    private final Map<BattlePaneState, BasePaneModifier> stateModifiers = new HashMap<>();
    private final int id;
    private BasePaneModifier currentModifier;

    public BattlePaneStateManager(BattlePane battlePane, BattlePaneStateContext context, int id) {
        this.battlePane = battlePane;
        this.context = context;
        this.id = id;
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
        GameLogic.getInstance().getSceneManager().closePopUp();

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
            case GAME_WIN -> {
                GameLogic.getInstance().getSelectedCharacter().resetBonuses();

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

                GameLogic.getInstance().clearBattle("BATTLE" + (id + 1));
                SaveManager.saveCharacter();

                EventHandler<? super KeyEvent> keyPressEvent = battlePane.getOnKeyPressed();

                battlePane.setOnKeyPressed(keyEvent -> {
                    battlePane.setOnKeyPressed(keyPressEvent);
                    GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP);
                });
            }
            case GAME_LOSE -> {
                GameLogic.getInstance().getSelectedCharacter().resetBonuses();

                battlePane.getInformationPane().setToPane(InfoPaneModifierType.TEXT);
                battlePane.getInformationPane().getChildren().clear();

                VBox vBox = new VBox(20);

                Label GameOverLabel = new Label("You Lose!");
                GameOverLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 20));

                Label GameOverDescriptionLabel = new Label("Try again next time!");
                GameOverDescriptionLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));

                vBox.getChildren().addAll(GameOverLabel, GameOverDescriptionLabel);

                Label pressAnyKeyToContinue = new Label("<< Press any key to continue.. >> ");
                pressAnyKeyToContinue.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));
                pressAnyKeyToContinue.setTextFill(Color.DARKGRAY);
                pressAnyKeyToContinue.setAlignment(Pos.CENTER);
                pressAnyKeyToContinue.setMaxWidth(Double.MAX_VALUE);
                pressAnyKeyToContinue.setVisible(false);

                new Timeline(
                        new KeyFrame(
                                Duration.millis(500),
                                ae -> {
                                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), pressAnyKeyToContinue);
                                    fadeIn.setFromValue(0);
                                    fadeIn.setToValue(1);
                                    fadeIn.setOnFinished(event -> {
                                        EventHandler<? super KeyEvent> keyPressEvent = battlePane.getOnKeyPressed();

                                        battlePane.setOnKeyPressed(keyEvent -> {
                                            battlePane.setOnKeyPressed(keyPressEvent); // Restore previous state
                                            GameLogic.getInstance().getSceneManager().setScene(SceneName.MAP);
                                        });

                                    });

                                    pressAnyKeyToContinue.setVisible(true);
                                    fadeIn.play();
                                }))
                        .play();

            }
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
            case ENEMY_DESCRIPTION -> {
                activateSkill(SkillActivationOnState.ACTIVATION_BEFORE_ATTACK, TurnOwnership.PLAYER);
                activateSkill(SkillActivationOnState.ACTIVATION_BEFORE_DEFENSE, TurnOwnership.ENEMY);
                activateSkill(SkillActivationOnState.ACTIVATION_BEFORE_ITEM, TurnOwnership.PLAYER);
                activateSkill(SkillActivationOnState.ACTIVATION_BEFORE_ITEM, TurnOwnership.ENEMY);

                transitionToState(BattlePaneState.PLAYER_BEFORE_ATTACK_ITEM_SELECTION);
            }
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
                activateSkill(SkillActivationOnState.ACTIVATION_BEFORE_ATTACK, TurnOwnership.ENEMY);
                activateSkill(SkillActivationOnState.ACTIVATION_BEFORE_DEFENSE, TurnOwnership.PLAYER);
                activateSkill(SkillActivationOnState.ACTIVATION_BEFORE_ITEM, TurnOwnership.PLAYER);
                activateSkill(SkillActivationOnState.ACTIVATION_BEFORE_ITEM, TurnOwnership.ENEMY);
                transitionToState(BattlePaneState.PLAYER_BEFORE_DEFENSE_ITEM_SELECTION);
            }
            case PLAYER_BEFORE_DEFENSE_ITEM_SELECTION -> {
                transitionToState(BattlePaneState.PLAYER_DEFENSE);
                applyItem(ActivateOnTurnState.BEFORE_DEFENSE);
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

    public void activateSkill(SkillActivationOnState skillActivationOnState, TurnOwnership turnOwnership) {
        Entity owner = switch (turnOwnership) {
            case PLAYER -> GameLogic.getInstance().getSelectedCharacter();
            case ENEMY -> context.getEnemy();
        };

        Entity target = switch (turnOwnership) {
            case PLAYER -> context.getEnemy();
            case ENEMY -> GameLogic.getInstance().getSelectedCharacter();
        };

        String ownerName = owner.getName();
        String targetName = target.getName();

        if (turnOwnership == TurnOwnership.PLAYER) {
            ownerName = GameLogic.getInstance().getSelectedCharacter().getName();
        } else {
            targetName = GameLogic.getInstance().getSelectedCharacter().getName();
        }

        Skill ownerSkill = owner.getSkill();

        if (ownerSkill == null) {
            return;
        }

        if (ownerSkill.isOnCooldown()) {

            if (ownerSkill.getActivationOnState() == skillActivationOnState) {
                ownerSkill.tickCooldown();
            }

            return;
        }

        if (ownerSkill instanceof SkillWithProbability && !((SkillWithProbability) ownerSkill).isProbability()) {
            return;
        }

        if (ownerSkill.getActivationOnState() != skillActivationOnState) {
            return;
        }

        if (ownerSkill instanceof SkillOnEntity) {
            ((SkillOnEntity) ownerSkill).useSkill(target);
            GameLogic.getInstance().getSceneManager().showBreadcrumb(
                    ownerName + " use " + ownerSkill.getName() + " on " + targetName,
                    ownerSkill.getDescription(),
                    2000
            );
            ownerSkill.resetCooldown();
        }

        if (ownerSkill instanceof SkillOnEnvironment) {
            ((SkillOnEnvironment) ownerSkill).useSkill(this);
            GameLogic.getInstance().getSceneManager().showBreadcrumb(
                    ownerName + " use " + ownerSkill.getName(),
                    ownerSkill.getDescription(),
                    2000
            );
            ownerSkill.resetCooldown();
        }

    }

    private void applyItem(ActivateOnTurnState currentState) {
        List<Item> itemsUsed = new ArrayList<>(context.getCurrentTurnContext().getItemsUsed());
        context.getCurrentTurnContext().getItemsUsed().clear();

        for (Item item : itemsUsed) {
            if (((ActivateOnTurn) item).getActivateOnTurnState() == currentState || ((ActivateOnTurn) item).getActivateOnTurnState() == ActivateOnTurnState.BOTH) {
                ((ActivateOnTurn) item).activate(this);
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