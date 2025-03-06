package dev.typeracist.typeracist.logic.inventory;

import dev.typeracist.typeracist.logic.global.ResourceManager;
import javafx.scene.image.Image;

public abstract class Item {
    private final String name;
    private final String description;
    private final int price;
    private final Image image;

    public Item(String name, String description, int price, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = ResourceManager.getImage(image);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public Image getImage() {
        return image;
    }

    public abstract Item copy();

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
