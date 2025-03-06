package dev.typeracist.typeracist.gui.game.level;

import dev.typeracist.typeracist.gui.game.battle.BattlePane;
import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.enemies.CursedSorcerer;
import dev.typeracist.typeracist.logic.game.battle.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.game.dataset.Dataset;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.DatasetName;
import dev.typeracist.typeracist.utils.DatasetWordsExtractor;

import java.util.List;

public class BattlePaneLevel7 extends BattlePane {
    public BattlePaneLevel7() {
        super(initializeContext());
    }

    public static BattlePaneStateContext initializeContext() {
        Enemy enemy = new CursedSorcerer();
        Dataset dataset = GameLogic
                .getInstance()
                .getDatasetManager()
                .getDataSet(DatasetName.POPULAR_BOOKS);
        DatasetWordsExtractor extractor = new DatasetWordsExtractor() {
            @Override
            public List<String> extractWord(Dataset dataset) {
                return dataset.getRandomWordsByScoreRange(5, 7.5, 50);
            }
        };

        return new BattlePaneStateContext(
                enemy,
                10 * 1000,
                dataset,
                extractor
        );
    }
}
