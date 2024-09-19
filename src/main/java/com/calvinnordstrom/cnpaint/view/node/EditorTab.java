package com.calvinnordstrom.cnpaint.view.node;

import com.calvinnordstrom.cnpaint.view.EditorPane;
import javafx.scene.control.Tab;

public class EditorTab extends Tab {
    public EditorTab(String text, EditorPane editor) {
        super(text, editor);

        getStyleClass().add("default-tab");

        init();
    }

    private void init() {

    }
}
