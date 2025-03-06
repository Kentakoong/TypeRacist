package dev.typeracist.typeracist.gui.global;

import dev.typeracist.typeracist.logic.global.ResourceManager;
import dev.typeracist.typeracist.utils.ResourceName;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class ThemedButton extends Button {
    private Color baseColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color textColor;
    private Color borderColor;
    private boolean isRadioButton = false;
    private boolean isSelected = false;
    private RadioButtonGroup radioButtonGroup = null;

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
        this.borderColor = Color.WHITE;
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

    public ThemedButton setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public ThemedButton setBorderColor(String borderHexColor) {
        this.borderColor = Color.web(borderHexColor);
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

    // Radio button support methods
    public ThemedButton setAsRadioButton(RadioButtonGroup group) {
        this.isRadioButton = true;
        this.radioButtonGroup = group;
        updateRadioButtonStyle();
        return this;
    }

    public ThemedButton setSelected(boolean selected) {
        if (isRadioButton) {
            if (selected) {
                // Deselect other buttons in the group
                if (radioButtonGroup != null) {
                    radioButtonGroup.deselectOthers(this);
                }
            }
            this.isSelected = selected;
            updateRadioButtonStyle();
        }
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    private void updateRadioButtonStyle() {
        if (isRadioButton) {
            if (isSelected) {
                setStyle(
                        "-fx-background-color: " + (hoverColor != null ? String.format("#%02X%02X%02X",
                                (int) (hoverColor.getRed() * 255),
                                (int) (hoverColor.getGreen() * 255),
                                (int) (hoverColor.getBlue() * 255)) : "#606060") + ";" +
                                "-fx-background-insets: 2;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 5;" +
                                "-fx-border-color: gold;" +
                                "-fx-border-width: 2px;" +
                                "-fx-border-radius: 5;");
            } else {
                setStyle(
                        "-fx-background-color: #484848;" +
                                "-fx-background-insets: 2;" +
                                "-fx-text-fill: white;" +
                                "-fx-border-color: white;" +
                                "-fx-border-width: 2px;" +
                                "-fx-background-radius: 5;" +
                                "-fx-border-radius: 5;");
            }
        }
    }

    // Radio Button Group support
    public static class RadioButtonGroup {
        private List<ThemedButton> buttons = new ArrayList<>();

        public void addButton(ThemedButton button) {
            buttons.add(button.setAsRadioButton(this));
        }

        public void deselectOthers(ThemedButton selectedButton) {
            for (ThemedButton button : buttons) {
                if (button != selectedButton) {
                    button.setSelected(false);
                }
            }
        }

        public ThemedButton getSelectedButton() {
            return buttons.stream()
                    .filter(ThemedButton::isSelected)
                    .findFirst()
                    .orElse(null);
        }

        public List<ThemedButton> getButtons() {
            return new ArrayList<>(buttons);
        }
    }

    private void initialize() {
        // Basic styling from ShopScene's createStyledButton
        setFont(ResourceManager.getFont(ResourceName.FONT_DEPARTURE_MONO, 16));

        // Consistent border styling with insets to prevent overflow
        Border defaultBorder = new Border(
                new BorderStroke(borderColor, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2)));

        setStyle(
                "-fx-background-color: #484848;" +
                        "-fx-background-insets: 2;" + // Create inset equal to border width
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 5;" + // Match border corner radius
                        "-fx-border-radius: 5;" + // Match background radius
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2px;");
        setBorder(defaultBorder);
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
            if (isRadioButton && isSelected) {
                return; // Don't change style if it's a selected radio button
            }
            String hoverColorHex = hoverColor != null
                    ? String.format("#%02X%02X%02X",
                            (int) (hoverColor.getRed() * 255),
                            (int) (hoverColor.getGreen() * 255),
                            (int) (hoverColor.getBlue() * 255))
                    : "#606060";
            setStyle(
                    "-fx-background-color: " + hoverColorHex + ";" +
                            "-fx-background-insets: 2;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 5;" +
                            "-fx-border-radius: 5;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 2px;");
            setBorder(new Border(
                    new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
        });

        setOnMouseExited(e -> {
            if (isRadioButton && isSelected) {
                return; // Don't change style if it's a selected radio button
            }
            setStyle(
                    "-fx-background-color: #484848;" +
                            "-fx-background-insets: 2;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 5;" +
                            "-fx-border-radius: 5;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 2px;");
            setBorder(defaultBorder);
        });

        // Click/pressed effect
        setOnMousePressed(e -> {
            if (isRadioButton && isSelected) {
                return; // Don't change style if it's a selected radio button
            }
            String pressedColorHex = pressedColor != null
                    ? String.format("#%02X%02X%02X",
                            (int) (pressedColor.getRed() * 255),
                            (int) (pressedColor.getGreen() * 255),
                            (int) (pressedColor.getBlue() * 255))
                    : "#707070";
            setStyle(
                    "-fx-background-color: " + pressedColorHex + ";" +
                            "-fx-background-insets: 2;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 5;" +
                            "-fx-border-radius: 5;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 2px;");
            setBorder(new Border(
                    new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
        });

        setOnMouseReleased(e -> {
            if (isRadioButton && isSelected) {
                return; // Don't change style if it's a selected radio button
            }
            setStyle(
                    "-fx-background-color: #484848;" +
                            "-fx-background-insets: 2;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 5;" +
                            "-fx-border-radius: 5;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 2px;");
            setBorder(defaultBorder);
        });

        // Modify click event for radio button support
        setOnMouseClicked(e -> {
            if (isRadioButton) {
                setSelected(true);
            }
        });
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