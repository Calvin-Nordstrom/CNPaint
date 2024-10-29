package com.calvinnordstrom.cnpaint.tool;

import com.calvinnordstrom.cnpaint.tool.control.EraserToolControls;
import com.calvinnordstrom.cnpaint.tool.control.PaintbrushToolControls;
import com.calvinnordstrom.cnpaint.tool.control.PencilToolControls;
import com.calvinnordstrom.cnpaint.tool.control.ToolControls;
import com.calvinnordstrom.cnpaint.view.EditorPane;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ToolManager {
    private static ToolManager instance;
    private final Map<ToolType, Tool> tools = new LinkedHashMap<>();
    private final Map<Class<? extends Tool>, ToolControls> controls = new LinkedHashMap<>();
    private final ObjectProperty<Tool> toolProperty;

    private ToolManager() {
        register(ToolType.PENCIL, new PencilTool(), new PencilToolControls());
        register(ToolType.PAINTBRUSH, new PaintbrushTool(), new PaintbrushToolControls());
        register(ToolType.ERASER, new EraserTool(), new EraserToolControls());
        toolProperty = new SimpleObjectProperty<>(tools.get(ToolType.PENCIL));
    }

    private void register(ToolType toolType, Tool tool, ToolControls factory) {
        tools.put(toolType, tool);
        controls.put(tool.getClass(), factory);
    }

    public void handleMousePressed(MouseEvent event, EditorPane editorPane) {
        if (toolProperty.get() != null) {
            toolProperty.get().onMousePressed(event, editorPane);
        }
    }

    public void handleMouseDragged(MouseEvent event, EditorPane editorPane) {
        if (toolProperty.get() != null) {
            toolProperty.get().onMouseDragged(event, editorPane);
        }
    }

    public void handleMouseReleased(MouseEvent event, EditorPane editorPane) {
        if (toolProperty.get() != null) {
            toolProperty.get().onMouseReleased(event, editorPane);
        }
    }

    public Tool getTool() {
        return toolProperty.get();
    }

    public void setTool(ToolType toolType) {
        toolProperty.set(tools.get(toolType));
    }

    public ObjectProperty<Tool> toolProperty() {
        return toolProperty;
    }

    public Set<ToolType> getToolKeys() {
        return tools.keySet();
    }

    public ToolType getToolType(Tool tool) {
        for (ToolType toolType : tools.keySet()) {
            if (tools.get(toolType).getClass().equals(tool.getClass())) {
                return toolType;
            }
        }
        return null;
    }

    public Pane getToolControls(Tool tool) {
        if (controls.get(tool.getClass()) != null) {
            return controls.get(tool.getClass()).getControls(tool);
        }
        return null;
    }

    public static ToolManager getInstance() {
        if (instance == null) {
            instance = new ToolManager();
        }
        return instance;
    }
}
