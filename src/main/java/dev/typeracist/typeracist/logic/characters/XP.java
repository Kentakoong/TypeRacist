package dev.typeracist.typeracist.logic.characters;

import dev.typeracist.typeracist.logic.global.GameLogic;

public class XP {
    private int xp;
    private int level;
    private int expToLvlUp;

    public XP() {
        this.xp = 0;
        this.level = 0;
        this.expToLvlUp = 25;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExpToLvlUp() {
        return expToLvlUp;
    }

    public void setExpToLvlUp(int expToLvlUp) {
        this.expToLvlUp = expToLvlUp;
    }

    public void gainXP(int amount) {
        this.xp += amount;
        checkLevelUp();
    }

    private void checkLevelUp() {
        if (xp < expToLvlUp)
            return;

        level++;
        xp -= expToLvlUp;
        expToLvlUp += 25;

        GameLogic.getInstance().getSelectedCharacter()
                .getHp().addMaxHp(5);
        GameLogic.getInstance().getSelectedCharacter()
                .addAtk(2);
        GameLogic.getInstance().getSelectedCharacter()
                .addDef(2);
    }

    public boolean canLevelUp() {
        return xp >= expToLvlUp;
    }
}
