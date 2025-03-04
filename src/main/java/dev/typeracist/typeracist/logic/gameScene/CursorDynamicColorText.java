package dev.typeracist.typeracist.logic.gameScene;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

// Don't adjust the spacing of this class in any way!,
// it will break the cursor position adjustment logic (that is not considering the spacing)

public class CursorDynamicColorText extends DynamicColorText {
    public static Color DEFAULT_CURSOR_COLOR = Color.BLACK;
    private static final Rectangle sharedCursor = new Rectangle(2, 15, DEFAULT_CURSOR_COLOR);
    private static CursorDynamicColorText currentOwner;

    private final Timeline blinkAnimation;
    private int cursorPosition;

    public CursorDynamicColorText(String text, Color baseColor) {
        super(text, baseColor);
        this.cursorPosition = 0;

        blinkAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> sharedCursor.setVisible(false)),
                new KeyFrame(Duration.seconds(1), e -> sharedCursor.setVisible(true)));
        blinkAnimation.setCycleCount(Timeline.INDEFINITE);
        blinkAnimation.play();

        if (currentOwner == null) {
            currentOwner = this;
        }
        setCursorPosition(cursorPosition);
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        if (cursorPosition > text.length()) {
            cursorPosition = text.length();
        }
    }

    private void updateCursorPosition() {
        getChildren().remove(sharedCursor);

        currentOwner = this;

        double x = 0;
        double baseline = 0;

        this.applyCss();
        this.layout();

        if (cursorPosition < getChildren().size()) {
            Node refNode = getChildren().get(cursorPosition);
            baseline = refNode.getBaselineOffset();
            x = refNode.getBoundsInParent().getMinX();
        }

        /*
         * if the cursor is at the end of the word, the first method will not work.
         * this method instead calculate the cursor position based on bounding box of
         * the last character.
         * (this method will incorrect shift in non monospace font)
         */

        else if (!getChildren().isEmpty()) {
            Node refNode = getChildren().getLast();
            baseline = refNode.getBaselineOffset();
            x = refNode.getBoundsInParent().getMinX() + refNode.getLayoutBounds().getWidth();
        }

        sharedCursor.setX(x);
        sharedCursor.setY(baseline - sharedCursor.getHeight());

        sharedCursor.setManaged(false);
        getChildren().add(sharedCursor);
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        sharedCursor.setHeight(font.getSize());
    }

    public int getCursorPosition() {
        return cursorPosition;
    }

    public void setCursorPosition(int position) {
        if (position < 0 || position > length()) {
            throw new IllegalArgumentException("Invalid cursor position");
        }
        this.cursorPosition = position;
        updateCursorPosition();
    }

    public void stopBlinking() {
        blinkAnimation.stop();
        sharedCursor.setVisible(true);
    }

    public void setCursorSize(double width, double height) {
        sharedCursor.setWidth(width);
        sharedCursor.setHeight(height);
    }

    public void setCursorColor(Color color) {
        sharedCursor.setFill(color);
    }
}
