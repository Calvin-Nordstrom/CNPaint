package com.calvinnordstrom.cnpaint.tool.control;

import com.calvinnordstrom.cnpaint.tool.EraserTool;
import com.calvinnordstrom.cnpaint.tool.Tool;
import com.calvinnordstrom.cnpaint.tool.ToolSettingsPool;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class EraserToolControls implements ToolControls {
    public EraserToolControls() {}

    @Override
    public Pane getControls(Tool tool) {
        EraserTool eraserTool = (EraserTool) tool;

        Node brushSizeControl = ToolSettingsPool.getInstance().getBrushSizeControl();

        return new HBox(5, brushSizeControl);
    }
}
