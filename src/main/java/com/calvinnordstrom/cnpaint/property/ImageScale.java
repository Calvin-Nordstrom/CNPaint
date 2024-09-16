package com.calvinnordstrom.cnpaint.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * {@code ImageScale} is a singleton class responsible for storing and
 * manipulating image scale data used by this editor.
 * <p>
 * Because only one image can be open at a time, the
 * singleton design pattern grants the ability to access and manipulate the
 * image scale data from any part of the application.
 *
 * @author Calvin Nordstrom
 */
public class ImageScale {
    /**
     * The lowest image scale.
     */
    public static final double MIN_SCALE = 1;
    /**
     * The highest image scale.
     */
    public static final double MAX_SCALE = 10000;
    /**
     * The default image scale.
     */
    public static final double DEFAULT_SCALE = 100;
    /**
     * The coefficient of which images are scaled by.
     */
    public static final double SCALE_FACTOR = 1.2d;
    private static ImageScale instance;
    private final DoubleProperty scale = new SimpleDoubleProperty(DEFAULT_SCALE);
    private final DoubleProperty percent = new SimpleDoubleProperty(50);
    private static double defaultEditorScale = DEFAULT_SCALE;

    private ImageScale() {}

    /**
     * Returns the scale attribute.
     *
     * @return the scale
     */
    public static double getScale() {
        return getInstance().scale.get();
    }

    /**
     * Returns the default editor scale attribute.
     *
     * @return the default editor scale
     */
    public static double getDefaultEditorScale() {
        return defaultEditorScale;
    }

    /**
     * Sets the scale attribute to the specified scale.
     *
     * @param scale the new scale
     */
    public static void setScale(double scale) {
        getInstance().scale.set(Math.clamp(scale, ImageScale.MIN_SCALE, ImageScale.MAX_SCALE));
    }

    /**
     * Sets the default editor scale attribute to the specified scale.
     *
     * @param scale the new scale
     */
    public static void setDefaultEditorScale(double scale) {
        defaultEditorScale = scale;
    }

    /**
     * Returns the {@code DoubleProperty} associated with the scale of
     * the image.
     *
     * @return the {@code DoubleProperty} scale
     */
    public static DoubleProperty scaleProperty() {
        return getInstance().scale;
    }

    /**
     * Returns the {@code DoubleProperty} associated with the scale percent of
     * the image.
     *
     * @return the {@code DoubleProperty} scale percent
     */
    public static DoubleProperty percentProperty() {
        return getInstance().percent;
    }

    /**
     * Multiplies the scale by a factor of {@code SCALE_FACTOR}.
     */
    public static void upscale() {
        setScale(getInstance().scale.get() * SCALE_FACTOR);
    }

    /**
     * Divides the scale by a factor of {@code SCALE_FACTOR}.
     */
    public static void downscale() {
        setScale(getInstance().scale.get() / SCALE_FACTOR);
    }

    /**
     * Returns the corresponding percent of the specified scale.
     * <p>
     * This function is the mathematical inverse of the exponential function
     * used to map the percentage 0 to {@code MIN_SCALE}, 50 to
     * {@code DEFAULT_SCALE}, and 100 to {@code MAX_SCALE}.
     *
     * @param scale the scale
     * @return the percentage of the specified scale
     */
    public static double toPercent(double scale) {
        return (25 * Math.log(scale)) / Math.log(10);
    }

    /**
     * Returns the corresponding scale in the range {@code MIN_SCALE} to
     * {@code MAX_SCALE} of the specified percentage.
     * <p>
     * This function is an exponential function used to map the percentage 0 to
     * {@code MIN_SCALE}, 50 to {@code DEFAULT_SCALE}, and 100 to
     * {@code MAX_SCALE}.
     *
     * @param percent the percentage
     * @return the scale of the specified percentage
     */
    public static double fromPercent(double percent) {
        return Math.pow(Math.E, (Math.log(10) * percent) / 25);
    }

    /**
     * Returns the singleton instance of the {@code ImageScale} class. An
     * instance will be created if one does not already exist.
     *
     * @return the {@code ImageScale} singleton instance
     */
    public static ImageScale getInstance() {
        if (instance == null) {
            instance = new ImageScale();
        }
        return instance;
    }
}
