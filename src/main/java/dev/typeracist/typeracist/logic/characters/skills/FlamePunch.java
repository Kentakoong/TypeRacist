package dev.typeracist.typeracist.logic.characters.skills;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Skill;
import java.util.Random;

public class FlamePunch extends Skill {
    private static final int MIN_BURN_DAMAGE = 2;
    private static final int MAX_BURN_DAMAGE = 3;
    private final Random random;

    public FlamePunch() {
        super("Flame Punch",
                "Inflict player with fire effect, deal 2-3 damages to player every turn.");
        this.random = new Random();
    }

    @Override
    public Skill copy() {
        return new FlamePunch();
    }

    @Override
    public void useSkill(BattlePane battlePane) {
        // Fire effect logic will be handled in battle
    }

    public int getBurnDamage() {
        return random.nextInt(MAX_BURN_DAMAGE - MIN_BURN_DAMAGE + 1) + MIN_BURN_DAMAGE;
    }
}