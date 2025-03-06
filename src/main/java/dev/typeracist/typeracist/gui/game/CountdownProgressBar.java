package dev.typeracist.typeracist.gui.game;

import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;

public class CountdownProgressBar extends ProgressBar {
    public static long DEFAULT_TOTAL_TIME = 10 * 1000;
    private long totalTime;

    public CountdownProgressBar(long totalTime) {
        super(1.0);
        this.totalTime = totalTime;

        setPrefHeight(9);
        setMaxWidth(Double.MAX_VALUE);
        setSkin(new ThemedProgressBarSkin(this, Color.web("#484848"), Color.LIMEGREEN));
    }

    public void updateProgress(long elapsedTime) {
        double remainingTime = totalTime - elapsedTime;
        double progress = remainingTime / totalTime;
        setProgress(Math.max(0, progress));
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
}
