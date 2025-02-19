package dev.typeracist.typeracist.logic.global;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public abstract class BaseDynamicScene<T extends Pane> extends Scene {
    protected final Pane rootPane;
    protected final HashMap<Integer, T> panes;
    protected int currentPaneId;

    public BaseDynamicScene(T rootPane) {
        super(rootPane);
        this.rootPane = rootPane;
        this.panes = new HashMap<>();
        this.currentPaneId = -1;
    }

    public BaseDynamicScene(T rootPane, double width, double height) {
        super(rootPane, width, height);
        this.rootPane = rootPane;
        this.panes = new HashMap<>();
        this.currentPaneId = -1;
    }

    public void addPane(int id, T pane) {
        panes.put(id, pane);
    }

    public void loadPane(int id) {
        if (!panes.containsKey(id))
            throw new IllegalArgumentException("No such pane with id " + id);

        rootPane.getChildren().setAll(panes.get(id));
        currentPaneId = id;
    }

    public int getCurrentPaneId() {
        return currentPaneId;
    }

    public Pane getCurrentPane() {
        return panes.get(currentPaneId);
    }

    public HashMap<Integer, T> getPanes() {
        return panes;
    }
}
