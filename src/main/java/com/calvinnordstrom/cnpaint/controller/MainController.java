package com.calvinnordstrom.cnpaint.controller;

import com.calvinnordstrom.cnpaint.model.MainModel;
import com.calvinnordstrom.cnpaint.view.MainView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.input.ScrollEvent;

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

        view.addScrollPaneListener("scrollPane", this::zoomEditor);
    }

    public void close() {
        Platform.exit();
    }

    public void zoomEditor(ScrollEvent event) {
        if (event.isControlDown()) {
            event.consume();
        }
    }

    public Parent getView() {
        return view.getView();
    }
}