package dev.typeracist.typeracist.gui.game.information.modifiers;

import dev.typeracist.typeracist.gui.game.information.InformationPane;

public abstract class BaseInfoPaneModifier {
    final protected InformationPane informationPane;

    public BaseInfoPaneModifier(InformationPane informationPane) {
        this.informationPane = informationPane;
    }

    public abstract void initialize();
}
