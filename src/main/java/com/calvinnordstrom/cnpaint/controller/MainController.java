package com.calvinnordstrom.cnpaint.controller;

import com.calvinnordstrom.cnpaint.model.MainModel;
import com.calvinnordstrom.cnpaint.tool.ToolManager;
import com.calvinnordstrom.cnpaint.tool.ToolType;
import com.calvinnordstrom.cnpaint.util.ImageUtils;
import com.calvinnordstrom.cnpaint.util.ServiceLocator;
import com.calvinnordstrom.cnpaint.view.MainView;
import com.calvinnordstrom.cnpaint.view.stage.ProgressStage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Parent;

public class MainController {
    private final MainModel model;
    private final MainView view;

    public MainController(MainModel model, MainView view) {
        this.model = model;
        this.view = view;

        initEventHandlers();
    }

    private void initEventHandlers() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();

        serviceLocator.getMenuItem("close").setOnAction(_ -> close());
        serviceLocator.getMenuItem("grayscale").setOnAction(_ -> {
            String text = serviceLocator.getMenuItem("grayscale").getText();
            Task<Void> task = ImageUtils.grayscale(view.getCurrentImage());
            setUpTask(text, task);
        });
        serviceLocator.getMenuItem("auto-level").setOnAction(_ -> {
            String text = serviceLocator.getMenuItem("auto-level").getText();
            Task<Void> task = ImageUtils.autoLevel(view.getCurrentImage());
            setUpTask(text, task);
        });
        serviceLocator.getMenuItem("invert-colors").setOnAction(_ -> {
            String text = serviceLocator.getMenuItem("invert-colors").getText();
            Task<Void> task = ImageUtils.invertColors(view.getCurrentImage());
            setUpTask(text, task);
        });

        serviceLocator.getNode("pencil-tool").setOnMouseClicked(_ -> setTool(ToolType.PENCIL));
        serviceLocator.getNode("paintbrush-tool").setOnMouseClicked(_ -> setTool(ToolType.PAINTBRUSH));
        serviceLocator.getNode("eraser-tool").setOnMouseClicked(_ -> setTool(ToolType.ERASER));
    }

    private void setUpTask(String text, Task<Void> task) {
        ProgressStage progressStage = new ProgressStage(text);
        progressStage.show();
        task.progressProperty().addListener((_, _, newValue) -> {
            progressStage.setProgress((double) newValue);
        });
        task.setOnSucceeded(_ -> {
            view.drawImage();
            progressStage.dispose();
        });
        task.setOnFailed(_ -> {
            System.out.println("Failed?");
        });
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
