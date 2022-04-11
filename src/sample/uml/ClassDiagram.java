package sample.uml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram extends Element{

    private List<UMLClass> classes;
    private List<UMLClassifier> classifiers;

    public ClassDiagram(String name) {
        super.rename(name);
        classes = new ArrayList<>();
    }

    public UMLClass createClass(String name) {
        for (UMLClassifier c : classes) {
            if (c.getName() == name) return null;
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
        for (UMLClassifier c : classifiers) {
            if (c.getName() == name) return c;
        }
        return null;
    }


    public ObservableList<UMLClass> getAll() {
        return FXCollections.observableList(classes);
    }

    public UMLClass getLast() {
        return classes.get(classes.size()-1);
    }

}
