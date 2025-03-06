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

    public boolean gainXP(int amount) {
        this.xp += amount;
        checkLevelUp();

        return checkLevelUp();
    }

    private boolean checkLevelUp() {
        if (xp < expToLvlUp)
            return false;

        level++;
        xp -= expToLvlUp;
        expToLvlUp += 25;

        GameLogic.getInstance().getSelectedCharacter()
                .getHp().addMaxHp(5);
        GameLogic.getInstance().getSelectedCharacter()
                .getHp().setCurrentHP(GameLogic.getInstance().getSelectedCharacter().getHp().getMaxHP());
        GameLogic.getInstance().getSelectedCharacter()
                .addAtk(1);
        GameLogic.getInstance().getSelectedCharacter()
                .addDef(1);

        System.out.println(GameLogic.getInstance().getSelectedCharacter().getHp().getMaxHP());
        System.out.println(GameLogic.getInstance().getSelectedCharacter().getBaseAtk());
        System.out.println(GameLogic.getInstance().getSelectedCharacter().getBaseDef());

        return true;
    }

    public boolean canLevelUp() {
        return xp >= expToLvlUp;
    }
}
