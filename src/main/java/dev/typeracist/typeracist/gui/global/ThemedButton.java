package dev.typeracist.typeracist.gui.global;

import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class ThemedButton extends Button {
    private Color baseColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color textColor;

    // Default constructor
    public ThemedButton(String text) {
        super(text);
        setDefaultColors(Color.DARKGREY);
        initialize();
    }

    // Constructor with base color
    public ThemedButton(String text, Color baseColor) {
        super(text);
        setDefaultColors(baseColor);
        initialize();
    }

    // Set default color scheme
    private void setDefaultColors(Color base) {
        this.baseColor = base;
        this.hoverColor = base.darker();
        this.pressedColor = base;
        this.textColor = Color.WHITE;
    }

    // Fluent color setters with hex support
    public ThemedButton setBaseColor(Color baseColor) {
        return setBaseColor(String.format("#%02X%02X%02X",
                (int) (baseColor.getRed() * 255),
                (int) (baseColor.getGreen() * 255),
                (int) (baseColor.getBlue() * 255)));
    }

    public ThemedButton setBaseColor(String hexColor) {
        setStyle(
                getStyle().replaceAll("-fx-background-color: [^;]+;",
                        "-fx-background-color: " + hexColor + ";"));
        return this;
    }

    public ThemedButton setHoverColor(Color hoverColor) {
        return setHoverColor(String.format("#%02X%02X%02X",
                (int) (hoverColor.getRed() * 255),
                (int) (hoverColor.getGreen() * 255),
                (int) (hoverColor.getBlue() * 255)));
    }

    public ThemedButton setHoverColor(String hoverHexColor) {
        this.hoverColor = Color.web(hoverHexColor);
        return this;
    }

    public ThemedButton setPressedColor(Color pressedColor) {
        return setPressedColor(String.format("#%02X%02X%02X",
                (int) (pressedColor.getRed() * 255),
                (int) (pressedColor.getGreen() * 255),
                (int) (pressedColor.getBlue() * 255)));
    }

    public ThemedButton setPressedColor(String pressedHexColor) {
        this.pressedColor = Color.web(pressedHexColor);
        return this;
    }

    public ThemedButton setTextColor(Color textColor) {
        return setTextColor(String.format("#%02X%02X%02X",
                (int) (textColor.getRed() * 255),
                (int) (textColor.getGreen() * 255),
                (int) (textColor.getBlue() * 255)));
    }

    public ThemedButton setTextColor(String textHexColor) {
        setStyle(
                getStyle().replaceAll("-fx-text-fill: [^;]+;",
                        "-fx-text-fill: " + textHexColor + ";"));
        return this;
    }

    private void initialize() {
        // Basic styling from ShopScene's createStyledButton
        setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));
        setStyle(
                "-fx-background-color: #484848; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;");
        setMinHeight(35);

        // Padding
        setPadding(new Insets(10, 15, 10, 15));

        // Add subtle drop shadow
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#000000", 0.3));
        shadow.setRadius(5);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        setEffect(shadow);

        // Hover effects
        setOnMouseEntered(e -> {
            String hoverColorHex = hoverColor != null
                    ? String.format("#%02X%02X%02X",
                            (int) (hoverColor.getRed() * 255),
                            (int) (hoverColor.getGreen() * 255),
                            (int) (hoverColor.getBlue() * 255))
                    : "#606060";
            setStyle(
                    "-fx-background-color: " + hoverColorHex + "; " +
                            "-fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;");
        });

        setOnMouseExited(e -> setStyle(
                "-fx-background-color: #484848; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;"));

        // Click/pressed effect
        setOnMousePressed(e -> {
            String pressedColorHex = pressedColor != null
                    ? String.format("#%02X%02X%02X",
                            (int) (pressedColor.getRed() * 255),
                            (int) (pressedColor.getGreen() * 255),
                            (int) (pressedColor.getBlue() * 255))
                    : "#707070";
            setStyle(
                    "-fx-background-color: " + pressedColorHex + "; " +
                            "-fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;");
        });

        setOnMouseReleased(e -> setStyle(
                "-fx-background-color: #484848; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2;"));
    }

    // Getters for color properties
    public Color getBaseColor() {
        return baseColor;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public Color getPressedColor() {
        return pressedColor;
    }

    public Color getTextColor() {
        return textColor;
    }
}