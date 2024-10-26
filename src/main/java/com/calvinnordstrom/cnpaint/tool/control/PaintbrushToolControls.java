package com.calvinnordstrom.cnpaint.tool.control;

import com.calvinnordstrom.cnpaint.tool.PaintbrushTool;
import com.calvinnordstrom.cnpaint.tool.Tool;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PaintbrushToolControls implements ToolControls {
    public PaintbrushToolControls() {}

    @Override
    public Pane getControls(Tool tool) {
        PaintbrushTool eraserTool = (PaintbrushTool) tool;

        HBox controls = new HBox();

        return controls;
    }
}
