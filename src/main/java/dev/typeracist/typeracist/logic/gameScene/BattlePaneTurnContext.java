package dev.typeracist.typeracist.logic.gameScene;

public class BattlePaneTurnContext {
    private int rawAttackScore;
    private int rawDefenseScore;
    private int damageTaken;
    private int attackDamage;
    private boolean hadAttack;
    private boolean hadDefense;

    public BattlePaneTurnContext() {
        rawAttackScore = -1;
        rawDefenseScore = -1;
        damageTaken = -1;
        attackDamage = -1;
        hadAttack = false;
        hadDefense = false;
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

}
