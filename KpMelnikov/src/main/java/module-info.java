module com.example.kpmelnikov {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.antlr.antlr4.runtime;
    requires java.desktop;
    requires javafx.swing;

    opens com.example.kpmelnikov to javafx.fxml;
    exports com.example.kpmelnikov;
    exports com.example.kpmelnikov.parser;
}