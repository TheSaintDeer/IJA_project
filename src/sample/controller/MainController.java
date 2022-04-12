package sample.controller;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import sample.Main;
import sample.controller.MainController;
import sample.uml.ClassDiagram;
import sample.uml.UMLAttribute;
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
    private ListView<?> attributesOfSelectedClass;

    @FXML
    private Button createClass;

    @FXML
    private Button createRelat;

    @FXML
    private Label diagramName;

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
    private TextField nameOfSelectedAttribute;

    @FXML
    private TextField nameOfSelectedClass;

    @FXML
    private Button submitChangeButton;

    @FXML
    private TextField toClass;

    @FXML
    private MenuItem typeAg;

    @FXML
    private MenuItem typeAs;

    @FXML
    private MenuItem typeGe;

    @FXML
    private MenuItem typeKo;

    @FXML
    private MenuButton typeRelat;
    private ClassDiagram diagram;

    public MainController(ClassDiagram d) {
        this.diagram = d;
    }

    @FXML
    void initialize() {

        diagramName.setText(diagram.getName());

        for (UMLClass c : diagram.getAll()) {
            System.out.println("adding class: " + c.getName());
            addNewClass(c);
        }

        for (UMLRelationship r : diagram.getAllRelat()) {
            System.out.println("adding relat: " + r.getFromClass() + " " + r.typeRelationship + " " + r.toClass);
            drawRelat(r);
        }

        createClass.setOnAction(event -> {
            nameOfClass.setVisible(true);
            acceptClass.setVisible(true);

//            diagram.findClass("Command").getAttributeByName("hello:-string(true)").setName("bye");
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

        createRelat.setOnAction(event -> {
            visibleObject(true);
        });
        acceptRelat.setOnAction(event -> {
            visibleObject(false);

            String type = "";
            if (typeRelat.getText() == "ASSOCIACE") {
                type = "<---";
            } else if (typeRelat.getText() == "AGREGACE") {
                type = "<o--";
            } else if (typeRelat.getText() == "KOMPOZICE") {
                type = "<*--";
            } else if (typeRelat.getText() == "GENERALIZACE") {
                type = "<|--";
            }

            typeRelat.setText("Type relat");

            if (diagram.findClass(fromClass.getText()) != null && diagram.findClass(toClass.getText()) != null && type != "") {
                UMLRelationship relat = diagram.createRelat(fromClass.getText(), toClass.getText(), type);
                drawRelat(relat);
            }

            clearField();
        });

        typeAs.setOnAction(event -> {
            typeRelat.setText("ASSOCIACE");
        });

        typeAg.setOnAction(event -> {
            typeRelat.setText("AGREGACE");
        });

        typeKo.setOnAction(event -> {
            typeRelat.setText("KOMPOZICE");
        });

        typeGe.setOnAction(event -> {
            typeRelat.setText("GENERALIZACE");
        });

        submitChangeButton.setOnAction(event -> {
            UMLAttribute selected = (UMLAttribute) attributesOfSelectedClass.getSelectionModel().getSelectedItem();
            String className = nameOfSelectedClass.getText();
            diagram.findClass(className).deleteAttribute(selected);

        });
    }

    void addNewClass(UMLClass c) {

        TitledPane newClass = (TitledPane) createNewClass(c);

        newClass.setLayoutX(200* (countOfClass % 4));
        newClass.setLayoutY(250*(countOfClass++ /4));
        newClass.setCollapsible(false);
        newClass.setId(c.getName()+"id");

        newClass.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                nameOfSelectedClass.setText(c.getName());
                attributesOfSelectedClass.setItems(c.getAttributes());



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
                    drawRelat(i);
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

    public void drawRelat(UMLRelationship r) {

        String fromClass = r.fromClass + "id";
        String toClass = r.toClass + "id";

        double x1 = mainPane.lookup("#" +fromClass).getLayoutX() + mainPane.lookup("#" +fromClass).getTranslateX();
        double y1 = mainPane.lookup("#" +fromClass).getLayoutY() + mainPane.lookup("#" +fromClass).getTranslateY();
        double x2 = mainPane.lookup("#" +toClass).getLayoutX() + mainPane.lookup("#" +toClass).getTranslateX();
        double y2 = mainPane.lookup("#" +toClass).getLayoutY() + mainPane.lookup("#" +toClass).getTranslateY();
        double h1 = mainPane.lookup("#" +fromClass).getBoundsInLocal().getHeight();
        double w1 = mainPane.lookup("#" +fromClass).getBoundsInLocal().getWidth();
        double h2 = mainPane.lookup("#" +toClass).getBoundsInLocal().getHeight();
        double w2 = mainPane.lookup("#" +toClass).getBoundsInLocal().getWidth();
        Line line1;
        Line line2;
        Line line3;
        Line line4;
        Polygon poly;

        switch (r.getTypeRelationship()) {

            case ASSOCIACE:
                if (fromClass == toClass) {
                    line1 = new Line(x1, y1+h1/2, x1-30, y1+h1/2);
                    line2 = new Line(x2-30, y1+h1/2, x2-30, y2-30);
                    line3 = new Line(x1-30, y1-30, x2+w2/2, y1-30);
                    line4 = new Line(x2+w2/2, y1-30, x2+w2/2, y2+h2/2);
                    line1.setId((r.fromClass + r.toClass +"id"));
                    line2.setId((r.fromClass + r.toClass +"id"));
                    line3.setId((r.fromClass + r.toClass +"id"));
                    line4.setId((r.fromClass + r.toClass +"id"));
                    mainPane.getChildren().add(0, line1);
                    mainPane.getChildren().add(0, line2);
                    mainPane.getChildren().add(0, line3);
                    mainPane.getChildren().add(0, line4);
                } else {
                    line1 = new Line(x1 + w1 / 2, y1 + h1 / 2, x2 + w2 / 2, y1 + h1 / 2);
                    line2 = new Line(x2 + w2 / 2, y1 + h1 / 2, x2 + w2 / 2, y2 + h2 / 2);
                    line1.setId((r.fromClass + r.toClass + "id"));
                    line2.setId((r.fromClass + r.toClass + "id"));
                    mainPane.getChildren().add(0, line1);
                    mainPane.getChildren().add(0, line2);
                }
                break;

            case AGREGACE:
                double y3 = y1+h1+60;
                line1 = new Line(x1+w1/4*3, y1+h1/2, x1+w1/4*3, y3);
                line2 = new Line(x2+w2/2, y3, x1+w1/4*3, y3);
                line3 = new Line(x2+w2/2, y2+h2/2, x2+w2/2, y3);
                poly = new Polygon(x1+w1/4*3, y1+h1, x1+w1/4*3-6, y1+h1+11, x1+w1/4*3, y1+h1+22, x1+w1/4*3+6, y1+h1+11);
                poly.setFill(Color.WHITE);
                poly.setStroke(Color.BLACK);
                line1.setId((r.fromClass + r.toClass +"id"));
                line2.setId((r.fromClass + r.toClass +"id"));
                line3.setId((r.fromClass + r.toClass +"id"));
                poly.setId((r.fromClass + r.toClass +"id"));
                mainPane.getChildren().add(0, line1);
                mainPane.getChildren().add(0, line2);
                mainPane.getChildren().add(0, line3);
                mainPane.getChildren().add(0, poly);
                break;

            case KOMPOZICE:
                y3 = y1+h1+40;
                line1 = new Line(x1+w1/4, y1+h1/2, x1+w1/4, y3);
                line2 = new Line(x2+w2/2, y3, x1+w1/4, y3);
                line3 = new Line(x2+w2/2, y2+h2/2, x2+w2/2, y3);
                poly = new Polygon(x1+w1/4, y1+h1, x1+w1/4-6, y1+h1+11, x1+w1/4, y1+h1+22, x1+w1/4+6, y1+h1+11);
                poly.setStroke(Color.BLACK);
                line1.setId((r.fromClass + r.toClass +"id"));
                line2.setId((r.fromClass + r.toClass +"id"));
                line3.setId((r.fromClass + r.toClass +"id"));
                poly.setId((r.fromClass + r.toClass +"id"));
                mainPane.getChildren().add(0, poly);
                mainPane.getChildren().add(0, line1);
                mainPane.getChildren().add(0, line2);
                mainPane.getChildren().add(0, line3);

            case GENERALIZACE:
                y3 = y1+h1+50;
                line1 = new Line(x1+w1/2, y1+h1/2, x1+w1/2, y3);
                line2 = new Line(x2+w2/2, y3, x1+w1/2, y3);
                line3 = new Line(x2+w2/2, y2+h2/2, x2+w2/2, y3);
                poly = new Polygon(x1+w1/2, y1+h1, x1+w1/2-10, y1+h1+20,x1+w1/2+10, y1+h1+20);
                poly.setFill(Color.WHITE);
                poly.setStroke(Color.BLACK);
                line1.setId((r.fromClass + r.toClass +"id"));
                line2.setId((r.fromClass + r.toClass +"id"));
                line3.setId((r.fromClass + r.toClass +"id"));
                poly.setId((r.fromClass + r.toClass +"id"));
                mainPane.getChildren().add(0, poly);
                mainPane.getChildren().add(0, line1);
                mainPane.getChildren().add(0, line2);
                mainPane.getChildren().add(0, line3);
        }


    }
}
