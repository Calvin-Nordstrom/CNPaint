package com.calvinnordstrom.cnpaint.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.StackPane;

public class ZoomPane extends StackPane {
    private final DoubleProperty scale;

    public ZoomPane() {
        scale = new SimpleDoubleProperty(1.0);

        scaleXProperty().bind(scale);
        scaleYProperty().bind(scale);
    }

    public double getScale() {
        return scale.get();
    }

    public void setScale(double newScale) {
        scale.set(newScale);
    }

    public void setPivot(double x, double y) {
        setTranslateX(getTranslateX() - x);
        setTranslateY(getTranslateY() - y);
    }
}
