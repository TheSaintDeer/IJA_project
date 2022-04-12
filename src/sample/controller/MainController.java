package sample.controller;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Line;
import sample.Main;
import sample.controller.MainController;
import sample.uml.ClassDiagram;
import sample.uml.UMLClass;

import java.io.IOException;

public class MainController extends Main {

    @FXML
    private Button acceptRelat;

    @FXML
    private Button closeWindow;

    @FXML
    private Button createClass;

    @FXML
    private Button createRelat;

    @FXML
    private TextField fromClass;

    @FXML
    private Label labelFrom;

    @FXML
    private Label labelTo;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField nameOfClass;

    @FXML
    private TextField toClass;

    private ClassDiagram diagram;

    private int i = 0;

    public MainController(ClassDiagram d) {
        this.diagram = d;
    }

    @FXML
    void initialize() {

        for (UMLClass c : diagram.getAll()) {
            System.out.println("adding class: " + c.getName());
            addNewClass(c);
        }

        createClass.setOnAction(event -> {
//            UMLClass newClass = diagram.createClass("Test " + i++);
//            addNewClass(newClass);
            diagram.findClass("Command").setName("Hahaha");
        });

        closeWindow.setOnAction(event -> {
            closeWindow.getScene().getWindow().hide();
        });
        createRelat.setOnAction(event -> {
            visibleObject(true);
        });
        acceptRelat.setOnAction(event -> {
            visibleObject(false);
            clearField();
        });
    }

    void addNewClass(UMLClass c) {

        Node newClass = createNewClass(c);

        newClass.setLayoutX(200*countOfClass);
        newClass.setLayoutY(200*countOfClass++);

//        newClass.
        mainPane.getChildren().add(newClass);

//        String nameClass = nameOfClass.getText();
//
//        if (!nameClass.isEmpty()) {
////            nameOfClass.setVisible(false);
//            nameOfClass.setText(null);
//
//            if (c != null) {
//                TitledPane titledPane = new TitledPane(nameClass, createNewClass(c));
//                titledPane.setId(nameClass.replaceAll("\\s+","€"));
//                titledPane.setPrefSize(-1, -1);
//                titledPane.setLayoutX(200*(countOfClass%4));
//                titledPane.setLayoutY(300*(countOfClass++/4));
//                titledPane.setCollapsible(false);
//
//                mainPane.getChildren().add(titledPane);
//            }
//
//
//        }
    }

    Parent createNewClass() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/fxml/class_sample2.fxml"));

        ClassController controller = new ClassController(countOfClass);
        loader.setController(controller);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loader.getRoot();
    }

    Parent createNewClass(UMLClass c) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/fxml/class_sample2.fxml"));

        ClassController controller = new ClassController(countOfClass);
        controller.setClass(c);
        loader.setController(controller);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loader.getRoot();
    }

    private void visibleObject(boolean switcher) {
        labelFrom.setVisible(switcher);
        labelTo.setVisible(switcher);
        fromClass.setVisible(switcher);
        toClass.setVisible(switcher);
        acceptRelat.setVisible(switcher);
    }

    public void clearField () {
        fromClass.setText("");
        toClass.setText("");
    }


}
