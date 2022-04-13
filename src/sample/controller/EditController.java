package sample.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
    private TextField newType;

    @FXML
    private MenuButton newVisibility;

    @FXML
    private Button submitButton;

    @FXML
    private MenuItem packageVisibility; // ~

    @FXML
    private MenuItem privateVisibility; // -

    @FXML
    private MenuItem protectedVisibility; // #

    @FXML
    private MenuItem publicVisibility;  // +

    @FXML
    public String newNameStr;

    @FXML
    public String newVisibilityStr;

    @FXML
    public String newTypeStr;

    @FXML
    void initialize() {

        submitButton.setOnAction(actionEvent -> {
            newNameStr = newName.getText();
            newTypeStr = newType.getText();
            submitButton.getScene().getWindow().hide();
        });

        publicVisibility.setOnAction(actionEvent -> {
            newVisibilityStr = "+";
            newVisibility.setText("+");
        });

        privateVisibility.setOnAction(actionEvent -> {
            newVisibilityStr = "-";
            newVisibility.setText("-");
        });

        protectedVisibility.setOnAction(actionEvent -> {
            newVisibilityStr = "#";
            newVisibility.setText("#");
        });

        packageVisibility.setOnAction(actionEvent -> {
            newVisibilityStr = "~";
            newVisibility.setText("~");
        });
    }

}
