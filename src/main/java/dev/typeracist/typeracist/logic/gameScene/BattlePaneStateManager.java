package dev.typeracist.typeracist.logic.gameScene;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.gui.gameScene.BattlePane.PaneModifier.*;
import dev.typeracist.typeracist.logic.characters.*;
import dev.typeracist.typeracist.logic.characters.skills.SkillWithProbability;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.SaveManager;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.ActivateOnTurnState;
import dev.typeracist.typeracist.logic.inventory.Item;
import dev.typeracist.typeracist.utils.TurnOwnership;
import javafx.application.Platform;

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
        stateModifiers.put(BattlePaneState.PLAYER_BEFORE_ATTACK_ITEM_SELECTION,
                new PlayerItemSelectionPaneModifier(battlePane, context));
        stateModifiers.put(BattlePaneState.PLAYER_BEFORE_DEFENSE_ITEM_SELECTION,
                new PlayerItemSelectionPaneModifier(battlePane, context));
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
                            5000);
                } else {
                    GameLogic.getInstance().getSceneManager().showBreadcrumb(
                            "Gain " + droppedEXP + " XP and " + droppedCoin + " Coins",
                            "XP: " + GameLogic.getInstance().getSelectedCharacter().getXp().getXp() + " / "
                                    + GameLogic.getInstance().getSelectedCharacter().getXp().getExpToLvlUp()
                                    + ", Coins: " + GameLogic.getInstance().getSelectedCharacter().getCoin(),
                            5000);
                }

                if (id + 1 == 10) {
                    GameLogic.getInstance().clearBattle("BOSS");
                } else {
                    GameLogic.getInstance().clearBattle("BATTLE" + (id + 1));
                }

                SaveManager.saveCharacter();
            }
            case GAME_LOSE -> {
                GameLogic.getInstance().getSelectedCharacter().resetBonuses();
                GameLogic.getInstance().getSelectedCharacter().getHp().setCurrentHP(35);
                SaveManager.saveCharacter();
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

        if (context.getEnemy().getHp().isDead()) {
            transitionToState(BattlePaneState.GAME_WIN);
            return;
        }

        if (GameLogic.getInstance().getSelectedCharacter().getHp().isDead()) {
            transitionToState(BattlePaneState.GAME_LOSE);
            return;
        }

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
                transitionToState(BattlePaneState.ENEMY_BEFORE_ATTACK);
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
                context.incrementTurn();
                transitionToState(BattlePaneState.ENEMY_DESCRIPTION);
            }
        }

        if (context.getEnemy().getHp().isDead()) {
            transitionToState(BattlePaneState.GAME_WIN);
            return;
        }

        if (GameLogic.getInstance().getSelectedCharacter().getHp().isDead()) {
            transitionToState(BattlePaneState.GAME_LOSE);
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
                    2000);
            ownerSkill.resetCooldown();
        }

        if (ownerSkill instanceof SkillOnEnvironment) {
            ((SkillOnEnvironment) ownerSkill).useSkill(this);
            GameLogic.getInstance().getSceneManager().showBreadcrumb(
                    ownerName + " use " + ownerSkill.getName(),
                    ownerSkill.getDescription(),
                    2000);
            ownerSkill.resetCooldown();
        }

    }

    private void applyItem(ActivateOnTurnState currentState) {
        List<Item> itemsUsed = new ArrayList<>(context.getCurrentTurnContext().getItemsUsed());
        context.getCurrentTurnContext().getItemsUsed().clear();

        for (Item item : itemsUsed) {
            if (((ActivateOnTurn) item).getActivateOnTurnState() == currentState
                    || ((ActivateOnTurn) item).getActivateOnTurnState() == ActivateOnTurnState.BOTH) {
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