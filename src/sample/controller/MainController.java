package sample.controller;

import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sample.Main;
import sample.controller.MainController;
import sample.uml.ClassDiagram;
import sample.uml.UMLClass;

import java.io.IOException;

public class MainController extends Main {

    @FXML
    private Button closeWindow;

    @FXML
    private Button createClass;

    @FXML
    private Button createRelat;

    @FXML
    private TextField fromClassX;

    @FXML
    private TextField fromClassY;

    @FXML
    private GridPane grPane;

    @FXML
    private TextField toClassX;

    @FXML
    private TextField toClassY;

    @FXML
    private ListView testListView;

    private ClassDiagram diagram;

    private int i;

    public MainController(ClassDiagram d) {
        this.diagram = d;
    }

    @FXML
    void initialize() {
//        i = 0;
//        testListView.setItems(diagram.getAll());

        for (UMLClass c :
                diagram.getAll()) {
            addNewClass(c);
        }

        createClass.setOnAction(event -> {
            addNewClass();

            diagram.createClass("Test");

        });

        closeWindow.setOnAction(event -> {
            closeWindow.getScene().getWindow().hide();
        });
    }

    void addNewClass() {
        grPane.add(createNewClass(), (countOfClass % 4), (countOfClass / 4));
        countOfClass++;
    }

    void addNewClass(UMLClass c) {
        grPane.add(createNewClass(c), (countOfClass % 4), (countOfClass / 4));
        countOfClass++;
    }

    Parent createNewClass() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/fxml/class_sample.fxml"));

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
        loader.setLocation(getClass().getResource("/sample/fxml/class_sample.fxml"));

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

}
