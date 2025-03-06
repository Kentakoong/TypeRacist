package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Entity;
import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.characters.SkillOnEntity;

public class SkillIssue extends Skill implements SkillOnEntity {
    public SkillIssue() {
        super(
                "Skill issue",
                "take half of your life span",
                SkillActivationOnState.ACTIVATION_ON_ATTACK,
                5);
    }

    @Override
    public Skill copy() {
        return new SkillIssue();
    }

    @Override
    public void useSkill(Entity entity) {
        entity.getHp().setCurrentHP(entity.getHp().getCurrentHP() / 2);
    }
}
