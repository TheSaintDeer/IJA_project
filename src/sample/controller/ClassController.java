package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.uml.UMLClass;

public class ClassController extends Main {

    public static int index;

    private UMLClass c;

    public ClassController(int i) {
        index = i;
    }

//    @FXML
//    private Button acceptClass;
//
//    @FXML
//    private Button addAttr;
//
//    @FXML
//    private Button addMetod;
//
//    @FXML
//    private TextField attr1;
//
//    @FXML
//    private TextField attr2;
//
//    @FXML
//    private Button changeClass;
//
//    @FXML
//    private Button deleteClass;
//
//    @FXML
//    private AnchorPane mainPane;
//
//    @FXML
//    private TextField metod1;
//
//    @FXML
//    private TextField metod2;
//
//    @FXML
//    private TextField nameClass;

    @FXML
    private TitledPane titledPane;

    @FXML
    private ListView listView;
    @FXML
    void initialize() {


//        titledPane.textProperty().bindBidirectional(c.nameProperty());
        listView.setItems(c.getAttributes());
//        nameClass.textProperty().bindBidirectional(c.nameProperty());
//
//
//        deleteClass.setOnAction(event -> {
//            System.out.println("Delete class on index: " + index + ", count of class: " + countOfClass);
//        });
    }

    public void setClass(UMLClass c) {
        this.c = c;
    }
}
