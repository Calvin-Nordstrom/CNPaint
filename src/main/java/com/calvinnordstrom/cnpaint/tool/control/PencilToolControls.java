package com.calvinnordstrom.cnpaint.tool.control;

import com.calvinnordstrom.cnpaint.tool.PencilTool;
import com.calvinnordstrom.cnpaint.tool.Tool;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PencilToolControls implements ToolControls {
    public PencilToolControls() {}

    @Override
    public Pane getControls(Tool tool) {
        PencilTool pencilTool = (PencilTool) tool;

        HBox controls = new HBox();

        return controls;
    }
}
