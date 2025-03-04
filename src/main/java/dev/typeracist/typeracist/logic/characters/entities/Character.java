package dev.typeracist.typeracist.logic.characters.entities;

import dev.typeracist.typeracist.logic.characters.Entity;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.XP;
import dev.typeracist.typeracist.logic.inventory.Inventory;
import javafx.scene.image.Image;

public abstract class Character extends Entity {
    protected static final HP BASE_HP = new HP(35);
    protected static final int BASE_ATK = 4;
    protected static final int BASE_DEF = 5;
    protected static final int XP_TO_LEVEL_UP = 25;
    protected final Inventory inventory;
    protected XP xp;
    protected int coin;

    public Character(HP hp, int atk, int def, Image image, Skill skill) {
        super(hp, atk, def, image, skill);
        this.xp = new XP();
        this.coin = 0;
        this.inventory = new Inventory();
    }

    public Character(int atk, int def, Image image, Skill skill) {
        this(BASE_HP, atk, def, image, skill);
    }

    public int attack(Entity target, int rawDamage) {
        int realDamage = Math.max(rawDamage * atk - target.getDef(), 0);
        target.getHp().damage(realDamage);

        return realDamage;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getCoin() {
        return coin;
    }

    public void gainCoin(int amount) {
        this.coin += amount;
    }

    public void spendCoin(int amount) {
        if (amount > coin) {
            throw new IllegalStateException("Not enough coins");
        }
        this.coin -= amount;
    }
}