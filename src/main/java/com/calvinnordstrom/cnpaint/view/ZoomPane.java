package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.property.ImageScale;
import com.calvinnordstrom.cnpaint.property.ImageTranslate;
import javafx.scene.layout.StackPane;

public class ZoomPane extends StackPane {
    public ZoomPane() {
        init();
    }

    private void init() {
        scaleXProperty().bind(ImageScale.scaleProperty().divide(100));
        scaleYProperty().bind(ImageScale.scaleProperty().divide(100));
        translateXProperty().bind(ImageTranslate.translateXProperty());
        translateYProperty().bind(ImageTranslate.translateYProperty());
    }

    public void setPivot(double x, double y) {
        ImageTranslate.setTranslateX(getTranslateX() - x);
        ImageTranslate.setTranslateY(getTranslateY() - y);
    }
}
