package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/main.fxml"));

        MainController mainController = new MainController(classDiagram);
        fxmlLoader.setController(mainController);

        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("UML Editor");
        stage.show();
    }

    public static void main(String[] args) {
        classDiagram = new ClassDiagram("Class Diagram");
        parser = new Parser();
        try {
            parser.parse(args, classDiagram);
        } catch (Exception e) {
//            e.printStackTrace();

        }

        launch();
    }
}