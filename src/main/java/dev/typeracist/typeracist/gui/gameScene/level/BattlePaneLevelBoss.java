package dev.typeracist.typeracist.gui.gameScene.level;

import dev.typeracist.typeracist.gui.gameScene.BattlePane.BattlePane;
import dev.typeracist.typeracist.logic.characters.Enemy;
import dev.typeracist.typeracist.logic.characters.enemies.Nattee115;
import dev.typeracist.typeracist.logic.gameScene.BattlePaneStateContext;
import dev.typeracist.typeracist.logic.gameScene.Dataset;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.DatasetName;
import dev.typeracist.typeracist.utils.DatasetWordsExtractor;

import java.util.List;

public class BattlePaneLevelBoss extends BattlePane {
    public BattlePaneLevelBoss() {
        super(initializeContext());
    }

    public static BattlePaneStateContext initializeContext() {
        Enemy enemy = new Nattee115();
        Dataset dataset = GameLogic
                .getInstance()
                .getDatasetManager()
                .getDataSet(DatasetName.DATA_STRUCTURE_AND_ALGORITHM);
        DatasetWordsExtractor extractor = new DatasetWordsExtractor() {
            @Override
            public List<String> extractWord(Dataset dataset) {
                return dataset.getRandomParagraph();
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
