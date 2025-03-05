package dev.typeracist.typeracist.gui.gameScene.level;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.enemies.SkeletonScribe;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.Dataset;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.DatasetName;
import dev.typeracist.typeracist.utils.DatasetWordsExtractor;

import java.util.List;

public class BattlePaneLevel3 extends BattlePane {
    public BattlePaneLevel3() {
        super(initializeContext());
    }

    public static BattlePaneStateContext initializeContext() {
        Enemy enemy = new SkeletonScribe();
        Dataset dataset = new Dataset(
                GameLogic
                        .getInstance()
                        .getDatasetManager()
                        .getDataSet(DatasetName.POPULAR_BOOKS),
                true,
                true,
                true
        );
        DatasetWordsExtractor extractor = new DatasetWordsExtractor() {
            @Override
            public List<String> extractWord(Dataset dataset) {
                return dataset.getRandomParagraphByScoreRange(4, 5);
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
