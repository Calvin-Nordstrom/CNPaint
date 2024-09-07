package com.calvinnordstrom.cnpaint.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ImageBounds {
    private static ImageBounds instance;
    private final DoubleProperty width = new SimpleDoubleProperty(0);
    private final DoubleProperty height = new SimpleDoubleProperty(0);

    private ImageBounds() {}

    public static double getWidth() {
        return getInstance().width.get();
    }

    public static void setWidth(double width) {
        getInstance().width.set(width);
    }

    public static DoubleProperty widthProperty() {
        return getInstance().width;
    }

    public static double getHeight() {
        return getInstance().height.get();
    }

    public static void setHeight(double height) {
        getInstance().height.set(height);
    }

    public static DoubleProperty heightProperty() {
        return getInstance().height;
    }

    public static ImageBounds getInstance() {
        if (instance == null) {
            instance = new ImageBounds();
        }
        return instance;
    }
}
