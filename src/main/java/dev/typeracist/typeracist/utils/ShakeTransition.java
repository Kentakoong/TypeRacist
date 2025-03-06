package dev.typeracist.typeracist.utils;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Animate a shake effect on the given node
 * <p>
 * Port of Shake from Animate.css http://daneden.me/animate by Dan Eden
 * <p>
 * {@literal @}keyframes shake {
 * 0%, 100% {transform: translateX(0);}
 * 10%, 30%, 50%, 70%, 90% {transform: translateX(-10px);}
 * 20%, 40%, 60%, 80% {transform: translateX(10px);}
 * }
 *
 * @author Jasper Potts
 */

public class ShakeTransition {
    /**
     * Create new ShakeTransition
     *
     * @param node The node to affect
     */
    public ShakeTransition(final Node node) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), new KeyValue(node.translateXProperty(), 0, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(50), new KeyValue(node.translateXProperty(), -5, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(100), new KeyValue(node.translateXProperty(), 5, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(150), new KeyValue(node.translateXProperty(), -5, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), 5, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(250), new KeyValue(node.translateXProperty(), -5, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(300), new KeyValue(node.translateXProperty(), 5, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(350), new KeyValue(node.translateXProperty(), -5, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(400), new KeyValue(node.translateXProperty(), 5, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(450), new KeyValue(node.translateXProperty(), -5, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(500), new KeyValue(node.translateXProperty(), 0, Interpolator.EASE_BOTH))
        );
        timeline.setCycleCount(1);
        timeline.setDelay(Duration.seconds(0.2));
        timeline.play();
    }
}