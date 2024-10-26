package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.util.ServiceLocator;
import com.calvinnordstrom.cnpaint.view.node.DefaultButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.entypo.Entypo;
import org.kordamp.ikonli.javafx.FontIcon;

public class ToolsPane extends VBox {
    private DefaultButton lastSelectedTool;

    public ToolsPane() {
        getStyleClass().add("tools-pane");

        init();
    }

    private void init() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();

        DefaultButton pencil = new DefaultButton();
        pencil.setFontIcon(FontIcon.of(Entypo.PENCIL), 30, Color.WHITE);
        pencil.getStyleClass().add("tool");
        pencil.getStyleClass().add("selected-tool");
        serviceLocator.register("pencil-tool", pencil);
        lastSelectedTool = pencil;
        handleOnAction(pencil);

        DefaultButton paintbrush = new DefaultButton();
        paintbrush.setFontIcon(FontIcon.of(Entypo.BRUSH), 30, Color.WHITE);
        paintbrush.getStyleClass().add("tool");
        serviceLocator.register("paintbrush-tool", paintbrush);
        handleOnAction(paintbrush);

        DefaultButton eraser = new DefaultButton();
        eraser.setFontIcon(FontIcon.of(Entypo.ERASER), 30, Color.WHITE);
        eraser.getStyleClass().add("tool");
        serviceLocator.register("eraser-tool", eraser);
        handleOnAction(eraser);

        getChildren().addAll(pencil, paintbrush, eraser);
    }

    private void handleOnAction(DefaultButton button) {
        button.setOnAction(_ -> {
            if (lastSelectedTool != null) {
                lastSelectedTool.getStyleClass().remove("selected-tool");
            }
            button.getStyleClass().add("selected-tool");
            lastSelectedTool = button;
        });
    }
}
