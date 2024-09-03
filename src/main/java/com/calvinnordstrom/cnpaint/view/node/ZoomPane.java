package com.calvinnordstrom.cnpaint.view.node;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.StackPane;

public class ZoomPane extends StackPane {
    private final DoubleProperty scale;
    private double panX;
    private double panY;

    public ZoomPane() {
        scale = new SimpleDoubleProperty(1.0);
        panX = 0;
        panY = 0;

        init();
    }

    private void init() {
        scaleXProperty().bind(scale);
        scaleYProperty().bind(scale);
    }

    public double getScale() {
        return scale.get();
    }

    public void setScale(double newScale) {
        scale.set(newScale);
    }

    public void setPanX(double panX) {
        this.panX = panX;
    }

    public double getPanX() {
        return panX;
    }

    public void setPanY(double panY) {
        this.panY = panY;
    }

    public double getPanY() {
        return panY;
    }

    public void setPivot(double x, double y) {
        setTranslateX(getTranslateX() - x);
        setTranslateY(getTranslateY() - y);
    }
}
