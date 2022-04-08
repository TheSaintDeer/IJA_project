package sample.controller;

import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class Controller {

    @FXML
    private Button createClass;

    @FXML
    private Button dropClass;

    @FXML
    private Button selectClass;

    @FXML
    void initialize() {
        createClass.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/fxml/class_sample.fxml"));

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

        dropClass.setOnAction(event -> {
            System.out.println("Drop Table");
        });

        selectClass.setOnAction(event -> {
            System.out.println("Select Table");
        });

    }

}
