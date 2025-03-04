package dev.typeracist.typeracist.logic.global;

import javafx.scene.image.Image;
import javafx.scene.text.Font;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static final Map<String, Image> imageCache = new HashMap<>();
    private static final Map<String, Font> fontCache = new HashMap<>();

    public static Image getImage(String path) {
        return imageCache.computeIfAbsent(path, ResourceManager::loadImage);
    }

    private static Image loadImage(String path) {
        InputStream stream = ResourceManager.class.getResourceAsStream(path);
        if (stream != null) {
            return new Image(stream);
        }
        return null;
    }

    public static Font getFont(String path, double size) {
        String key = path + "_" + size;
        return fontCache.computeIfAbsent(key, k -> loadFont(path, size));
    }

    private static Font loadFont(String path, double size) {
        InputStream stream = ResourceManager.class.getResourceAsStream(path);
        if (stream != null) {
            try {
                Font font = Font.loadFont(stream, size);
                stream.close();
                return font;
            } catch (IOException e) {
                System.err.println("Error loading font: " + path);
                e.printStackTrace();
            }
        }

        return Font.font("System", size);
    }
}