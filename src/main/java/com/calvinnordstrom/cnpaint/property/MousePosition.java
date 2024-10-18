package com.calvinnordstrom.cnpaint.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * {@code MousePosition} is responsible for storing and manipulating the mouse
 * position within the editor.
 *
 * @author Calvin Nordstrom
 */
public class MousePosition {
    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    private final IntegerProperty intX = new SimpleIntegerProperty();
    private final IntegerProperty intY = new SimpleIntegerProperty();

    /**
     * Constructs a {@code MousePosition} with the default mouse position of
     * {@code x = 0}, and {@code y = 0}.
     */
    public MousePosition() {
        x.addListener((_, _, _) -> intX.set(round(x.get())));
        y.addListener((_, _, _) -> intY.set(round(y.get())));
    }

    /**
     * Returns the precise x position attribute of the mouse.
     *
     * @return the x position
     */
    public double getX() {
        return x.get();
    }

    /**
     * Sets the precise x position attribute of the mouse.
     *
     * @param x the new x position
     */
    public void setX(double x) {
        this.x.set(x);
    }

    /**
     * Returns the {@code DoubleProperty} associated with the precise x
     * position of the mouse.
     *
     * @return the {@code DoubleProperty} x position
     */
    public DoubleProperty xProperty() {
        return x;
    }

    /**
     * Returns the precise y position attribute of the mouse.
     *
     * @return the y position
     */
    public double getY() {
        return y.get();
    }

    /**
     * Sets the precise y position attribute of the mouse.
     *
     * @param y the new y position
     */
    public void setY(double y) {
        this.y.set(y);
    }

    /**
     * Returns the {@code DoubleProperty} associated with the precise y
     * position of the mouse.
     *
     * @return the {@code DoubleProperty} y position
     */
    public DoubleProperty yProperty() {
        return y;
    }

    /**
     * Returns the x position attribute of the mouse as an integer.
     *
     * @return the x position integer
     */
    public int getIntX() {
        return intX.get();
    }

    /**
     * Returns the {@code IntegerProperty} associated with the integer x
     * position of the mouse.
     *
     * @return the {@code IntegerProperty} x position
     */
    public IntegerProperty xIntProperty() {
        return intX;
    }

    /**
     * Returns the y position attribute of the mouse as an integer.
     *
     * @return the y position integer
     */
    public int getIntY() {
        return intY.get();
    }

    /**
     * Returns the {@code IntegerProperty} associated with the integer y
     * position of the mouse.
     *
     * @return the {@code IntegerProperty} y position
     */
    public IntegerProperty yIntProperty() {
        return intY;
    }

    private int round(double value) {
        return value < 0 ? (int) Math.floor(value) : (int) value;
    }
}
