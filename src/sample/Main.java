package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.MainController;
import sample.parser.Parser;
import sample.uml.ClassDiagram;

import java.io.IOException;

public class Main extends Application {

    private static Scene scene;
    private static ClassDiagram classDiagram;
    private static Parser parser;
    public static int countOfClass = 0;

    @Override
    public void start(Stage stage) throws IOException {
        sceneSet(stage, "fxml/main.");
    }

    private void sceneSet(Stage stage, String fxml) throws IOException {
        scene = new Scene(loadFXML(fxml));
        stage.setScene(scene);
        stage.setTitle("Database");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + "fxml"));

        MainController mainController = new MainController(classDiagram);
        fxmlLoader.setController(mainController);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        classDiagram = new ClassDiagram("Diagram");
        parser = new Parser();
        try {
            parser.parse(new String[] {"src/sample/diagram.txt"}, classDiagram);
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch();
    }
}