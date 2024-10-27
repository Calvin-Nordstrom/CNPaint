package com.calvinnordstrom.cnpaint.tool;

import com.calvinnordstrom.cnpaint.view.control.IntegerControl;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;

public class ToolSettingsPool {
    private static ToolSettingsPool instance;
    private final IntegerProperty brushSize = new SimpleIntegerProperty(2) {
        @Override
        protected void invalidated() {
            brushSize.set(Math.clamp(brushSize.get(), 1, 1000));
        }
    };
    private final Node brushSizeControl = new IntegerControl("Brush size:", brushSize);

    private ToolSettingsPool() {}

    public int getBrushSize() {
        return brushSize.get();
    }

    public Node getBrushSizeControl() {
        return brushSizeControl;
    }

    public static ToolSettingsPool getInstance() {
        if (instance == null) {
            instance = new ToolSettingsPool();
        }
        return instance;
    }
}
