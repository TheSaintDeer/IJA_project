package sample.controller;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Line;
import sample.Main;
import sample.controller.MainController;
import sample.uml.ClassDiagram;
import sample.uml.UMLClass;

import java.io.IOException;
import java.util.List;

public class MainController extends Main {

    private String nameOfActiveObject = null;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    @FXML
    private Button acceptClass;

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
            nameOfClass.setVisible(true);
            acceptClass.setVisible(true);
        });

        acceptClass.setOnAction(event -> {
            UMLClass newClass = diagram.createClass(nameOfClass.getText());
            if (newClass == null) {
                System.out.println("error eblan");
            } else {
                nameOfClass.setVisible(false);
                acceptClass.setVisible(false);
                addNewClass(newClass);
                nameOfClass.setText("");
            }
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

        TitledPane newClass = (TitledPane) createNewClass(c);

        newClass.setLayoutX(200*countOfClass);
        newClass.setLayoutY(200*countOfClass++);
        newClass.setCollapsible(false);
        newClass.setId(nameOfClass.getText()+"id");

        newClass.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nameOfActiveObject = newClass.getId();

                orgSceneX = event.getSceneX();
                orgSceneY = event.getSceneY();
                orgTranslateX = ((TitledPane)(event.getSource())).getTranslateX();
                orgTranslateY = ((TitledPane)(event.getSource())).getTranslateY();
            }
        });

        newClass.setOnMouseDragged(new EventHandler <MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                double offsetX = event.getSceneX() - orgSceneX;
                double offsetY = event.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;

                ((TitledPane)(event.getSource())).setTranslateX(newTranslateX);
                ((TitledPane)(event.getSource())).setTranslateY(newTranslateY);
            }
        });

        mainPane.getChildren().add(newClass);

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
