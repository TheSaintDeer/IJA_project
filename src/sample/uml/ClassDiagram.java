package sample.uml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassDiagram extends Element{

    private ObservableList<UMLClass> classes;
    private List<UMLClassifier> classifiers;

    public ClassDiagram(String name) {
        super(name);
        classes = FXCollections.observableArrayList();
        classifiers = new ArrayList<>();
    }

    public UMLClass createClass(String name) {
        for (UMLClassifier c : classes) {
            if (Objects.equals(c.getName(), name)) return null;
        }
        UMLClass umlClass = new UMLClass(name);
        classes.add(umlClass);
        return umlClass;
    }

    public UMLClassifier classifierForName(String name) {

        UMLClassifier classifier = findClassifier(name);
        if (classifier == null) {
            classifier = UMLClassifier.forName(name);
        }
        classifiers.add(classifier);
        return classifier;
    }


    public UMLClassifier findClassifier(String name) {

        if (classifiers.isEmpty()) return null;

        for (UMLClassifier c : classifiers) {
            if (c.getName().equals(name)) return c;
        }
        return null;
    }


    public ObservableList<UMLClass> getAll() {
        return classes;
    }

    public UMLClass getLast() {
        return classes.get(classes.size()-1);
    }

}
