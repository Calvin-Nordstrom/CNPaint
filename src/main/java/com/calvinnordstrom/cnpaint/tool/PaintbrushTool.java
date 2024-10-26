package com.calvinnordstrom.cnpaint.tool;

import com.calvinnordstrom.cnpaint.property.MousePosition;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

public class PaintbrushTool implements Tool {
    @Override
    public String getDescription() {
        return "Left click to use primary color, right click to use secondary color.";
    }

    @Override
    public void onMousePressed(MouseEvent event, WritableImage image, MousePosition position) {

    }

    @Override
    public void onMouseDragged(MouseEvent event, WritableImage image, MousePosition position) {

    }

    @Override
    public void onMouseReleased(MouseEvent event, WritableImage image, MousePosition position) {

    }
}
