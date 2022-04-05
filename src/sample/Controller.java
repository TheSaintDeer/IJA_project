package sample;

        import java.net.URL;
        import java.util.ResourceBundle;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createTable;

    @FXML
    private Button dropTable;

    @FXML
    private Button selectTable;

    @FXML
    void initialize() {
        createTable.setOnAction(event -> {
            System.out.println("Create Table");
        });

        dropTable.setOnAction(event -> {
            System.out.println("Drop Table");
        });

        selectTable.setOnAction(event -> {
            System.out.println("Select Table");
        });

    }

}
