package com.calvinnordstrom.cnpaint.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * {@code ImageBounds} is a singleton class responsible for storing and
 * manipulating image bounds data used by this editor.
 * <p>
 * Because only one image can be open at a time, the
 * singleton design pattern grants the ability to access and manipulate the
 * image bounds data from any part of the application.
 *
 * @author Calvin Nordstrom
 */
public class ImageBounds {
    private static ImageBounds instance;
    private final DoubleProperty width = new SimpleDoubleProperty(0);
    private final DoubleProperty height = new SimpleDoubleProperty(0);

    private ImageBounds() {}

    /**
     * Returns the width attribute.
     *
     * @return the width
     */
    public static double getWidth() {
        return getInstance().width.get();
    }

    /**
     * Sets the width attribute to the specified width.
     *
     * @param width the new width
     */
    public static void setWidth(double width) {
        getInstance().width.set(width);
    }

    /**
     * Returns the {@code DoubleProperty} associated with the width of
     * the image.
     *
     * @return the {@code DoubleProperty} width
     */
    public static DoubleProperty widthProperty() {
        return getInstance().width;
    }

    /**
     * Returns the height attribute.
     *
     * @return the width
     */
    public static double getHeight() {
        return getInstance().height.get();
    }

    /**
     * Sets the height attribute to the specified height.
     *
     * @param height the new height
     */
    public static void setHeight(double height) {
        getInstance().height.set(height);
    }

    /**
     * Returns the {@code DoubleProperty} associated with the height of
     * the image.
     *
     * @return the {@code DoubleProperty} height
     */
    public static DoubleProperty heightProperty() {
        return getInstance().height;
    }

    /**
     * Returns the singleton instance of the {@code ImageBounds} class. An
     * instance will be created if one does not already exist.
     *
     * @return the {@code ImageBounds} singleton instance
     */
    public static ImageBounds getInstance() {
        if (instance == null) {
            instance = new ImageBounds();
        }
        return instance;
    }
}
