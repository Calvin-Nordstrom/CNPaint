package com.calvinnordstrom.cnpaint.util;

import com.calvinnordstrom.cnpaint.Main;
import javafx.scene.image.Image;

/**
 * A utility class for holding references to the resources used in this
 * Application.
 */
public class Resources {
    private Resources() {}

    /**
     * The default Image used when the editor is initialized.
     * <p>
     * This Image is completely white and has dimensions {@code width = 960}
     * pixels and {@code height = 540} pixels.
     */
    public static final Image DEFAULT_IMAGE = new Image(String.valueOf(Main.class.getResource("default.png")));
}
