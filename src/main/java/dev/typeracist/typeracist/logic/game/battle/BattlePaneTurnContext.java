package dev.typeracist.typeracist.logic.game.battle;

import dev.typeracist.typeracist.logic.inventory.ActivateOnTurn;
import dev.typeracist.typeracist.logic.inventory.Item;

import java.util.ArrayList;
import java.util.List;

public class BattlePaneTurnContext {
    private final List<Item> itemsUsed;
    private int rawAttackScore;
    private int rawDefenseScore;
    private int damageTaken;
    private int attackDamage;
    private boolean hadAttack;
    private boolean hadDefense;
    private double wordMultiplier;

    private int playerAttackModifier;
    private int enemyAttackModifier;
    private boolean itemSelectDisable;

    public BattlePaneTurnContext() {
        itemsUsed = new ArrayList<>();
        rawAttackScore = 0;
        rawDefenseScore = 0;
        wordMultiplier = 1;
        damageTaken = -1;
        attackDamage = -1;
        playerAttackModifier = 0;
        enemyAttackModifier = 0;
        itemSelectDisable = false;
        hadAttack = false;
        hadDefense = false;
    }

    public boolean isItemSelectDisable() {
        return itemSelectDisable;
    }

    public void setItemSelectDisable(boolean itemSelectDisable) {
        this.itemSelectDisable = itemSelectDisable;
    }

    public int getEnemyAttackModifier() {
        return enemyAttackModifier;
    }

    public void setEnemyAttackModifier(int enemyAttackModifier) {
        this.enemyAttackModifier = enemyAttackModifier;
    }

    public void addEnemyAttackModifier(int enemyAttackModifier) {
        this.enemyAttackModifier += enemyAttackModifier;
    }

    public int getPlayerAttackModifier() {
        return playerAttackModifier;
    }

    public void setPlayerAttackModifier(int playerAttackModifier) {
        this.playerAttackModifier = playerAttackModifier;
    }

    public void addPlayerAttackModifier(int playerAttackModifier) {
        this.playerAttackModifier += playerAttackModifier;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.hadAttack = true;
        this.attackDamage = attackDamage;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(int damageTaken) {
        this.hadDefense = true;
        this.damageTaken = damageTaken;
    }

    public boolean isHadDefense() {
        return hadDefense;
    }

    public void setHadDefense(boolean hadDefense) {
        this.hadDefense = hadDefense;
    }

    public boolean isHadAttack() {
        return hadAttack;
    }

    public void setHadAttack(boolean hadAttack) {
        this.hadAttack = hadAttack;
    }

    public int getRawDefenseScore() {
        return rawDefenseScore;
    }

    public void setRawDefenseScore(int rawDefenseScore) {
        this.rawDefenseScore = rawDefenseScore;
    }

    public int getRawAttackScore() {
        return rawAttackScore;
    }

    public void setRawAttackScore(int rawAttackScore) {
        this.rawAttackScore = rawAttackScore;
    }

    public <T extends Item & ActivateOnTurn> void addItemUsed(T item) {
        itemsUsed.add(item);
    }

    public List<Item> getItemsUsed() {
        return itemsUsed;
    }

    public double getWordMultiplier() {
        return wordMultiplier;
    }

    public void setWordMultiplier(double wordMultiplier) {
        this.wordMultiplier = wordMultiplier;
    }
}
