package dev.typeracist.typeracist.gui.gameScene;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.function.Consumer;

public class TimedTypingPane extends TypingPane {
    private final CountdownProgressBar countdownProgressBar;
    private final double totalTime;
    private final StackPane progressBarContainer;
    private double spacing;
    private Consumer<Void> onStopCallback;

    public TimedTypingPane(List<String> words, long totalTime) {
        super(words);
        this.totalTime = totalTime;

        countdownProgressBar = new CountdownProgressBar(totalTime);
        countdownProgressBar.prefWidthProperty().bind(this.widthProperty());

        this.progressBarContainer = new StackPane();
        this.progressBarContainer.getChildren().add(countdownProgressBar);

        getChildren().add(this.progressBarContainer);
    }

    public TimedTypingPane(List<String> words) {
        this(words, CountdownProgressBar.DEFAULT_TOTAL_TIME);
    }

    public void start() {
        getTypingTracker().start();

        new Thread(() -> {
            while (getTypingTracker().isRunning()) {
                if (getTypingTracker().getElapsedTime() > totalTime) {
                    stop();
                }

                try {
                    Platform.runLater(() -> countdownProgressBar.updateProgress(getTypingTracker().getElapsedTime()));
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void stop() {
        getTypingTracker().stop();
        firstTypeHandled = false;

        // make the key invalid, so it doesn't register as a printable character
        // and not added to the typing pane

        setOnType(event -> new KeyEvent(
                event.getEventType(),
                "",
                "",
                KeyCode.F24,
                true,
                true,
                true,
                true));

        // Call the onStop callback, if it's set
        if (onStopCallback != null) {
            onStopCallback.accept(null);
        }
    }

    public double getSpacing() {
        return spacing;
    }

    public void setSpacing(double spacing) {
        this.spacing = spacing;
        this.progressBarContainer.setPadding(new Insets(spacing, 0, 0, 0));
    }

    // Set the callback to be invoked when the typing session stops
    public void setOnStop(Consumer<Void> onStopCallback) {
        this.onStopCallback = onStopCallback;
    }
}
