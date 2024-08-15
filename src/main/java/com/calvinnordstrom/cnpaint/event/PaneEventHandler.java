package com.calvinnordstrom.cnpaint.event;

import com.calvinnordstrom.cnpaint.util.DragContext;
import com.calvinnordstrom.cnpaint.view.ZoomPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class PaneEventHandler {
    private static final double MAX_SCALE = 20.0d;
    private static final double MIN_SCALE = .1d;
    private final ZoomPane pane;
    private final DragContext dragContext;

    public PaneEventHandler(ZoomPane pane) {
        this.pane = pane;
        dragContext = new DragContext();
    }

    public EventHandler<MouseEvent> getOnMousePressed() {
        return event -> {
            if (!event.isMiddleButtonDown()) return;
            dragContext.x = event.getSceneX();
            dragContext.y = event.getSceneY();
            dragContext.dx = pane.getTranslateX();
            dragContext.dy = pane.getTranslateY();
        };
    }

    public EventHandler<MouseEvent> getOnMouseDragged() {
        return event -> {
            if (!event.isMiddleButtonDown()) return;
            pane.setTranslateX(dragContext.dx + event.getSceneX() - dragContext.x);
            pane.setTranslateY(dragContext.dy + event.getSceneY() - dragContext.y);
        };
    }

    public EventHandler<ScrollEvent> getOnScroll() {
        return event -> {
            if (!event.isControlDown()) return;
            double delta = 1.2;
            double scale = pane.getScale();
            double oldScale = scale;

            if (event.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = Math.clamp(scale, MIN_SCALE, MAX_SCALE);

            double f = (scale / oldScale) - 1;
            double dx = (event.getSceneX()
                    - (pane.getBoundsInParent().getWidth() / 2
                    + pane.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY()
                    - (pane.getBoundsInParent().getHeight() / 2
                    + pane.getBoundsInParent().getMinY()));

            pane.setScale(scale);
            pane.setPivot(f * dx, f * dy);
        };
    }
}
