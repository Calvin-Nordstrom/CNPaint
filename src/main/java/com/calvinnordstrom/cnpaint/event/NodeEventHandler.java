package com.calvinnordstrom.cnpaint.event;

import com.calvinnordstrom.cnpaint.util.DragContext;
import com.calvinnordstrom.cnpaint.view.ZoomPane;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class NodeEventHandler {
    private final ZoomPane pane;
    private final DragContext dragContext;

    public NodeEventHandler(ZoomPane pane) {
        this.pane = pane;
        dragContext = new DragContext();
    }

    public EventHandler<MouseEvent> getOnMousePressed() {
        return event -> {
            if (!event.isPrimaryButtonDown()) return;
            Node node = (Node) event.getSource();
            dragContext.x = event.getSceneX();
            dragContext.y = event.getSceneY();
            dragContext.dx = node.getTranslateX();
            dragContext.dy = node.getTranslateY();
        };
    }

    public EventHandler<MouseEvent> getOnMouseDragged() {
        return event -> {
            if (!event.isPrimaryButtonDown()) return;
            double scale = pane.getScale();
            Node node = (Node) event.getSource();
            node.setTranslateX(dragContext.dx + ((event.getSceneX() - dragContext.x) / scale));
            node.setTranslateY(dragContext.dy + ((event.getSceneY() - dragContext.y) / scale));
            event.consume();
        };
    }
}
