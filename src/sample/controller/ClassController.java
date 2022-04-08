package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;
import sample.uml.UMLAttribute;
import sample.uml.UMLClass;
import sample.uml.UMLClassifier;

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
    private TextField fieldCustom;

    @FXML
    private MenuItem customType;

    @FXML
    private MenuItem intType;

    @FXML
    private MenuButton menuButton;

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
            UMLAttribute attr;
            if (Objects.equals(menuButton.getText(), "Custom")) {
                attr = new UMLAttribute(nameOfAttribute.getText(), new UMLClassifier(fieldCustom.getText()));
            } else {
                attr = new UMLAttribute(nameOfAttribute.getText(), new UMLClassifier(menuButton.getText()));
            }

//            attr = new UMLAttribute(nameOfAttribute.getText(), new UMLClassifier(menuButton.getText()));
//            Create error if attribute had already created \/
            umlclass.addAttribute(attr);
            clearCustomField();
            nameOfAttribute.setText("");
            menuButton.setText("Type of attribute");
        });

        intType.setOnAction(event -> {
            clearCustomField();
            menuButton.setText("Integer");
        });

        stringType.setOnAction(event -> {
            clearCustomField();
            menuButton.setText("String");
        });

        booleanType.setOnAction(event -> {
            clearCustomField();
            menuButton.setText("Boolean");
        });

        voidType.setOnAction(event -> {
            clearCustomField();
            menuButton.setText("Void");
        });

        customType.setOnAction(event -> {
            menuButton.setText("Custom");
            fieldCustom.setEditable(true);
            fieldCustom.setPromptText("Enter your type");
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

    public void clearCustomField () {
        fieldCustom.setEditable(false);
        fieldCustom.setPromptText("");
        fieldCustom.setText("");
    }


}
