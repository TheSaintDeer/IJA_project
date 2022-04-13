package sample.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditClassnameController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField newName;

    @FXML
    private Button submitButton;

    @FXML
    private Label warningLabel;

    @FXML
    public String newClassNameStr;

    @FXML
    void initialize() {

        submitButton.setOnAction(actionEvent -> {

            newClassNameStr = newName.getText();
            if (newName.equals("")) {
                warningLabel.setVisible(true);
            } else {
                submitButton.getScene().getWindow().hide();
            }

        });

    }

}
