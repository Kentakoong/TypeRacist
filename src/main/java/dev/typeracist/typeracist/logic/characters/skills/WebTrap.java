package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Skill;

public class WebTrap extends Skill {
    public WebTrap() {
        super("Web Trap",
                "Player cannot use items for one turn.");
    }

    @Override
    public Skill copy() {
        return new WebTrap();
    }

    @Override
    public void useSkill(BattlePane battlePane) {
        // Web trap effect will be handled in battle logic
    }
}