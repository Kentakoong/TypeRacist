package dev.typeracist.typeracist.gui.gameScene.BattlePane;

import dev.typeracist.typeracist.gui.gameScene.ThemedProgressBarSkin;
import dev.typeracist.typeracist.logic.characters.HP;
import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class HealthBar extends StackPane {
    private final ProgressBar progressBar;
    private final Label hpLabel;
    private final HP hp;

    public HealthBar(HP hp, Color barColor) {
        this.hp = hp;

        progressBar = new ProgressBar(1.0);
        progressBar.setPrefHeight(12);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressBar.setSkin(new ThemedProgressBarSkin(progressBar, Color.web("#383838"), barColor));

        hpLabel = new Label();
        hpLabel.setTextFill(Color.WHITE);
        hpLabel.setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));

        getChildren().addAll(progressBar, hpLabel);
        updateHealthBar();
    }

    public void updateHealthBar() {
        double newProgress = (double) hp.getCurrentHP() / hp.getMaxHP();
        animateProgressBar(progressBar.getProgress(), newProgress);

        // Update label text
        hpLabel.setText(hp.getCurrentHP() + "/" + hp.getMaxHP());
    }

    private void animateProgressBar(double oldProgress, double newProgress) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), oldProgress)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(progressBar.progressProperty(), newProgress))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
}

