package com.calvinnordstrom.cnpaint.tool;

import com.calvinnordstrom.cnpaint.view.EditorPane;
import javafx.scene.input.MouseEvent;

public interface Tool {
    String getDescription();
    void onMousePressed(MouseEvent event, EditorPane editorPane);
    void onMouseDragged(MouseEvent event, EditorPane editorPane);
    void onMouseReleased(MouseEvent event, EditorPane editorPane);
}
