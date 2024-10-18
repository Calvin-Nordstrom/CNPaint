package com.calvinnordstrom.cnpaint.tool;

import com.calvinnordstrom.cnpaint.property.MousePosition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ToolManager {
    private static ToolManager instance;
    private final ObjectProperty<Tool> toolProperty = new SimpleObjectProperty<>();
    private final Map<ToolType, Tool> tools = new LinkedHashMap<>();

    private ToolManager() {
        tools.put(ToolType.PENCIL, new PencilTool());
        tools.put(ToolType.ERASER, new EraserTool());
        toolProperty.set(tools.get(ToolType.PENCIL));
    }

    public void handleMousePressed(MouseEvent event, WritableImage image, MousePosition position) {
        if (toolProperty.get() != null) {
            toolProperty.get().onMousePressed(event, image, position);
        }
    }

    public void handleMouseDragged(MouseEvent event, WritableImage image, MousePosition position) {
        if (toolProperty.get() != null) {
            toolProperty.get().onMouseDragged(event, image, position);
        }
    }

    public void handleMouseReleased(MouseEvent event, WritableImage image, MousePosition position) {
        if (toolProperty.get() != null) {
            toolProperty.get().onMouseReleased(event, image, position);
        }
    }

    public Tool getTool() {
        return toolProperty.get();
    }

    public void setTool(ToolType toolType) {
        this.toolProperty.set(tools.get(toolType));
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

    public static ToolManager getInstance() {
        if (instance == null) {
            instance = new ToolManager();
        }
        return instance;
    }
}
