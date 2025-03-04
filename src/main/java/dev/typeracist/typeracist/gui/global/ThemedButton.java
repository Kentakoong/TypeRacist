package dev.typeracist.typeracist.gui.global;

import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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

    // Fluent color setters
    public ThemedButton setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
        this.hoverColor = baseColor.darker();
        this.pressedColor = baseColor.brighter();
        updateButtonColors();
        return this;
    }

    public ThemedButton setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
        updateButtonColors();
        return this;
    }

    public ThemedButton setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
        return this;
    }

    public ThemedButton setTextColor(Color textColor) {
        this.textColor = textColor;
        setTextFill(textColor);
        return this;
    }

    // Update button appearance
    private void updateButtonColors() {
        BackgroundFill backgroundFill = new BackgroundFill(
                baseColor,
                new CornerRadii(10),
                Insets.EMPTY
        );
        setBackground(new Background(backgroundFill));
        setTextFill(textColor);
    }

    private void initialize() {
        // Basic styling
        setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 14));

        // Padding
        setPadding(new Insets(10, 15, 10, 15));

        // Add subtle drop shadow
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.DARKGRAY);
        shadow.setRadius(5);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        setEffect(shadow);

        updateButtonColors();
        // Hover effects
        setOnMouseEntered(e -> {
            setBackground(new Background(new BackgroundFill(
                    hoverColor,
                    new CornerRadii(10),
                    Insets.EMPTY
            )));
        });

        setOnMouseExited(e -> {
            setBackground(new Background(new BackgroundFill(
                    baseColor,
                    new CornerRadii(10),
                    Insets.EMPTY
            )));
        });

        // Click/pressed effect
        setOnMousePressed(e -> {
            setBackground(new Background(new BackgroundFill(
                    pressedColor,
                    new CornerRadii(10),
                    Insets.EMPTY
            )));
        });

        setOnMouseReleased(e -> {
            setBackground(new Background(new BackgroundFill(
                    baseColor,
                    new CornerRadii(10),
                    Insets.EMPTY
            )));
        });
    }
}