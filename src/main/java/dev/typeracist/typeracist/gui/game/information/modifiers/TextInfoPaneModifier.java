package dev.typeracist.typeracist.gui.game.information.modifiers;

import dev.typeracist.typeracist.gui.game.information.InformationPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class TextInfoPaneModifier extends BaseInfoPaneModifier {

    public TextInfoPaneModifier(InformationPane informationPane) {
        super(informationPane);
    }

    @Override
    public void initialize() {
        informationPane.setAlignment(Pos.CENTER);
        informationPane.setPadding(new Insets(20));
    }
}
