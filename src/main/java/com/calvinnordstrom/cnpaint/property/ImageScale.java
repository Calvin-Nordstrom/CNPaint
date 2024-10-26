package com.calvinnordstrom.cnpaint.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * {@link ImageScale} is responsible for storing and manipulating the image
 * scale for an editor.
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
    private final DoubleProperty scale = new SimpleDoubleProperty(DEFAULT_SCALE);
    private final DoubleProperty percent = new SimpleDoubleProperty(50);
    private double defaultEditorScale = DEFAULT_SCALE;

    /**
     * Constructs an {@link ImageScale} with the default scale and percent
     * properties and the default editor scale attribute.
     */
    public ImageScale() {}

    /**
     * Returns the scale attribute.
     *
     * @return the scale
     */
    public double getScale() {
        return scale.get();
    }

    /**
     * Sets the scale property to the specified scale. The scale will be
     * clamped between {@code MIN_SCALE} and {@code MAX_SCALE}.
     *
     * @param scale the new scale
     */
    public void setScale(double scale) {
        this.scale.set(Math.clamp(scale, ImageScale.MIN_SCALE, ImageScale.MAX_SCALE));
    }

    /**
     * Returns the {@link DoubleProperty} associated with the scale.
     *
     * @return the {@link DoubleProperty} scale
     */
    public DoubleProperty scaleProperty() {
        return scale;
    }

    /**
     * Returns the default editor scale attribute.
     *
     * @return the default editor scale
     */
    public double getDefaultEditorScale() {
        return defaultEditorScale;
    }

    /**
     * Sets the default editor scale property to the specified scale.
     *
     * @param scale the new scale
     */
    public void setDefaultEditorScale(double scale) {
        defaultEditorScale = scale;
    }

    /**
     * Returns the {@link DoubleProperty} associated with the scale percent.
     *
     * @return the {@link DoubleProperty} scale percent
     */
    public DoubleProperty percentProperty() {
        return percent;
    }

    /**
     * Multiplies the scale by a factor of {@code SCALE_FACTOR}.
     */
    public void upscale() {
        setScale(scale.get() * SCALE_FACTOR);
    }

    /**
     * Divides the scale by a factor of {@code SCALE_FACTOR}.
     */
    public void downscale() {
        setScale(scale.get() / SCALE_FACTOR);
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
}
