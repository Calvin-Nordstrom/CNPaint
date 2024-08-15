module com.calvinnordstrom.cnpaint {
    requires javafx.controls;
    requires java.logging;
    requires java.desktop;

    exports com.calvinnordstrom.cnpaint;
    exports com.calvinnordstrom.cnpaint.controller;
    exports com.calvinnordstrom.cnpaint.model;
    exports com.calvinnordstrom.cnpaint.view;
}