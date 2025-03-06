package dev.typeracist.typeracist.gui.game.information.modifiers;

import dev.typeracist.typeracist.gui.game.TimedTypingPane;
import dev.typeracist.typeracist.gui.game.battle.BattlePane;
import dev.typeracist.typeracist.gui.game.information.InformationPane;
import dev.typeracist.typeracist.logic.game.typing.TypedWordStatus;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class TypingInfoPaneModifier extends BaseInfoPaneModifier {
    private final BattlePane battlePane;
    private TimedTypingPane typingPane;
    private Label progressLabel;

    public TypingInfoPaneModifier(BattlePane battlePane, InformationPane informationPane) {
        super(informationPane);
        this.battlePane = battlePane;
    }

    @Override
    public void initialize() {
        informationPane.setPadding(new Insets(8, 20, 8, 20));

        typingPane = createTypingPane();
        progressLabel = createProgressLabel();

        informationPane.getChildren().addAll(progressLabel, typingPane);

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
                battlePane.getStateContext().extractDataset(),
                battlePane.getStateContext().getTypingMaxTime());

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

    public long countCompletedWords() {
        return (long) Math.floor((double) typingPane.getTypingTracker()
                .getTypedWordStatuses()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == TypedWordStatus.CORRECTED)
                .count() * battlePane.getStateContext().getCurrentTurnContext().getWordMultiplier());
    }
}
