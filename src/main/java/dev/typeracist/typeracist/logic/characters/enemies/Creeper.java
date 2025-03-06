package dev.typeracist.typeracist.logic.characters.enemies;

import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.characters.skills.CreeperExplosion;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.RandomRange;
import dev.typeracist.typeracist.utils.ResourceName;

public class Creeper extends Enemy {
    private static final String[] DESCRIPTIONS = {
            "YOOO, a Creeper!",
            "HSSSS SHHSHSSH",
            "Is it blowing up?"
    };
    private static final RandomRange COIN_RANGE = new RandomRange(30, 100);
    private static final RandomRange XP_RANGE = new RandomRange(150, 200);

    public Creeper() {
        super(new HP(100), 50, 25,
                ResourceManager.getImage(ResourceName.IMAGE_ENEMY_CREEPER),
                DESCRIPTIONS,
                new CreeperExplosion(),
                COIN_RANGE,
                XP_RANGE);
    }

    @Override
    public int damage(int amount) {
        int damage = super.damage(amount);
        if (hp.isDead()) {
            ((CreeperExplosion) skill).useSkill(GameLogic.getInstance().getSelectedCharacter());
            GameLogic.getInstance().getSceneManager().showBreadcrumb(
                    "Ahh mann.. dfjasdjf aiojEXPLOSONdinfsBOmm!!!",
                    "Creeper exploded dealt a amazing " + new CreeperExplosion().getExplosionDamage() + " damage!",
                    3000
            );
        }

        return damage;
    }
}