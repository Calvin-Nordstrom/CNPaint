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
    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    private final DoubleProperty width = new SimpleDoubleProperty();
    private final DoubleProperty height = new SimpleDoubleProperty();

    /**
     * Constructs an {@code ImageBounds} with the default width and height
     * properties.
     */
    public ImageBounds() {}

    /**
     * Returns the x position attribute.
     *
     * @return the x position
     */
    public double getX() {
        return x.get();
    }

    /**
     * Sets the x position attribute to the specified x position.
     *
     * @param x the new x position
     */
    public void setX(double x) {
        this.x.set(x);
    }

    /**
     * Returns the {@code DoubleProperty} associated with the x position of
     * the image.
     *
     * @return the {@code DoubleProperty} x position
     */
    public DoubleProperty xProperty() {
        return x;
    }

    /**
     * Returns the y position attribute.
     *
     * @return the y position
     */
    public double getY() {
        return y.get();
    }

    /**
     * Sets the y position attribute to the specified y position.
     *
     * @param y the new y position
     */
    public void setY(double y) {
        this.y.set(y);
    }

    /**
     * Returns the {@code DoubleProperty} associated with the y position of
     * the image.
     *
     * @return the {@code DoubleProperty} y position
     */
    public DoubleProperty yProperty() {
        return y;
    }

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
     * Returns the {@code DoubleProperty} associated with the width of the
     * image.
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
     * Returns the {@code DoubleProperty} associated with the height of the
     * image.
     *
     * @return the {@code DoubleProperty} height
     */
    public DoubleProperty heightProperty() {
        return height;
    }
}
