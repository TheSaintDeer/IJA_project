package sample.controller;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import sample.Main;
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
    private Label nameOfSelectedClass;

    @FXML
    private Label terminalErrors;

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
    private final ClassDiagram diagram;

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
            System.out.println("adding relation: " + r.getFromClass() + " " + r.typeRelationship + " " + r.toClass);
            drawRelat(r);
        }

        createClass.setOnAction(event -> {
            nameOfClass.setVisible(true);
            acceptClass.setVisible(true);
        });

        acceptClass.setOnAction(event -> {
            UMLClass newClass = diagram.createClass(nameOfClass.getText());

            if (nameOfClass.getText().isEmpty()) {
                terminalErrors.setText("Empty name");
                nameOfClass.setVisible(false);
                acceptClass.setVisible(false);

            } else if (newClass == null) {
                terminalErrors.setText("Class already exists");

            } else {
                terminalErrors.setText("");
                nameOfClass.setVisible(false);
                acceptClass.setVisible(false);
                addNewClass(newClass);
                nameOfClass.setText("");
            }
        });

        createRelat.setOnAction(event -> visibleObject(true));

        acceptRelat.setOnAction(event -> {
            if (fromClass.getText().isEmpty()) {
                terminalErrors.setText("Empty \"From class\"");
                visibleObject(false);
                typeRelat.setText("Type relat");
                clearField();
            } else if (toClass.getText().isEmpty()) {
                terminalErrors.setText("Empty \"To class\"");
                visibleObject(false);
                typeRelat.setText("Type relat");
                clearField();
            } else if (typeRelat.getText().equals("Type relat")) {
                terminalErrors.setText("Don't choose type");
                visibleObject(false);
                clearField();
            } else {
                visibleObject(false);
                typeRelat.setText("Type relat");
                clearField();

                String type = "";
                if (typeRelat.getText().equals("Association")) {
                    type = "<---";
                } else if (typeRelat.getText().equals("Aggregation")) {
                    type = "<o--";
                } else if (typeRelat.getText().equals("Composition")) {
                    type = "<*--";
                } else if (typeRelat.getText().equals("Generalization")) {
                    type = "<|--";
                }
                if (diagram.findClass(fromClass.getText()) == null  || diagram.findClass(toClass.getText()) == null) {
                    terminalErrors.setText("Don't exist one of classes");
                } else {
                    UMLRelationship relat = diagram.createRelat(fromClass.getText(), toClass.getText(), type);
                    drawRelat(relat);
                    terminalErrors.setText("");
                }
            }
        });

        typeAs.setOnAction(event -> typeRelat.setText("Association"));

        typeAg.setOnAction(event -> typeRelat.setText("Aggregation"));

        typeKo.setOnAction(event -> typeRelat.setText("Composition"));

        typeGe.setOnAction(event -> typeRelat.setText("Generalization"));

    }

    /**
     * Creating new class in windows
     * @param c - class from diagram
     */
    void addNewClass(UMLClass c) {

        TitledPane newClass = (TitledPane) createNewClass(c);

        newClass.setLayoutX(50+300*(countOfClass % 3));
        newClass.setLayoutY(50+250*(countOfClass / 3));
        countOfClass++;
        newClass.setCollapsible(false);
        newClass.setId(c.getName()+"id");

        /**
         * Action when mouse pressed on class
         */
        newClass.setOnMousePressed(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {

                nameOfSelectedClass.setText(c.getName());
                attributesOfSelectedClass.setItems(c.getAttributes());


                nameOfActiveObject = newClass.getId();
                SceneX = event.getSceneX();
                SceneY = event.getSceneY();
                TranslateX = ((TitledPane) (event.getSource())).getTranslateX();
                TranslateY = ((TitledPane) (event.getSource())).getTranslateY();
            }
        });

        /**
         * Action when mouse dragging class
         */
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

    /**
     * Create and load loader
     */
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

    /**
     * makes objects visible or invisible
     * @param switcher boolean
     */
    private void visibleObject(boolean switcher) {
        labelFrom.setVisible(switcher);
        labelTo.setVisible(switcher);
        fromClass.setVisible(switcher);
        toClass.setVisible(switcher);
        acceptRelat.setVisible(switcher);
        typeRelat.setVisible(switcher);
    }

    /**
     * Clearing texts in TextField
     */
    public void clearField () {
        fromClass.setText("");
        toClass.setText("");
    }

    /**
     * Draw line of relation
     * @param r - relation from diagram
     */
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
                if (fromClass.equals(toClass)) {
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
                poly = new Polygon(x1+w1/2, y1+h1, x1+w1/2-5, y1+h1+20,x1+w1/2+5, y1+h1+20);
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
