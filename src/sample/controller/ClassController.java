package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.uml.UMLAttribute;
import sample.uml.UMLClass;
import sample.uml.UMLClassifier;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.util.Objects;

public class ClassController extends Main {

    public UMLClass umlclass;

    @FXML
    private MenuItem booleanType;

    @FXML
    private Button createNewAttribute;

    @FXML
    private Button createNewClass;

    @FXML
    private MenuItem customType;

    @FXML
    private MenuItem intType;

    @FXML
    private SplitMenuButton menuButton;

    @FXML
    private TextField nameOfAttribute;

    @FXML
    private TextField nameOfClass;

    @FXML
    private MenuItem stringType;

    @FXML
    private MenuItem voidType;

    @FXML
    void initialize() {
        createNewClass.setOnAction(event -> {

            createNewClass();

            createNewClass.getScene().getWindow().hide();
        });

        createNewAttribute.setOnAction(event -> {

            umlclass = createNewClass();
//            UMLAttribute attr = new UMLAttribute(nameOfAttribute.getText(), menuButton.getText());
            //Create error if attribute had already created \/
//            umlclass.addAttribute(attr);
            nameOfAttribute.setText("");

        });

        menuButton.setOnAction(event -> {

            MnemonicPars();
        });

    }

    public UMLClass createNewClass () {
        if (this.umlclass == null) {
            if (!Objects.equals(nameOfClass.getText(), "")) {
                this.umlclass = classDiagram.createClass(nameOfClass.getText());
            }
        }

        return umlclass;
    }

    public void MnemonicPars () {
        if (intType.isMnemonicParsing()) {
            menuButton.setText("Integer");
        } else if (stringType.isMnemonicParsing()) {
            menuButton.setText("String");
        } else if (booleanType.isMnemonicParsing()) {
            menuButton.setText("Boolean");
        } else if (voidType.isMnemonicParsing()) {
            menuButton.setText("Void");
        } else {
            menuButton.setText("Custom");
        }

    }

}
