package com.calvinnordstrom.cnpaint.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ImageScale {
    public static final double MAX_SCALE = 20.0d;
    public static final double MIN_SCALE = 0.05d;
    public static final double DEFAULT_SCALE = 1.0d;
    public static final double SCALE_FACTOR = 1.2d;
    private static ImageScale instance;
    private final DoubleProperty scale = new SimpleDoubleProperty(DEFAULT_SCALE);

    private ImageScale() {}

    public static double getScale() {
        return getInstance().scale.get();
    }

    public static void setScale(double scale) {
        getInstance().scale.set(Math.clamp(scale, ImageScale.MIN_SCALE, ImageScale.MAX_SCALE));
    }

    public static DoubleProperty scaleProperty() {
        return getInstance().scale;
    }

    public static void downscale() {
        setScale(getInstance().scale.get() / SCALE_FACTOR);
    }

    public static void upscale() {
        setScale(getInstance().scale.get() * SCALE_FACTOR);
    }

    public static ImageScale getInstance() {
        if (instance == null) {
            instance = new ImageScale();
        }
        return instance;
    }
}
