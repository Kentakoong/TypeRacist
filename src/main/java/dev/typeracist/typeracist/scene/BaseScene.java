package dev.typeracist.typeracist.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class BaseScene extends Scene {
    public BaseScene(Parent parent, double v, double v1) {
        super(parent, v, v1);
    }

    public BaseScene(Parent parent) {
        super(parent);
    }

    public abstract void onSceneEnter();

    public abstract void onSceneLeave();
}
