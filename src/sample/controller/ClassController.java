package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.Main;

public class ClassController extends Main {

    public static int index;

//    public static int getIndex() {
//        return index;
//    }
//
//    public static void setIndex(int index) {
//        ClassController.index = index;
//    }

    public ClassController(int i) {
        index = i;
    }

    @FXML
    private Button acceptClass;

    @FXML
    private Button addAttr;

    @FXML
    private Button addMetod;

    @FXML
    private TextField attr1;

    @FXML
    private TextField attr2;

    @FXML
    private Button changeClass;

    @FXML
    private Button deleteClass;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField metod1;

    @FXML
    private TextField metod2;

    @FXML
    private TextField nameClass;

    @FXML
    void initialize() {
        nameClass.setText(String.valueOf(countOfClass));

        deleteClass.setOnAction(event -> {
            System.out.println("Delete class on index: " + index + ", count of class: " + countOfClass);
        });
    }

}
