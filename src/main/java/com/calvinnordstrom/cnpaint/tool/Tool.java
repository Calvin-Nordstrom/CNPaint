package com.calvinnordstrom.cnpaint.tool;

import com.calvinnordstrom.cnpaint.property.MousePosition;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

public interface Tool {
    String getDescription();
    void onMousePressed(MouseEvent event, WritableImage image, MousePosition position);
    void onMouseDragged(MouseEvent event, WritableImage image, MousePosition position);
    void onMouseReleased(MouseEvent event, WritableImage image, MousePosition position);
}
