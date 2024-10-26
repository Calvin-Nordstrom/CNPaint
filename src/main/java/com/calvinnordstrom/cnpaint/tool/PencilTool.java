package com.calvinnordstrom.cnpaint.tool;

import com.calvinnordstrom.cnpaint.property.MousePosition;
import com.calvinnordstrom.cnpaint.util.DragContext;
import com.calvinnordstrom.cnpaint.util.ImageUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

public class PencilTool implements Tool {
    private final DragContext dragContext = new DragContext();

    @Override
    public String getDescription() {
        return "Draws one-pixel wide lines. Left click to use primary color, right click to use secondary color.";
    }

    @Override
    public void onMousePressed(MouseEvent event, WritableImage image, MousePosition position) {
        int x = position.getIntX();
        int y = position.getIntY();
        dragContext.x = x;
        dragContext.y = y;
        image.getPixelWriter().setColor(x, y, ImageUtils.getColor(event));
    }

    @Override
    public void onMouseDragged(MouseEvent event, WritableImage image, MousePosition position) {
        int x = position.getIntX();
        int y = position.getIntY();
        ImageUtils.drawLine(image.getPixelWriter(), dragContext.x, dragContext.y, x, y, ImageUtils.getColor(event));
        dragContext.x = x;
        dragContext.y = y;
    }

    @Override
    public void onMouseReleased(MouseEvent event, WritableImage image, MousePosition position) {

    }
}
