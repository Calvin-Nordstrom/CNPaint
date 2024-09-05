module com.calvinnordstrom.cnpaint {
    requires javafx.controls;
    requires java.logging;
    requires java.desktop;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.entypo;
    requires org.kordamp.ikonli.evaicons;

    exports com.calvinnordstrom.cnpaint;
    exports com.calvinnordstrom.cnpaint.controller;
    exports com.calvinnordstrom.cnpaint.model;
    exports com.calvinnordstrom.cnpaint.view;
    exports com.calvinnordstrom.cnpaint.view.node;
    exports com.calvinnordstrom.cnpaint.property;
}