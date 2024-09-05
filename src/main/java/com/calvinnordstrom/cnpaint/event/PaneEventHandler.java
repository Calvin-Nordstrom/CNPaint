package com.calvinnordstrom.cnpaint.event;

import com.calvinnordstrom.cnpaint.property.ImageScale;
import com.calvinnordstrom.cnpaint.property.ImageTranslate;
import com.calvinnordstrom.cnpaint.util.DragContext;
import com.calvinnordstrom.cnpaint.view.ZoomPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class PaneEventHandler {
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
            ImageTranslate.setTranslateX(dragContext.dx + event.getX() - dragContext.x);
            ImageTranslate.setTranslateY(dragContext.dy + event.getY() - dragContext.y);
        };
    }

    public EventHandler<ScrollEvent> getOnScroll() {
        return event -> {
            if (!event.isControlDown()) return;
            double scale = ImageScale.getScale();

            if (event.getDeltaY() < 0) {
                ImageScale.downscale();
            } else {
                ImageScale.upscale();
            }

            double factor = (ImageScale.getScale() / scale) - 1;
            double dx = (event.getX() - (pane.getBoundsInParent().getWidth() / 2
                    + pane.getBoundsInParent().getMinX()));
            double dy = (event.getY() - (pane.getBoundsInParent().getHeight() / 2
                    + pane.getBoundsInParent().getMinY()));

            pane.setPivot(factor * dx, factor * dy);
        };
    }
}
