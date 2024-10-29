package com.calvinnordstrom.cnpaint.tool;

import com.calvinnordstrom.cnpaint.view.EditorPane;
import javafx.scene.input.MouseEvent;

public class PaintbrushTool implements Tool {
    @Override
    public String getDescription() {
        return "Left click to use primary color, right click to use secondary color.";
    }

    @Override
    public void onMousePressed(MouseEvent event, EditorPane editorPane) {

    }

    @Override
    public void onMouseDragged(MouseEvent event, EditorPane editorPane) {

    }

    @Override
    public void onMouseReleased(MouseEvent event, EditorPane editorPane) {

    }
}
