package dev.typeracist.typeracist.gui.game;

import java.util.List;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;

public class TimedTypingPane extends TypingPane {
    private final CountdownProgressBar countdownProgressBar;
    private final StackPane progressBarContainer;
    private double totalTime;
    private double spacing;
    private Consumer<Void> onStopCallback;
    private Thread timerThread;

    public TimedTypingPane(List<String> words, long totalTime) {
        super(words);
        this.totalTime = totalTime;

        countdownProgressBar = new CountdownProgressBar(totalTime);
        countdownProgressBar.prefWidthProperty().bind(this.widthProperty());

        this.progressBarContainer = new StackPane();
        this.progressBarContainer.getChildren().add(countdownProgressBar);

        getChildren().add(this.progressBarContainer);

        // Ensure focus is requested when clicked
        setOnMouseClicked(event -> requestFocus());

        // Ensure focus is maintained
        focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                Platform.runLater(this::requestFocus);
            }
        });
    }

    public TimedTypingPane(List<String> words) {
        this(words, CountdownProgressBar.DEFAULT_TOTAL_TIME);
    }

    public void start() {
        if (timerThread != null && timerThread.isAlive()) {
            return; // Don't start if already running
        }

        getTypingTracker().start();
        requestFocus(); // Ensure focus when starting

        timerThread = new Thread(() -> {
            while (getTypingTracker().isRunning()) {
                if (getTypingTracker().getElapsedTime() > totalTime) {
                    Platform.runLater(this::stop);
                    break;
                }

                try {
                    Platform.runLater(() -> countdownProgressBar.updateProgress(getTypingTracker().getElapsedTime()));
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();
    }

    public void stop() {
        getTypingTracker().stop();
        firstTypeHandled = false;

        if (timerThread != null) {
            timerThread.interrupt();
            timerThread = null;
        }

        // Call the onStop callback, if it's set
        if (onStopCallback != null) {
            onStopCallback.accept(null);
        }
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
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
