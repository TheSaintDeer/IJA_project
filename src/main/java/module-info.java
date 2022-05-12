module com.umleditor.umleditor {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.umleditor to javafx.fxml;
    exports com.umleditor;
    exports com.umleditor.controller;
    exports com.umleditor.uml;

    opens com.umleditor.uml to com.google.gson;
    opens com.umleditor.controller to javafx.fxml;
    exports com.umleditor.adapters;
    opens com.umleditor.adapters to com.google.gson;
}