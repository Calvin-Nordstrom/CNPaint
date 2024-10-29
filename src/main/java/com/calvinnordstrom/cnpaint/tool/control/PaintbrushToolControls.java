package com.calvinnordstrom.cnpaint.tool.control;

import com.calvinnordstrom.cnpaint.tool.PaintbrushTool;
import com.calvinnordstrom.cnpaint.tool.Tool;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PaintbrushToolControls implements ToolControls {
    public PaintbrushToolControls() {}

    @Override
    public Pane getControls(Tool tool) {
        PaintbrushTool paintbrushTool = (PaintbrushTool) tool;

        Node brushSizeControl = ToolControlsPool.getInstance().getBrushSizeControl();

        return new HBox(5, new Separator(Orientation.VERTICAL), brushSizeControl);
    }
}
