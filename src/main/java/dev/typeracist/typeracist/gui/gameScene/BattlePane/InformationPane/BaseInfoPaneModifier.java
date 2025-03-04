package dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane;

public abstract class BaseInfoPaneModifier {
    final protected InformationPane informationPane;

    public BaseInfoPaneModifier(InformationPane informationPane) {
        this.informationPane = informationPane;
    }

    protected abstract void initialize();
}
