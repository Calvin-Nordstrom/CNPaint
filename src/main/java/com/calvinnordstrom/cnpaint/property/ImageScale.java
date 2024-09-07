package com.calvinnordstrom.cnpaint.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ImageScale {
    public static final double MAX_SCALE = 10000;
    public static final double MIN_SCALE = 1;
    public static final double DEFAULT_SCALE = 100;
    public static final double SCALE_FACTOR = 1.2d;
    private static ImageScale instance;
    private final DoubleProperty scale = new SimpleDoubleProperty(DEFAULT_SCALE);
    private final DoubleProperty percent = new SimpleDoubleProperty(50);

    private ImageScale() {
        percent.addListener((_, _, newValue) -> setScale(fromPercent((double) newValue)));
    }

    public static double getScale() {
        return getInstance().scale.get();
    }

    public static void setScale(double scale) {
        getInstance().scale.set(Math.clamp(scale, ImageScale.MIN_SCALE, ImageScale.MAX_SCALE));
    }

    public static DoubleProperty scaleProperty() {
        return getInstance().scale;
    }

    public static DoubleProperty percentProperty() {
        return getInstance().percent;
    }

    public static void upscale() {
        setScale(getInstance().scale.get() * SCALE_FACTOR);
    }

    public static void downscale() {
        setScale(getInstance().scale.get() / SCALE_FACTOR);
    }

    public static double toPercent(double value) {
        return (25 * Math.log(value)) / Math.log(10);
    }

    public static double fromPercent(double value) {
        return Math.pow(Math.E, (Math.log(10) * value) / 25);
    }

    public static ImageScale getInstance() {
        if (instance == null) {
            instance = new ImageScale();
        }
        return instance;
    }
}
