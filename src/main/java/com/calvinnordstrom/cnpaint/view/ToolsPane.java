package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.view.node.DefaultButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.entypo.Entypo;
import org.kordamp.ikonli.javafx.FontIcon;

public class ToolsPane extends VBox {
    public ToolsPane() {
        getStyleClass().add("tools-pane");

        init();
    }

    private void init() {
        DefaultButton pencil = new DefaultButton();
        pencil.setFontIcon(FontIcon.of(Entypo.PENCIL), 30, Color.WHITE);
        pencil.getStyleClass().add("tool");

        getChildren().addAll(pencil);
    }
}
