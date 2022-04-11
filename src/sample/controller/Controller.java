package sample.controller;

import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sample.Main;

import java.io.IOException;

public class Controller extends Main {

    @FXML
    private Button closeWindow;

    @FXML
    private Button createClass;

    @FXML
    private Button createRelat;

    @FXML
    private TextField fromClassX;

    @FXML
    private TextField fromClassY;

    @FXML
    private GridPane grPane;

    @FXML
    private TextField toClassX;

    @FXML
    private TextField toClassY;

    @FXML
    void initialize() {
        createClass.setOnAction(event -> {
            createNewClass();
        });

        closeWindow.setOnAction(event -> {
            closeWindow.getScene().getWindow().hide();
        });
    }

    void createNewClass() {
        grPane.add(getMyParent(), (countOfClass % 4), (countOfClass / 4));
        countOfClass++;
    }

    Parent getMyParent() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/fxml/class_sample.fxml"));

        ClassController controller = new ClassController(countOfClass);
        loader.setController(controller);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loader.getRoot();
    }

}
