package com.calvinnordstrom.cnpaint.controller;

import com.calvinnordstrom.cnpaint.adjustment.*;
import com.calvinnordstrom.cnpaint.model.MainModel;
import com.calvinnordstrom.cnpaint.tool.ToolManager;
import com.calvinnordstrom.cnpaint.tool.ToolType;
import com.calvinnordstrom.cnpaint.util.ServiceLocator;
import com.calvinnordstrom.cnpaint.view.MainView;
import com.calvinnordstrom.cnpaint.view.adjustment.HueSaturation;
import com.calvinnordstrom.cnpaint.view.stage.DefaultStage;
import com.calvinnordstrom.cnpaint.view.stage.ProgressStage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;

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
            Task<Void> task = Grayscale.apply(view.getCurrentImage());
            initTask(text, task);
        });
        serviceLocator.getMenuItem("auto-level").setOnAction(_ -> {
            String text = serviceLocator.getMenuItem("auto-level").getText();
            Task<Void> task = AutoLevel.apply(view.getCurrentImage());
            initTask(text, task);
        });
        serviceLocator.getMenuItem("invert-colors").setOnAction(_ -> {
            String text = serviceLocator.getMenuItem("invert-colors").getText();
            Task<Void> task = InvertColors.apply(view.getCurrentImage());
            initTask(text, task);
        });
        serviceLocator.getMenuItem("invert-alpha").setOnAction(_ -> {
            String text = serviceLocator.getMenuItem("invert-alpha").getText();
            Task<Void> task = InvertAlpha.apply(view.getCurrentImage());
            initTask(text, task);
        });
        serviceLocator.getMenuItem("hue-saturation").setOnAction(_ -> {
            String text = serviceLocator.getMenuItem("hue-saturation").getText();
            initMenu(text, new Scene(new HueSaturation()));
        });

        serviceLocator.getNode("pencil-tool").setOnMouseClicked(_ -> setTool(ToolType.PENCIL));
        serviceLocator.getNode("paintbrush-tool").setOnMouseClicked(_ -> setTool(ToolType.PAINTBRUSH));
        serviceLocator.getNode("eraser-tool").setOnMouseClicked(_ -> setTool(ToolType.ERASER));
    }

    private void initTask(String text, Task<Void> task) {
        ProgressStage stage = new ProgressStage(text);
        stage.show();
        task.progressProperty().addListener((_, _, newValue) -> {
            stage.setProgress((double) newValue);
        });
        task.setOnSucceeded(_ -> {
            view.drawImage();
            stage.dispose();
        });
        task.setOnFailed(_ -> {
            System.out.println("Failed?");
        });
    }

    private void initMenu(String text, Scene scene) {
        DefaultStage menu = new DefaultStage(text);
        menu.setScene(scene);
        menu.show();
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
