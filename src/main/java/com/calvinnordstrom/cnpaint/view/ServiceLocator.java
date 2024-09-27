package com.calvinnordstrom.cnpaint.view;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<String, MenuItem> menuItems = new HashMap<>();
    private static final Map<String, Button> buttons = new HashMap<>();
    private static final Map<String, Slider> sliders = new HashMap<>();

    private static final Map<EditorPane, EditorControlsPane> editorPanes = new HashMap<>();

    public static void register(String key, MenuItem value) {
        menuItems.put(key, value);
    }

    public static MenuItem getMenuItem(String key) {
        return menuItems.get(key);
    }

    public static void register(EditorPane key, EditorControlsPane value) {
        editorPanes.put(key, value);
    }

    public static EditorControlsPane getEditorControlsPane(EditorPane key) {
        return editorPanes.get(key);
    }
}
