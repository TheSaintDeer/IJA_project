package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ClassController {

//    public UMLClass umlclass;

    @FXML
    private CheckBox checkboxPackage;

    @FXML
    private CheckBox checkboxPrivate;

    @FXML
    private CheckBox checkboxProtected;

    @FXML
    private CheckBox checkboxPublic;

    @FXML
    private Button createNewAttribute;

    @FXML
    private Button createNewClass;

    @FXML
    private TextField nameOfAttribute;

    @FXML
    private TextField nameOfClass;

    @FXML
    void initialize() {
        createNewClass.setOnAction(event -> {
//
//            if (umlclass == null) {
//                if (nameOfClass.getText() != "") {
//                    System.out.println(nameOfClass.getText());
//                }
//            }

            createNewClass.getScene().getWindow().hide();
        });

    }

}
