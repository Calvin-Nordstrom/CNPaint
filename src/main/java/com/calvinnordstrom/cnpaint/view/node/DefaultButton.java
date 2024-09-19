package com.calvinnordstrom.cnpaint.view.node;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

public class DefaultButton extends Button {
    public DefaultButton() {
        this(null);
    }

    public DefaultButton(String text) {
        super(text);

        getStyleClass().add("default-button");

        init();
    }

    private void init() {
        setFocused(false);
        setFocusTraversable(false);
    }

    public void setFontIcon(FontIcon icon, int size, Color color) {
        icon.setIconSize(size);
        icon.setIconColor(color);
        setGraphic(icon);
        setBackground(null);
        setPadding(new Insets(0));
    }
}
