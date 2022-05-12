package com.umleditor.controller;

import com.umleditor.uml.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import com.umleditor.Main;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import com.google.gson.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

public class MainController extends Main {

    private String nameOfActiveObject = null;
    double SceneX, SceneY;
    double TranslateX, TranslateY;

    private Gson gson;
    private boolean classMode = true;

    @FXML
    private MenuItem open;
    @FXML
    private MenuItem saveAs;

    @FXML
    private MenuItem quit;
    @FXML
    private MenuItem newFile;
    @FXML
    private Button acceptClass;
    @FXML
    private Button deleteClass;
    @FXML
    private Button acceptRelat;

    @FXML
    private TextField nameOfActivateClass;

    @FXML
    private Button switchClass;
    @FXML
    private ListView<?> attributesOfSelectedClass;

    @FXML
    private Button createClass;

    @FXML
    private TextField nameOfSequenceRelat;

    @FXML
    private Button createRelation;

    @FXML
    private Button changeMode;

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
    private ClassDiagram diagram;
    @FXML
    public ClassController classController;

    public MainController(ClassDiagram d) {
        this.diagram = d;
    }

    void addNewSequenceClass(UMLClass c) {
        Label newLabel = new Label();
        newLabel.setLayoutX(50+400*countOfSequenceClass);
        newLabel.setLayoutY(20);
        newLabel.setPrefHeight(50);
        newLabel.setPrefWidth(150);
        newLabel.setText(c.getName());
        newLabel.setFont(new Font("Bold", 18));
        newLabel.setAlignment(Pos.CENTER);
        newLabel.setBorder(new Border(new BorderStroke(Color.SILVER, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        mainPane.getChildren().add(newLabel);

        Line newLine = new Line(125+400*countOfSequenceClass, 70, 125+400*countOfSequenceClass, 1200);
        mainPane.getChildren().add(newLine);

        countOfSequenceClass++;
    }

    /**
     * Creating new class in windows
     * @param c - class from diagram
     */
    void addNewClass(UMLClass c) {

        TitledPane newClass = (TitledPane) createNewClass(c);

        if (c.isAbstract()) {
            newClass.setStyle("-fx-text-fill: #009CFC");

        }
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
                attributesOfSelectedClass.setItems(c.getAttributesObservable());


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

                List<UMLRelation> relations = diagram.findAllRelat(nameOfActiveObject.substring(0, (nameOfActiveObject.length() - 2)));
                for (UMLRelation i: relations) {
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
        loader.setLocation(MainController.class.getResource("class_sample.fxml"));

        ClassController controller = new ClassController(countOfClass);
        controller.setUMLClass(c);
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
        if (classMode)
            typeRelat.setVisible(switcher);
        else
            nameOfSequenceRelat.setVisible(switcher);
    }

    /**
     * Clearing texts in TextField
     */
    public void clearField () {
        fromClass.setText("");
        toClass.setText("");
        nameOfSequenceRelat.setText("");
    }

    /**
     * Draw line of relation
     * @param r - relation from diagram
     */
    public void drawRelat(UMLRelation r) {

        String fromClass = r.fromClass + "id";
        String toClass = r.toClass + "id";

        double x1 = mainPane.lookup("#" + fromClass).getLayoutX() + mainPane.lookup("#" + fromClass).getTranslateX();
        double y1 = mainPane.lookup("#" + fromClass).getLayoutY() + mainPane.lookup("#" + fromClass).getTranslateY();
        double x2 = mainPane.lookup("#" + toClass).getLayoutX() + mainPane.lookup("#" + toClass).getTranslateX();
        double y2 = mainPane.lookup("#" + toClass).getLayoutY() + mainPane.lookup("#" + toClass).getTranslateY();
        double h1 = mainPane.lookup("#" + fromClass).getBoundsInLocal().getHeight();
        double w1 = mainPane.lookup("#" + fromClass).getBoundsInLocal().getWidth();
        double h2 = mainPane.lookup("#" + toClass).getBoundsInLocal().getHeight();
        double w2 = mainPane.lookup("#" + toClass).getBoundsInLocal().getWidth();
        Line line1;
        Line line2;
        Line line3;
        Line line4;
        Polygon poly;

        switch (r.getTypeRelationship()) {

            case ASSOCIATION:
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

            case AGGREGATION:
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

            case COMPOSITION:
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
                break;

            case GENERALIZATION:
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
                break;
        }


    }

    @FXML
    void initialize() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("All files", "*.*");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("TXT files", "*.txt");
        FileChooser.ExtensionFilter extFilter3 = new FileChooser.ExtensionFilter("JSON files", "*.json");
        fileChooser.getExtensionFilters().addAll(extFilter1,extFilter2,extFilter3);

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(ClassDiagram.class, new DiagramAdapter());
        gson = builder.create();

        diagramName.setText("Class diagram mode");

        renderClasses();

        createClass.setOnAction(event -> {
            nameOfClass.setVisible(true);
            acceptClass.setVisible(true);
        });

//activate class

        acceptClass.setOnAction(event -> {
            UMLClass newClass = diagram.createClass(nameOfClass.getText());

            if (nameOfClass.getText().isEmpty()) {
                terminalErrors.setText("Empty name");
                nameOfClass.setVisible(false);
                acceptClass.setVisible(false);

            } else if (newClass == null) {
                terminalErrors.setText("Class already exists");
                nameOfClass.setText("");

            } else {
                terminalErrors.setText("");
                nameOfClass.setVisible(false);
                acceptClass.setVisible(false);
                if (classMode)
                    addNewClass(newClass);
                else
                    addNewSequenceClass(newClass);
                nameOfClass.setText("");
            }

            System.out.println(diagram.getAll());
        });

        createRelation.setOnAction(event -> visibleObject(true));

        acceptRelat.setOnAction(event -> {

            if (classMode) {

                if (fromClass.getText().isEmpty()) {
                    terminalErrors.setText("Empty \"From class\"");
                    visibleObject(false);
                    typeRelat.setText("Type");
                    clearField();
                } else if (toClass.getText().isEmpty()) {
                    terminalErrors.setText("Empty \"To class\"");
                    visibleObject(false);
                    typeRelat.setText("Type");
                    clearField();
                } else if (typeRelat.getText().equals("Type")) {
                    terminalErrors.setText("Choose type!");
                    visibleObject(false);
                    clearField();
                } else {
                    visibleObject(false);

                    String type = "";
                    if (typeRelat.getText().equals("Association")) {
                        type = "ASSOCIATION";
                    } else if (typeRelat.getText().equals("Aggregation")) {
                        type = "AGGREGATION";
                    } else if (typeRelat.getText().equals("Composition")) {
                        type = "COMPOSITION";
                    } else if (typeRelat.getText().equals("Generalization")) {
                        type = "GENERALIZATION";
                    }

                    if (diagram.findClass(fromClass.getText()) == null || diagram.findClass(toClass.getText()) == null) {
                        terminalErrors.setText("One of the classes doesn't exist");
                    } else {
                        UMLRelation relat = diagram.createRelat(fromClass.getText(), toClass.getText(), type);
                        drawRelat(relat);
                        terminalErrors.setText("");
                    }

                    typeRelat.setText("Type");
                    clearField();
                }

            } else {
                visibleObject(false);
                if (fromClass.getText().isEmpty()) {
                    terminalErrors.setText("Empty \"From class\"");
                    visibleObject(false);
                    typeRelat.setText("Type");
                    clearField();
                } else if (toClass.getText().isEmpty()) {
                    terminalErrors.setText("Empty \"To class\"");
                    visibleObject(false);
                    typeRelat.setText("Type");
                    clearField();
                } else if (nameOfSequenceRelat.getText().isEmpty()) {
                    terminalErrors.setText("Empty \"Name of relation\"");
                    visibleObject(false);
                    clearField();
                } else {

                    ClassSequence newSequence = diagram.createNewSeqRelation(nameOfSequenceRelat.getText(), fromClass.getText(), toClass.getText());
                    if (newSequence == null) {
                        terminalErrors.setText("One of classes not exist");
                        visibleObject(false);
                        typeRelat.setText("Type");
                        clearField();
                    } else {
                        terminalErrors.setText("");

                        drawRelatSequence(newSequence);
                        clearField();
                    }
                }
            }
        });

        typeAs.setOnAction(event -> typeRelat.setText("Association"));

        typeAg.setOnAction(event -> typeRelat.setText("Aggregation"));

        typeKo.setOnAction(event -> typeRelat.setText("Composition"));

        typeGe.setOnAction(event -> typeRelat.setText("Generalization"));

        changeMode.setOnAction(event -> {
            if (classMode) {

                // actions when class mode is turned on (switch to sequence mode)
                classMode = false;
                diagramName.setText("Sequence diagram mode");
                createRelation.setText("Create sequence");
                changeMode.setText("Class Mode");
                switchClass.setVisible(true);
                nameOfActivateClass.setVisible(true);

                if (typeRelat.isVisible()) {
                    nameOfSequenceRelat.setVisible(true);
                    typeRelat.setVisible(false);
                }

                mainPane.getChildren().clear();

                countOfSequenceClass = 0;

                renderSequences();

            }else {

                // actions when sequence mode is turned on (switch to class mode)
                classMode = true;
                diagramName.setText("Class diagram mode");
                createRelation.setText("Create relation");
                changeMode.setText("Sequence Mode");
                switchClass.setVisible(false);
                nameOfActivateClass.setVisible(false);

                if (nameOfSequenceRelat.isVisible()) {
                    nameOfSequenceRelat.setVisible(false);
                    typeRelat.setVisible(true);
                }

                mainPane.getChildren().clear();

                countOfClass = 0;

                renderClasses();


            }
        });

        open.setOnAction(event -> {

            mainPane.getChildren().clear();


            countOfClass = 0;
            File file = fileChooser.showOpenDialog(mainPane.getScene().getWindow());
            if (file != null) {
                parse_file(file);
            }

        });

        quit.setOnAction(event -> {

            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();

        });



        newFile.setOnAction(event -> {

            mainPane.getChildren().clear();
            diagram.removeAllClasses();
            diagram.removeAllSequences();
            diagram.removeAllRelations();

            System.out.println(diagram);
        });

        saveAs.setOnAction(event -> {

            fileChooser.setInitialFileName(diagram.getName()+".json");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            File file = fileChooser.showSaveDialog(mainPane.getScene().getWindow());

            if (file != null) {
                String filePath = file.getPath();
                try {
                    Writer writer = new FileWriter(filePath);
                    writer.write(gson.toJson(diagram));
                    System.out.println(gson.toJson(diagram));
                    writer.close();
                } catch (Exception e) {
                    System.out.println((e.toString()));
                }
            } else {
                System.out.println(("file was not saved"));
            }

        });

        deleteClass.setOnAction(event -> {

            UMLClass selected = diagram.findClass(nameOfSelectedClass.getText());
            System.out.println(selected);
            diagram.getAll().remove(selected);
            mainPane.getChildren().removeAll(mainPane.lookupAll("#" + nameOfSelectedClass.getText() + "id"));

        });

    }

    private void renderSequences() {
        for (UMLClass c : diagram.getAll())
            addNewSequenceClass(c);
        countOfSequenceRelat = 0;
        for (ClassSequence s : diagram.getSequences()) {
            drawRelatSequence(s);
        }
    }

    private void drawRelatSequence(ClassSequence sequence) {


        int indexFrom = 0;
        int indexTo = 0;
        int i = 0;
        for (UMLClass c : diagram.getClasses()) {
            if (c.getName().equals(sequence.getNameClassFrom()))
                indexFrom = i;
            if (c.getName().equals(sequence.getNameClassTo()))
                indexTo = i;
            i++;
        }

        int move;
        int start;
        Label newLabel = new Label();

        if (indexFrom < indexTo){
            newLabel.setAlignment(Pos.CENTER_LEFT);
            start = 125+400*indexFrom;
            move = -10;
        }
        else {
            newLabel.setAlignment(Pos.CENTER_RIGHT);
            start = 125+400*indexTo;
            move = 10;
        }

        Line newLine = new Line(125+400*indexFrom, 120+60*countOfSequenceRelat, 125+400*indexTo, 120+60*countOfSequenceRelat);
        Polygon newPolygon = new Polygon(125+400*indexTo+move, 120+60*countOfSequenceRelat - 5, 125+400*indexTo+move, 120+60*countOfSequenceRelat + 5, 125+400*indexTo, 120+60*countOfSequenceRelat);

        newLabel.setLayoutX(start-move);
        newLabel.setLayoutY(80+60*countOfSequenceRelat);
        newLabel.setPrefHeight(50);
        newLabel.setPrefWidth(400*Math.abs(indexFrom - indexTo));
        newLabel.setText(sequence.getName());
        newLabel.setFont(new Font("Bold", 12));



        countOfSequenceRelat++;
        mainPane.getChildren().add(newLine);
        mainPane.getChildren().add(newPolygon);
        mainPane.getChildren().add(newLabel);
    }

    private void renderClasses() {

        for (UMLClass c : diagram.getAll())
            addNewClass(c);

        for (UMLRelation r : diagram.getAllRelations())
            drawRelat(r);


    }

    private void parse_file(File file) {


        try (Reader reader = new FileReader(file)) {

            // Convert JSON File to Java Object
            diagram = gson.fromJson(reader, ClassDiagram.class);

            // print diagram
            System.out.println(diagram);

            if (classMode) {
                renderClasses();
            }else {
                renderSequences();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

