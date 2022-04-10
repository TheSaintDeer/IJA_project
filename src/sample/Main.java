package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.uml.ClassDiagram;

import java.io.IOException;

public class Main extends Application {
    public static Scene scene;
    public static ClassDiagram classDiagram;
    public static int countOfClass = 0;

    @Override
    public void start(Stage stage) throws IOException {
        sceneSet(stage, "fxml/sample.");
    }

    private void sceneSet(Stage stage, String fxml) throws IOException {
        scene = new Scene(loadFXML(fxml));
        stage.setScene(scene);
        stage.setTitle("Database");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + "fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        classDiagram = new ClassDiagram("Diagram");
        launch();
    }
}