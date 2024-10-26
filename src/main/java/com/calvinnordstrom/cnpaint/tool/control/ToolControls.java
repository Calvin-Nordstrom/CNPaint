package com.calvinnordstrom.cnpaint.tool.control;

import com.calvinnordstrom.cnpaint.tool.Tool;
import javafx.scene.layout.Pane;

public interface ToolControls {
    Pane getControls(Tool tool);
}
