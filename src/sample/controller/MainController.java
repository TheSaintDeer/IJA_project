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
import sample.uml.UMLRelationship;

import java.io.IOException;
import java.util.List;

public class MainController extends Main {

    private String nameOfActiveObject = null;
    double SceneX, SceneY;
    double TranslateX, TranslateY;

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
            diagram.createRelat(fromClass.getText(), toClass.getText(), "<---");
            drawRelat(fromClass.getText(), toClass.getText());
            clearField();
        });
    }

    void addNewClass(UMLClass c) {

        TitledPane newClass = (TitledPane) createNewClass(c);

        newClass.setLayoutX(200*countOfClass);
        newClass.setLayoutY(200*countOfClass++);
        newClass.setCollapsible(false);
        newClass.setId(c.getName()+"id");

        newClass.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nameOfActiveObject = newClass.getId();

                SceneX = event.getSceneX();
                SceneY = event.getSceneY();
                TranslateX = ((TitledPane)(event.getSource())).getTranslateX();
                TranslateY = ((TitledPane)(event.getSource())).getTranslateY();
            }
        });

        newClass.setOnMouseDragged(new EventHandler <MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double offsetX = event.getSceneX() - SceneX;
                double offsetY = event.getSceneY() - SceneY;
                double newTranslateX = TranslateX + offsetX;
                double newTranslateY = TranslateY + offsetY;

                ((TitledPane)(event.getSource())).setTranslateX(newTranslateX);
                ((TitledPane)(event.getSource())).setTranslateY(newTranslateY);

                List<UMLRelationship> relations = diagram.findAllRelat(nameOfActiveObject.substring(0, (nameOfActiveObject.length() - 2)));
                for (UMLRelationship i: relations) {
                    mainPane.getChildren().removeAll(mainPane.lookupAll("#" + i.getFromClass() + i.getToClass() +"id"));
                    mainPane.getChildren().removeAll(mainPane.lookupAll("#" + i.getToClass() + i.getFromClass() +"id"));
                    drawRelat(i.getFromClass(), i.getToClass());
                }

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

    public void drawRelat(String from, String to) {
        String fromClass = from + "id";
        String toClass = to + "id";

        double x1 = mainPane.lookup("#" +fromClass).getLayoutX() + mainPane.lookup("#" +fromClass).getTranslateX();
        double y1 = mainPane.lookup("#" +fromClass).getLayoutY() + mainPane.lookup("#" +fromClass).getTranslateY();
        double x2 = mainPane.lookup("#" +toClass).getLayoutX() + mainPane.lookup("#" +toClass).getTranslateX();
        double y2 = mainPane.lookup("#" +toClass).getLayoutY() + mainPane.lookup("#" +toClass).getTranslateY();
        double h1 = mainPane.lookup("#" +fromClass).getBoundsInLocal().getHeight();
        double w1 = mainPane.lookup("#" +fromClass).getBoundsInLocal().getWidth();
        double h2 = mainPane.lookup("#" +toClass).getBoundsInLocal().getHeight();
        double w2 = mainPane.lookup("#" +toClass).getBoundsInLocal().getWidth();

        Line line1 = new Line(x1+w1/2, y1+h1/2, x2+w2/2, y1+h1/2);
        Line line2 = new Line(x2+w2/2, y1+h1/2, x2+w2/2, y2+h2/2);
        line1.setId((from+ to +"id"));
        line2.setId((from+ to + "id"));
        mainPane.getChildren().add(0, line1);
        mainPane.getChildren().add(0, line2);
    }
}
