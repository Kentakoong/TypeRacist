package dev.typeracist.typeracist.logic.gameScene;

public class BattlePaneTurnContext {
    private int rawAttackScore;
    private int rawDefenceScore;

    public BattlePaneTurnContext() {
        rawAttackScore = -1;
        rawDefenceScore = -1;
    }

    public int getRawDefenseScore() {
        return rawDefenceScore;
    }

    public void setRawDefenceScore(int rawDefenceScore) {
        this.rawDefenceScore = rawDefenceScore;
    }

    public int getRawAttackScore() {
        return rawAttackScore;
    }

    public void setRawAttackScore(int rawAttackScore) {
        this.rawAttackScore = rawAttackScore;
    }

}
