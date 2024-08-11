module com.calvinnordstrom.cnpaint {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.calvinnordstrom.cnpaint to javafx.fxml;
    exports com.calvinnordstrom.cnpaint;
}