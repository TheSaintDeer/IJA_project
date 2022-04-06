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
    private Button createNewAttribute;

    @FXML
    private Button createNewClass;

    @FXML
    private MenuItem customType;

    @FXML
    private MenuItem dateType;

    @FXML
    private MenuItem floatType;

    @FXML
    private MenuItem intType;

    @FXML
    private TextField nameOfAttribute;

    @FXML
    private TextField nameOfClass;

    @FXML
    private MenuItem stringType;

    @FXML
    void initialize() {
        createNewClass.setOnAction(event -> {

            createNewClass();

            createNewClass.getScene().getWindow().hide();
        });

        createNewAttribute.setOnAction(event -> {

            umlclass = createNewClass();
            UMLAttribute attr = new UMLAttribute(nameOfAttribute.getText(), MnemonicPars());
            //Create error if attribute had already created \/
            umlclass.addAttribute(attr);
            nameOfAttribute.setText("");

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

    public UMLClassifier MnemonicPars () {
        if (intType.isMnemonicParsing()) {
            return new UMLClassifier("Integer");
        } else if (floatType.isMnemonicParsing()) {
            return new UMLClassifier("Float");
        } else if (dateType.isMnemonicParsing()) {
            return new UMLClassifier("Date");
        } else if (stringType.isMnemonicParsing()) {
            return new UMLClassifier("String");
        } else {
            return new UMLClassifier("Custom");
        }

    }

}
