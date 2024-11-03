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
    requires java.compiler;
    requires java.desktop;
    requires java.persistence;
    requires com.fasterxml.jackson.annotation;

    opens org.example.btl to javafx.fxml, org.hibernate.orm.core;
    opens org.example.btl.model to org.hibernate.orm.core;
    opens org.example.btl.controller to javafx.fxml, org.hibernate.orm.core;
//    opens org.example.btl.fxmlFiles to javafx.fxml;
    exports org.example.btl;
    exports org.example.btl.controller;

}