package dev.typeracist.typeracist.utils;

/**
 * Enum representing different difficulty levels in the game.
 */
public enum Difficulty {
    EASY("Easy"),
    NORMAL("Normal"),
    HARD("Hard"),
    HELL("Hell");

    private final String displayName;

    Difficulty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get a Difficulty enum value from its display name.
     * 
     * @param displayName the display name to look up
     * @return the corresponding Difficulty enum value, or NORMAL if not found
     */
    public static Difficulty fromDisplayName(String displayName) {
        for (Difficulty difficulty : values()) {
            if (difficulty.getDisplayName().equals(displayName)) {
                return difficulty;
            }
        }
        return NORMAL; // Default to NORMAL if not found
    }
}