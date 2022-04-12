package sample.controller;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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

    private int countOfRow = 0;

    @FXML
    private Button acceptRelat;

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
    private Label labelFrom;

    @FXML
    private Label labelTo;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField toClassX;

    @FXML
    private TextField toClassY;

    @FXML
    private ListView testListView;

    private ClassDiagram diagram;

    private int i = 0;

    public MainController(ClassDiagram d) {
        this.diagram = d;
    }

    @FXML
    void initialize() {
//        i = 0;
//        testListView.setItems(diagram.getAll());

        for (UMLClass c : diagram.getAll()) {
            addNewClass(c);
        }

        createClass.setOnAction(event -> {
            UMLClass newClass = diagram.createClass("Test " + i++);
            addNewClass(newClass);
        });

        closeWindow.setOnAction(event -> {
            closeWindow.getScene().getWindow().hide();
        });
        createRelat.setOnAction(event -> {
            visibleObject(true);
        });
        acceptRelat.setOnAction(event -> {
            visibleObject(false);
            Node fromC = getNodeByRowColumnIndex(Integer.parseInt(fromClassY.getText()), Integer.parseInt(fromClassX.getText()));
            Node toC = getNodeByRowColumnIndex(Integer.parseInt(toClassY.getText()), Integer.parseInt(toClassX.getText()));
            drawNewLine(fromC, toC);

            clearField();
        });
    }

    void addNewClass() {
        checkRow();
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

    private void visibleObject(boolean switcher) {
        labelFrom.setVisible(switcher);
        labelTo.setVisible(switcher);
        fromClassX.setVisible(switcher);
        fromClassY.setVisible(switcher);
        toClassX.setVisible(switcher);
        toClassY.setVisible(switcher);
        acceptRelat.setVisible(switcher);
    }

    public Node getNodeByRowColumnIndex (final int row, final int column) {
        Node result = null;
        ObservableList<Node> childrens = grPane.getChildren();

        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    public void clearField () {
        fromClassX.setText("");
        fromClassY.setText("");
        toClassX.setText("");
        toClassY.setText("");
    }

    private void drawNewLine (Node from, Node to) {
        Line line = new Line();
        line.setStartX(from.getLayoutX() + (grPane.getColumnConstraints().get(Integer.parseInt(fromClassX.getText())).getPrefWidth() / 2) + 5);
        line.setStartY(from.getLayoutY() + grPane.getRowConstraints().get(Integer.parseInt(fromClassY.getText())).getPrefHeight());
        line.setEndX(to.getLayoutX() + (grPane.getColumnConstraints().get(Integer.parseInt(toClassX.getText())).getPrefWidth() / 2) + 5);
        line.setEndY(to.getLayoutY());


        mainPane.getChildren().add(line);
//        grPane.add(line, 0, 0);
    }

    public void checkRow() {
        if (countOfRow < (countOfClass / 4)) {
            countOfRow++;
            RowConstraints row = new RowConstraints();
            row.setMinHeight(240);
            row.setFillHeight(false);
            grPane.getRowConstraints().addAll(row);
        }
    }


}
