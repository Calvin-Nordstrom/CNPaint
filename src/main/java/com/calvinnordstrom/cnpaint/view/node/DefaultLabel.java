package com.calvinnordstrom.cnpaint.view.node;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

public class DefaultLabel extends Label {
    public DefaultLabel() {
        this(null);
    }

    public DefaultLabel(String text) {
        super(text);

        getStyleClass().add("default-label");

        init();
    }

    private void init() {}

    public void setFontIcon(FontIcon icon, int size, Color color) {
        icon.setIconSize(size);
        icon.setIconColor(color);
        setGraphic(icon);
    }
}
