package com.umleditor.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.umleditor.Main;
import com.umleditor.uml.UMLAttribute;
import com.umleditor.uml.UMLClass;
import com.umleditor.uml.UMLClassifier;

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

    @FXML
    void initialize() {

        titledPane.textProperty().bindBidirectional(c.nameProperty());
        listView.setItems(c.getAttributesObservable());

        ContextMenu contextMenu = new ContextMenu();
        MenuItem add = new MenuItem("Add attribute");
        MenuItem edit_classname = new MenuItem("Edit classname");
        MenuItem edit_attribute = new MenuItem("Edit attribute");
        MenuItem delete_attribute = new MenuItem("Delete attribute");
        MenuItem changeAbstract = new MenuItem("Change abstraction");
        contextMenu.getItems().add(add);
        contextMenu.getItems().add(edit_attribute);
        contextMenu.getItems().add(edit_classname);
        contextMenu.getItems().add(delete_attribute);
        contextMenu.getItems().add(changeAbstract);
        listView.setContextMenu(contextMenu);
        listView.setEditable(true);

        /**
         * Add new attribute
         */
        add.setOnAction(new EventHandler<>() {
            int i = 0;
            @Override
            public void handle(ActionEvent actionEvent) {
                c.addAttribute(new UMLAttribute("+", "newAttribute" + i++, new UMLClassifier("type")));
            }

        });

        edit_classname.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage popupSave = new Stage();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("class_edit_classname.fxml"));
                EditClassnameController edit = new EditClassnameController();
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

                //after the popup closes, this will run
                c.setName(edit.newClassNameStr);
            }
        });

        /**
         * Edit attribute
         */
        edit_attribute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage popupSave = new Stage();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("class_edit_attribute.fxml"));
                EditAttributeController edit = new EditAttributeController();
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

                //after the popup closes, this will run
                UMLAttribute selected = (UMLAttribute) listView.getSelectionModel().getSelectedItem();

                if (selected != null && edit.newNameStr != null && !edit.newNameStr.equals("")) {
                    selected.setVisibility(edit.newVisibilityStr);
                    selected.setName(edit.newNameStr);
                    selected.setType(edit.newTypeStr);
                }

                listView.getSelectionModel().clearSelection();

            }
        });

        /**
         * Delete attribute
         */
        delete_attribute.setOnAction(event -> {
            UMLAttribute selected = (UMLAttribute) listView.getSelectionModel().getSelectedItem();
            System.out.println(selected);
            System.out.println(c.getAttributesObservable());
            c.deleteAttribute(selected);
        });

        changeAbstract.setOnAction(event -> {
            if (c.isAbstract()) {
                c.setAbstract(false);
                titledPane.setStyle(null);

            }else {
                c.setAbstract(true);
                titledPane.setStyle("-fx-text-fill: #009CFC");
            }

        });

    }

    /**
     * Set class
     * @param c input class
     */
    public void setUMLClass(UMLClass c) {
        this.c = c;
    }

}
