package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Entity;
import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.characters.SkillOnEntity;

public class CreeperExplosion extends Skill implements SkillOnEntity {
    private static final int EXPLOSION_DAMAGE = 39;

    public CreeperExplosion() {
        super("Creeper Explosion",
                "When defeated, deals 39 damage to the player.",
                SkillActivationOnState.ACTIVATION_AFTER_ATTACK);
    }

    @Override
    public Skill copy() {
        return new CreeperExplosion();
    }

    public int getExplosionDamage() {
        return EXPLOSION_DAMAGE;
    }

    @Override
    public void useSkill(Entity entity) {
        entity.damage(EXPLOSION_DAMAGE);
    }
}