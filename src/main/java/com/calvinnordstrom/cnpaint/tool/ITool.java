package com.calvinnordstrom.cnpaint.tool;

import javafx.scene.input.MouseEvent;

public interface ITool {
    void onMousePressed(MouseEvent event);
    void onMouseDragged(MouseEvent event);
    void onMouseReleased(MouseEvent event);
}
