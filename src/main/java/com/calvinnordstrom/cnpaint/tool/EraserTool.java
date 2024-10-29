package com.calvinnordstrom.cnpaint.tool;

import com.calvinnordstrom.cnpaint.util.DragContext;
import com.calvinnordstrom.cnpaint.util.ImageUtils;
import com.calvinnordstrom.cnpaint.view.EditorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class EraserTool implements Tool {
    private final DragContext dragContext = new DragContext();

    @Override
    public String getDescription() {
        return "Click and drag to erase pixels around the mouse.";
    }

    @Override
    public void onMousePressed(MouseEvent event, EditorPane editorPane) {
        int x = editorPane.getMousePosition().getIntX();
        int y = editorPane.getMousePosition().getIntY();
        dragContext.x = x;
        dragContext.y = y;

        editorPane.getImage().getPixelWriter().setColor(x, y, Color.TRANSPARENT);
    }

    @Override
    public void onMouseDragged(MouseEvent event, EditorPane editorPane) {
        int x = editorPane.getMousePosition().getIntX();
        int y = editorPane.getMousePosition().getIntY();

        ImageUtils.drawLine(editorPane.getImage().getPixelWriter(), dragContext.x, dragContext.y, x, y, Color.TRANSPARENT);

        dragContext.x = x;
        dragContext.y = y;
    }

    @Override
    public void onMouseReleased(MouseEvent event, EditorPane editorPane) {

    }
}
