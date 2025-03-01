package dev.typeracist.typeracist.gui.gameScene.InformationPane;

import dev.typeracist.typeracist.gui.gameScene.TimedTypingPane;
import dev.typeracist.typeracist.logic.gameScene.TypedWordStatus;
import dev.typeracist.typeracist.logic.global.GameLogic;
import dev.typeracist.typeracist.utils.DatasetName;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TypingInfoPane extends BaseInfoPane {
    private TimedTypingPane typingPane;
    private Label progressLabel;

    public TypingInfoPane(InformationPane subPaneNavigator) {
        super(subPaneNavigator);

        initialize();
        Platform.runLater(typingPane::requestFocus);
    }

    @Override
    protected void initializeContent() {
        setPadding(new Insets(8, 20, 8, 20));

        typingPane = createTypingPane();
        progressLabel = createProgressLabel();

        getChildren().addAll(progressLabel, typingPane);
    }

    private TimedTypingPane createTypingPane() {
        TimedTypingPane pane = new TimedTypingPane(
                GameLogic.getInstance().getDatasetManager()
                        .getDataSet(DatasetName.COMMON_WORDS_1K)
                        .getRandomWordsByScoreRange(4, 7, 50)
        );

        pane.setHighlightColors(pane.getBaseColor(), Color.BLACK, Color.TOMATO, Color.DARKRED);
        pane.setFont(Font.font(baseFont.getName(), 24));
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

        pane.setOnStop(event -> {
            GameLogic.getInstance().getSceneManager().setToPreviousScene();
        });
    }

    private Label createProgressLabel() {
        Label label = new Label("0/" + typingPane.getTypingTracker().getWords().size());
        label.setFont(Font.font(baseFont.getName(), 18));
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
