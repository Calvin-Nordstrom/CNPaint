package com.calvinnordstrom.cnpaint.view;

import com.calvinnordstrom.cnpaint.util.ServiceLocator;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuPane extends MenuBar {
    public MenuPane() {
        getStyleClass().add("menu-pane");

        init();
    }

    private void init() {
        Menu fileMenu = new Menu("File");
        add(fileMenu, "close", new MenuItem("Close"));

        Menu editMenu = new Menu("Edit");

        Menu viewMenu = new Menu("View");

        Menu adjustmentsMenu = new Menu("Adjustments");
        add(adjustmentsMenu, "grayscale", new MenuItem("Grayscale"));
        add(adjustmentsMenu, "auto-level", new MenuItem("Auto-Level"));
        add(adjustmentsMenu, "invert-colors", new MenuItem("Invert Colors"));
        add(adjustmentsMenu, "invert-alpha", new MenuItem("Invert Alpha"));

        Menu effectsMenu = new Menu("Effects");

        getMenus().setAll(fileMenu, editMenu, viewMenu, adjustmentsMenu, effectsMenu);
    }

    private void add(Menu menu, String key, MenuItem value) {
        menu.getItems().add(value);
        ServiceLocator.getInstance().register(key, value);
    }
}
