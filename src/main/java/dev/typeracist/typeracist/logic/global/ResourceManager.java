package dev.typeracist.typeracist.logic.global;


import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static final Map<String, Image> imageCache = new HashMap<>();

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
}