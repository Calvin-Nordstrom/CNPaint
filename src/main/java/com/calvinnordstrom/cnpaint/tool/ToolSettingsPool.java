package com.calvinnordstrom.cnpaint.tool;

import com.calvinnordstrom.cnpaint.view.control.IntegerControl;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ToolSettingsPool {
    private static ToolSettingsPool instance;
    private final IntegerProperty brushSize = new SimpleIntegerProperty(2) {
        @Override
        protected void invalidated() {
            brushSize.set(Math.clamp(brushSize.get(), 1, 1000));
        }
    };
    private final Node brushSizeControl = new IntegerControl("Brush size:", brushSize, getBrushSizes());

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

    private static Integer[] getBrushSizes() {
        List<Integer> values = new ArrayList<>();

        IntStream.rangeClosed(1, 15).forEach(values::add);
        IntStream.iterate(20, i -> i <= 100, i -> i + 5).forEach(values::add);
        IntStream.iterate(125, i -> i <= 500, i -> i + 25).forEach(values::add);
        IntStream.iterate(550, i -> i <= 1000, i -> i + 50).forEach(values::add);
        IntStream.iterate(1100, i -> i <= 2000, i -> i + 100).forEach(values::add);

        return values.toArray(new Integer[0]);
    }
}
