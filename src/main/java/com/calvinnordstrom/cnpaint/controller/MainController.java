package com.calvinnordstrom.cnpaint.controller;

import com.calvinnordstrom.cnpaint.model.MainModel;
import com.calvinnordstrom.cnpaint.tool.ToolManager;
import com.calvinnordstrom.cnpaint.tool.ToolType;
import com.calvinnordstrom.cnpaint.view.MainView;
import com.calvinnordstrom.cnpaint.util.ServiceLocator;
import javafx.application.Platform;
import javafx.scene.Parent;

public class MainController {
    private final MainModel model;
    private final MainView view;

    public MainController(MainModel model, MainView view) {
        this.model = model;
        this.view = view;

        initListeners();
    }

    private void initListeners() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();

        serviceLocator.getMenuItem("close").setOnAction(_ -> close());

        serviceLocator.getNode("pencil-tool").setOnMouseClicked(_ -> setTool(ToolType.PENCIL));
        serviceLocator.getNode("paintbrush-tool").setOnMouseClicked(_ -> setTool(ToolType.PAINTBRUSH));
        serviceLocator.getNode("eraser-tool").setOnMouseClicked(_ -> setTool(ToolType.ERASER));
    }

    public void setTool(ToolType toolType) {
        ToolManager tm = ToolManager.getInstance();
        tm.setTool(toolType);
    }

    public void close() {
        Platform.exit();
    }

    public Parent getView() {
        return view;
    }
}
