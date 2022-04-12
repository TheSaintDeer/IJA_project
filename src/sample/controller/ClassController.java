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

        /**
         * Add new attribute
         */
        add.setOnAction(new EventHandler<ActionEvent>() {
            int i = 0;
            @Override
            public void handle(ActionEvent actionEvent) {
                c.addAttribute(new UMLAttribute("+","newAttribute"+String.valueOf(i++),new UMLClassifier("type")));
            }

        });

        /**
         * Edit attribute
         */
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage popupSave = new Stage();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/fxml/class_edit_attribute.fxml"));
                EditController edit = new EditController();
                loader.setController(edit);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                Scene scene = new Scene(root);
                popupSave.setScene(scene);
                popupSave.showAndWait();

                //after the popup closes, this will run, setting the label's text to the popup's test variable, which is public.

                UMLAttribute selected = (UMLAttribute) listView.getSelectionModel().getSelectedItem();

                if (selected != null && edit.newNameStr != null && edit.newNameStr != "") {
                    selected.setName(edit.newNameStr);
                }

            }
        });

        /**
         * Delete attribute
         */
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

    /**
     * Set class
     * @param c input class
     */
    public void setClass(UMLClass c) {
        this.c = c;
    }
}
