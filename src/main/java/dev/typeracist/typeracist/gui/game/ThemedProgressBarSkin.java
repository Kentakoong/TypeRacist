package dev.typeracist.typeracist.gui.game;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ThemedProgressBarSkin extends SkinBase<ProgressBar> {
    private final Rectangle track;
    private final Rectangle bar;
    private final ProgressBar control;

    public ThemedProgressBarSkin(ProgressBar control, Color trackColor, Color barColor) {
        super(control);
        this.control = control;

        this.track = new Rectangle();
        this.track.setFill(trackColor);
        this.track.setStroke(trackColor);
        this.track.setStrokeWidth(3);

        this.bar = new Rectangle();
        this.bar.setFill(barColor);

        Pane container = new Pane(this.track, this.bar);
        getChildren().add(container);

        control.progressProperty().addListener((obs, oldVal, newVal) -> updateBar(newVal.doubleValue()));
        control.widthProperty().addListener((obs, oldVal, newVal) -> updateSize());
        control.heightProperty().addListener((obs, oldVal, newVal) -> updateSize());
        updateSize();
    }

    private void updateSize() {
        double width = control.getWidth();
        double height = control.getHeight();

        if (width > 0 && height > 0) {
            track.setWidth(width);
            track.setHeight(height);

            bar.setHeight(height);
            updateBar(control.getProgress());
        }
    }

    private void updateBar(double progress) {
        double width = control.getWidth() * Math.max(0, Math.min(progress, 1));
        bar.setWidth(width);
    }
}
