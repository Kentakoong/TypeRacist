package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Skill;

public class CreeperExplosion extends Skill {
    private static final int EXPLOSION_DAMAGE = 10;

    public CreeperExplosion() {
        super("Creeper Explosion",
                "When defeated, deals 10 damage to the player.");
    }

    @Override
    public Skill copy() {
        return new CreeperExplosion();
    }

    @Override
    public void useSkill(BattlePane battlePane) {
        // Explosion logic will be handled on death
    }

    public int getExplosionDamage() {
        return EXPLOSION_DAMAGE;
    }
}