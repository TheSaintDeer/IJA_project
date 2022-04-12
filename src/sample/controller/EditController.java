package sample.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField newName;

    @FXML
    private Button submitButton;

    @FXML
    public String newNameStr;

    @FXML
    void initialize() {
        submitButton.setOnAction(actionEvent -> {
            newNameStr = newName.getText();
            submitButton.getScene().getWindow().hide();
        });
    }

}
