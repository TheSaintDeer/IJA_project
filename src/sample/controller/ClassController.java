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

    void initialize() {
        createNewClass.setOnAction(event -> {
            createNewClass.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/fxml/sample.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

    }

}
