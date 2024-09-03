package com.calvinnordstrom.cnpaint.event;

import com.calvinnordstrom.cnpaint.util.DragContext;
import com.calvinnordstrom.cnpaint.view.node.ZoomPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class PaneEventHandler {
    private static final double MAX_SCALE = 20.0d;
    private static final double MIN_SCALE = .1d;
    private static final double SCALE_FACTOR = 1.2d;
    private final ZoomPane pane;
    private final DragContext dragContext;

    public PaneEventHandler(ZoomPane pane) {
        this.pane = pane;
        dragContext = new DragContext();
    }

    public EventHandler<MouseEvent> getOnMousePressed() {
        return event -> {
            if (!event.isMiddleButtonDown()) return;
            dragContext.x = event.getX();
            dragContext.y = event.getY();
            dragContext.dx = pane.getTranslateX();
            dragContext.dy = pane.getTranslateY();
        };
    }

    public EventHandler<MouseEvent> getOnMouseDragged() {
        return event -> {
            if (!event.isMiddleButtonDown()) return;
            pane.setTranslateX(dragContext.dx + event.getX() - dragContext.x);
            pane.setTranslateY(dragContext.dy + event.getY() - dragContext.y);
            pane.setPanX(dragContext.dx + event.getX() - dragContext.x);
            pane.setPanY(dragContext.dy + event.getY() - dragContext.y);
        };
    }

    public EventHandler<ScrollEvent> getOnScroll() {
        return event -> {
            if (!event.isControlDown()) return;
            double scale = pane.getScale();
            double oldScale = scale;

            if (event.getDeltaY() < 0) {
                scale /= SCALE_FACTOR;
            } else {
                scale *= SCALE_FACTOR;
            }
            scale = Math.clamp(scale, MIN_SCALE, MAX_SCALE);

            double factor = (scale / oldScale) - 1;
            double dx = (event.getX() - (pane.getBoundsInParent().getWidth() / 2
                    + pane.getBoundsInParent().getMinX()));
            double dy = (event.getY() - (pane.getBoundsInParent().getHeight() / 2
                    + pane.getBoundsInParent().getMinY()));

            pane.setPivot(factor * dx, factor * dy);
            pane.setScale(scale);
        };
    }
}
