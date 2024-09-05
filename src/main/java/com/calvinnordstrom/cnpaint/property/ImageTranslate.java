package com.calvinnordstrom.cnpaint.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ImageTranslate {
    private static ImageTranslate instance;
    private final DoubleProperty translateX = new SimpleDoubleProperty(0.0d);
    private final DoubleProperty translateY = new SimpleDoubleProperty(0.0d);

    private ImageTranslate() {}

    public static double getTranslateX() {
        return getInstance().translateX.get();
    }

    public static void setTranslateX(double translateX) {
        getInstance().translateX.set(translateX);
    }

    public static DoubleProperty translateXProperty() {
        return getInstance().translateX;
    }

    public static double getTranslateY() {
        return getInstance().translateY.get();
    }

    public static void setTranslateY(double translateY) {
        getInstance().translateY.set(translateY);
    }

    public static DoubleProperty translateYProperty() {
        return getInstance().translateY;
    }

    public static ImageTranslate getInstance() {
        if (instance == null) {
            instance = new ImageTranslate();
        }
        return instance;
    }
}
