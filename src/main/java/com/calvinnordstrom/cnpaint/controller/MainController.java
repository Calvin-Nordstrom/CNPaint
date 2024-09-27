package com.calvinnordstrom.cnpaint.controller;

import com.calvinnordstrom.cnpaint.model.MainModel;
import com.calvinnordstrom.cnpaint.view.MainView;
import com.calvinnordstrom.cnpaint.view.ServiceLocator;
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
        ServiceLocator.getMenuItem("close").setOnAction(_ -> close());
    }

    public void close() {
        Platform.exit();
    }

    public Parent getView() {
        return view;
    }
}
