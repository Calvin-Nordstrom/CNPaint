package com.calvinnordstrom.cnpaint.adjustment;

import javafx.concurrent.Task;

class Adjustment {
    static final int PROGRESS_THRESHOLD = 500;

    Adjustment() {}

    static void startTask(Task<Void> task) {
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
