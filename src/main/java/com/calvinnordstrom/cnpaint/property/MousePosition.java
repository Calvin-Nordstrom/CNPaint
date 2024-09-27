package com.calvinnordstrom.cnpaint.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * {@code MousePosition} is a singleton class responsible for storing and
 * manipulating the mouse position within the editor.
 * <p>
 * Because only one image can be open at a time, the
 * singleton design pattern grants the ability to access and manipulate the
 * mouse position data from any part of the application.
 *
 * @author Calvin Nordstrom
 */
public class MousePosition {
    private static final MousePosition instance = new MousePosition();
    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    private final IntegerProperty intX = new SimpleIntegerProperty();
    private final IntegerProperty intY = new SimpleIntegerProperty();

    private MousePosition() {
        x.addListener((_, _, _) -> intX.set(round(x.get())));
        y.addListener((_, _, _) -> intY.set(round(y.get())));
    }

    /**
     * Returns the precise x position attribute of the mouse.
     *
     * @return the x position
     */
    public static double getX() {
        return getInstance().x.get();
    }

    /**
     * Sets the precise x position attribute of the mouse.
     *
     * @param x the new x position
     */
    public static void setX(double x) {
        getInstance().x.set(x);
    }

    /**
     * Returns the {@code DoubleProperty} associated with the precise x
     * position of the mouse.
     *
     * @return the {@code DoubleProperty} x position
     */
    public static DoubleProperty xProperty() {
        return getInstance().x;
    }

    /**
     * Returns the precise y position attribute of the mouse.
     *
     * @return the y position
     */
    public static double getY() {
        return getInstance().y.get();
    }

    /**
     * Sets the precise y position attribute of the mouse.
     *
     * @param y the new y position
     */
    public static void setY(double y) {
        getInstance().y.set(y);
    }

    /**
     * Returns the {@code DoubleProperty} associated with the precise y
     * position of the mouse.
     *
     * @return the {@code DoubleProperty} y position
     */
    public static DoubleProperty yProperty() {
        return getInstance().y;
    }

    /**
     * Returns the {@code IntegerProperty} associated with the integer x
     * position of the mouse.
     *
     * @return the {@code IntegerProperty} x position
     */
    public static IntegerProperty xIntProperty() {
        return getInstance().intX;
    }

    /**
     * Returns the {@code IntegerProperty} associated with the integer y
     * position of the mouse.
     *
     * @return the {@code IntegerProperty} y position
     */
    public static IntegerProperty yIntProperty() {
        return getInstance().intY;
    }

    private static int round(double value) {
        return value < 0 ? (int) Math.floor(value) : (int) value;
    }

    /**
     * Returns the singleton instance of the {@code MousePosition} class. An
     * instance will be created if one does not already exist.
     *
     * @return the {@code MousePosition} singleton instance
     */
    public static MousePosition getInstance() {
        return instance;
    }
}
