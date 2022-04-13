package sample.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditAttributeController {

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
    private Label warningLabel;

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

            if (newNameStr.equals("") || newTypeStr.equals("")) {
                warningLabel.setVisible(true);
            } else {
                submitButton.getScene().getWindow().hide();
            }

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
