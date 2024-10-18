package com.calvinnordstrom.cnpaint.tool.control;

import com.calvinnordstrom.cnpaint.tool.EraserTool;
import com.calvinnordstrom.cnpaint.tool.PencilTool;
import com.calvinnordstrom.cnpaint.tool.Tool;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class ToolControlFactoryRegistry {
    private static ToolControlFactoryRegistry instance;
    private final Map<Class<? extends Tool>, ToolControlFactory> registry = new HashMap<>();

    private ToolControlFactoryRegistry() {
        registry.put(PencilTool.class, new PencilToolControlFactory());
        registry.put(EraserTool.class, new EraserToolControlFactory());
    }

    public Pane getToolControls(Tool tool) {
        if (registry.get(tool.getClass()) != null) {
            return registry.get(tool.getClass()).getControls(tool);
        }
        return null;
    }

    public static ToolControlFactoryRegistry getInstance() {
        if (instance == null) {
            instance = new ToolControlFactoryRegistry();
        }
        return instance;
    }
}
