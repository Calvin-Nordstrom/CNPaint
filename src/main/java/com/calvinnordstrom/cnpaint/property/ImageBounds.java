package com.calvinnordstrom.cnpaint.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * {@code ImageBounds} is responsible for storing and manipulating the image
 * bounds for an editor.
 *
 * @author Calvin Nordstrom
 */
public class ImageBounds {
    private final DoubleProperty width = new SimpleDoubleProperty(0);
    private final DoubleProperty height = new SimpleDoubleProperty(0);

    /**
     * Constructs an {@code ImageBounds} with the default width and height
     * properties.
     */
    public ImageBounds() {}

    /**
     * Returns the width attribute.
     *
     * @return the width
     */
    public double getWidth() {
        return width.get();
    }

    /**
     * Sets the width attribute to the specified width.
     *
     * @param width the new width
     */
    public void setWidth(double width) {
        this.width.set(width);
    }

    /**
     * Returns the {@code DoubleProperty} associated with the width of
     * the image.
     *
     * @return the {@code DoubleProperty} width
     */
    public DoubleProperty widthProperty() {
        return width;
    }

    /**
     * Returns the height attribute.
     *
     * @return the width
     */
    public double getHeight() {
        return height.get();
    }

    /**
     * Sets the height attribute to the specified height.
     *
     * @param height the new height
     */
    public void setHeight(double height) {
        this.height.set(height);
    }

    /**
     * Returns the {@code DoubleProperty} associated with the height of
     * the image.
     *
     * @return the {@code DoubleProperty} height
     */
    public DoubleProperty heightProperty() {
        return height;
    }
}
