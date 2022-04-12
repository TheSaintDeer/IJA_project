package sample.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.uml.UMLAttribute;
import sample.uml.UMLClass;
import sample.uml.UMLClassifier;

import java.io.IOException;

public class ClassController extends Main {

    public static int index;

    private UMLClass c;

    public ClassController(int i) {
        index = i;
    }

//    @FXML
//    private Button acceptClass;
//
//    @FXML
//    private Button addAttr;
//
//    @FXML
//    private Button addMetod;
//
//    @FXML
//    private TextField attr1;
//
//    @FXML
//    private TextField attr2;
//
//    @FXML
//    private Button changeClass;
//
//    @FXML
//    private Button deleteClass;
//
//    @FXML
//    private AnchorPane mainPane;
//
//    @FXML
//    private TextField metod1;
//
//    @FXML
//    private TextField metod2;
//
//    @FXML
//    private TextField nameClass;

    @FXML
    private TitledPane titledPane;

    @FXML
    private ListView listView;

    public void setExampleTextField(String text) {
        titledPane.setText(text);
    }
    @FXML
    void initialize() {

        titledPane.textProperty().bindBidirectional(c.nameProperty());
        listView.setItems(c.getAttributes());

        ContextMenu contextMenu = new ContextMenu();
        MenuItem add = new MenuItem("Add");
        MenuItem edit = new MenuItem("Edit");
        MenuItem delete = new MenuItem("Delete");
        contextMenu.getItems().add(add);
        contextMenu.getItems().add(edit);
        contextMenu.getItems().add(delete);
        listView.setContextMenu(contextMenu);
        listView.setEditable(true);




        add.setOnAction(new EventHandler<ActionEvent>() {
            int i = 0;
            @Override
            public void handle(ActionEvent actionEvent) {
                c.addAttribute(new UMLAttribute("+","newAttribute"+String.valueOf(i++),new UMLClassifier("type")));
            }

        });
//        edit.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Stage popupSave = new Stage();
//                popupSave.initModality(Modality.APPLICATION_MODAL);
////                popupSave.initOwner(SwitchingPass.stage);
//
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("/sample/fxml/class_edit_attribute.fxml"));
//                EditController edit = loader.getController();
//
//                loader.setController(edit);
//                Parent root = null;
//                try {
//                    root = loader.load();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//
//                Scene scene = new Scene(root);
//                popupSave.setScene(scene);
//                popupSave.showAndWait();
//
//                //after the popup closes, this will run, setting the label's text to the popup's test variable, which is public.
//                if (edit.newNameStr != null) {
//                    System.out.println(edit.newNameStr);
//                }
//            }
//        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UMLAttribute selected = (UMLAttribute) listView.getSelectionModel().getSelectedItem();
                System.out.println(selected);
                System.out.println(c.getAttributes());
                c.deleteAttribute(selected);

            }
        });


    }

    public void setClass(UMLClass c) {
        this.c = c;
    }
}
