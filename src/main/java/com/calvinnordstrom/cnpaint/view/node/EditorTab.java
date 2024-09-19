package com.calvinnordstrom.cnpaint.view.node;

import com.calvinnordstrom.cnpaint.view.EditorControlsPane;
import com.calvinnordstrom.cnpaint.view.EditorPane;
import javafx.scene.control.Tab;

public class EditorTab extends Tab {
    private final EditorControlsPane editorControls;

    public EditorTab(String text, EditorPane editor) {
        super(text, editor);

        editorControls = new EditorControlsPane(editor);

        init();
    }

    private void init() {

    }

    public EditorControlsPane getEditorControls() {
        return editorControls;
    }
}
