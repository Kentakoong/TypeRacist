package dev.typeracist.typeracist.gui.gameScene.level;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.enemies.GoblinTypist;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.Dataset;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.DatasetName;
import dev.typeracist.typeracist.utils.DatasetWordsExtractor;

import java.util.List;

public class BattlePaneLevel2 extends BattlePane {
    public BattlePaneLevel2() {
        super(initializeContext());
    }

    public static BattlePaneStateContext initializeContext() {
        Enemy enemy = new GoblinTypist();
        Dataset dataset = GameLogic
                .getInstance()
                .getDatasetManager()
                .getDataSet(DatasetName.COMMON_WORDS_1K);
        DatasetWordsExtractor extractor = new DatasetWordsExtractor() {
            @Override
            public List<String> extractWord(Dataset dataset) {
                return dataset.getRandomWordsByScoreRange(2, 4, 50);
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
