package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.logic.characters.Skill;
import dev.typeracist.typeracist.logic.characters.SkillActivationOnState;
import dev.typeracist.typeracist.logic.characters.SkillOnEnvironment;
import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateManager;

public class Evasion extends SkillWithProbability implements SkillOnEnvironment {
  public Evasion() {
    super("Evasion",
        "40% chance to dodge the player's attack.",
        SkillActivationOnState.ACTIVATION_ON_DEFENSE,
        0.40);
  }

  @Override
  public Skill copy() {
    return new Evasion();
  }

  @Override
  public void useSkill(BattlePaneStateManager manager) {
    manager.getContext().getCurrentTurnContext().addPlayerAttackModifier(-(int) 1e9);
  }
}
