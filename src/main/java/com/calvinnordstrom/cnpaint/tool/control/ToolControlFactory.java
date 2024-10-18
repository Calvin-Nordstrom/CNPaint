package com.calvinnordstrom.cnpaint.tool.control;

import com.calvinnordstrom.cnpaint.tool.Tool;
import javafx.scene.layout.Pane;

public interface ToolControlFactory {
    Pane getControls(Tool tool);
}
