package dev.typeracist.typeracist.gui.gameScene.BattlePane.InformationPane;

import dev.typeracist.typeracist.gui.gameScene.TimedTypingPane;
import dev.typeracist.typeracist.logic.gameScene.TypedWordStatus;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.DatasetName;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class TypingInfoPaneModifier extends BaseInfoPaneModifier {
    private TimedTypingPane typingPane;
    private Label progressLabel;

    public TypingInfoPaneModifier(InformationPane subPaneNavigator) {
        super(subPaneNavigator);
    }

    @Override
    protected void initialize() {
        setPadding(new Insets(8, 20, 8, 20));

        typingPane = createTypingPane();
        progressLabel = createProgressLabel();

        getChildren().addAll(progressLabel, typingPane);

        Platform.runLater(typingPane::requestFocus);
    }

    public TimedTypingPane getTypingPane() {
        return typingPane;
    }

    public Label getProgressLabel() {
        return progressLabel;
    }

    private TimedTypingPane createTypingPane() {
        TimedTypingPane pane = new TimedTypingPane(
                GameLogic.getInstance().getDatasetManager()
                        .getDataSet(DatasetName.COMMON_WORDS_1K)
                        .getRandomWordsByScoreRange(4, 7, 50));

        pane.setHighlightColors(pane.getBaseColor(), Color.BLACK, Color.TOMATO, Color.DARKRED);
        pane.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 24));
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setSpacing(25);

        configureTypingPaneEvents(pane);

        return pane;
    }

    private void configureTypingPaneEvents(TimedTypingPane pane) {
        pane.setOnFirstType(event -> pane.start());

        pane.setOnType(event -> {
            updateProgressLabel();
            return event;
        });
    }

    private Label createProgressLabel() {
        Label label = new Label("0/" + typingPane.getTypingTracker().getWords().size());
        label.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 18));
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setMaxWidth(Double.MAX_VALUE);
        return label;
    }

    private void updateProgressLabel() {
        progressLabel.setText(countCompletedWords() + "/" + typingPane.getTypingTracker().getWords().size());
    }

    private long countCompletedWords() {
        return typingPane.getTypingTracker()
                .getTypedWordStatuses()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == TypedWordStatus.CORRECTED)
                .count();
    }
}
