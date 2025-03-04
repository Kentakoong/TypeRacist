package dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class TextInfoPaneModifier extends BaseInfoPaneModifier {


    public TextInfoPaneModifier(InformationPane informationPane) {
        super(informationPane);
    }

    @Override
    protected void initialize() {
        informationPane.setAlignment(Pos.CENTER);
        informationPane.setPadding(new Insets(20));
    }
}
