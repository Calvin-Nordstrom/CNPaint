package com.calvinnordstrom.cnpaint.view.node;

import com.calvinnordstrom.cnpaint.view.EditorControlsPane;
import com.calvinnordstrom.cnpaint.view.EditorPane;
import com.calvinnordstrom.cnpaint.util.ServiceLocator;
import javafx.scene.control.Tab;

public class EditorTab extends Tab {
    private final EditorPane editor;

    public EditorTab(String text, EditorPane editor) {
        super(text, editor);
        this.editor = editor;

        getStyleClass().add("editor-tab");

        init();
    }

    private void init() {
        ServiceLocator.getInstance().register(editor, new EditorControlsPane(editor));
    }
}
