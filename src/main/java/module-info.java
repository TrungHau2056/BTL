module org.example.btl {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens org.example.btl to javafx.fxml;
    opens org.example.btl.libraryManage to javafx.fxml;
    exports org.example.btl.libraryManage;
    exports org.example.btl.controllers to javafx.fxml;
}