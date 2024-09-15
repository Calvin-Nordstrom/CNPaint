package com.calvinnordstrom.cnpaint.controller;

import com.calvinnordstrom.cnpaint.model.MainModel;
import com.calvinnordstrom.cnpaint.view.MainView;
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
        view.addMenuItemListener("close", _ -> close());
    }

    public void close() {
        Platform.exit();
    }

    public Parent getView() {
        return view.getView();
    }
}