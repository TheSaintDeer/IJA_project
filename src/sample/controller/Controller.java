package sample.controller;

import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class Controller extends Main {

    public static int countRow = 0;

    @FXML
    private Button closeWindow;

    @FXML
    private Button createClass;

    @FXML
    private GridPane grPane;

    @FXML
    void initialize() {
        createClass.setOnAction(event -> {
            Parent root = getMyParent();
            createNewClass(root);
        });

        closeWindow.setOnAction(event -> {
            closeWindow.getScene().getWindow().hide();
        });
    }

    void createNewClass(Parent root) {
        grPane.add(root, (countOfClass % 4), (countOfClass / 4));
        countOfClass++;
    }

    Parent getMyParent() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/fxml/class_sample.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loader.getRoot();
    }

}
