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
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens org.example.btl to javafx.fxml, org.hibernate.orm.core;
    opens org.example.btl.controllers to javafx.fxml;
    opens org.example.btl.libraryManage to org.hibernate.orm.core;
    exports org.example.btl;
}