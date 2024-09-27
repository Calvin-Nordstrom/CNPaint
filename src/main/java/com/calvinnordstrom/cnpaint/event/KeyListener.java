package com.calvinnordstrom.cnpaint.event;

import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class KeyListener {
    private static final KeyListener instance = new KeyListener();
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    private KeyListener() {}

    public void keyPressed(KeyCode keyCode) {
        pressedKeys.add(keyCode);
    }

    public void keyReleased(KeyCode keyCode) {
        pressedKeys.remove(keyCode);
    }

    public boolean isKeyPressed(KeyCode keyCode) {
        return pressedKeys.contains(keyCode);
    }

    public static KeyListener getInstance() {
        return instance;
    }
}
