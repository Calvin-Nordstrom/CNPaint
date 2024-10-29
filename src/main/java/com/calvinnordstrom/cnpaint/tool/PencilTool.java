package com.calvinnordstrom.cnpaint.tool;

import com.calvinnordstrom.cnpaint.util.DragContext;
import com.calvinnordstrom.cnpaint.util.ImageUtils;
import com.calvinnordstrom.cnpaint.view.EditorPane;
import javafx.scene.input.MouseEvent;

public class PencilTool extends AbstractTool implements Tool {
    private final DragContext dragContext = new DragContext();

    @Override
    public String getDescription() {
        return "Draws one-pixel wide lines. Left click to use primary color, right click to use secondary color.";
    }

    @Override
    public void onMousePressed(MouseEvent event, EditorPane editorPane) {
        this.editorPane = editorPane;
        int x = editorPane.getMousePosition().getIntX();
        int y = editorPane.getMousePosition().getIntY();
        dragContext.x = x;
        dragContext.y = y;

        editorPane.getImage().getPixelWriter().setColor(x, y, ImageUtils.getColor(event));
        editorPane.drawImage();
    }

    @Override
    public void onMouseDragged(MouseEvent event, EditorPane editorPane) {
        int x = editorPane.getMousePosition().getIntX();
        int y = editorPane.getMousePosition().getIntY();

        ImageUtils.drawLine(editorPane.getImage().getPixelWriter(),
                dragContext.x, dragContext.y, x, y, ImageUtils.getColor(event));
        timeline.play();

        dragContext.x = x;
        dragContext.y = y;
    }

    @Override
    public void onMouseReleased(MouseEvent event, EditorPane editorPane) {
        timeline.stop();
        editorPane.drawImage();
    }
}
