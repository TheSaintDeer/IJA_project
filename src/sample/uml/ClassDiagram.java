package sample.uml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassDiagram extends Element{

    private ObservableList<UMLClass> classes;
    private List<UMLClassifier> classifiers;
    private ArrayList<UMLRelationship> relationships;

    public ClassDiagram(String name) {
        super(name);
        classes = FXCollections.observableArrayList();
        classifiers = new ArrayList<>();
        relationships = new ArrayList<>();
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

    public UMLRelationship createRelat (String from, String to, String type) {
        UMLRelationship relat = new UMLRelationship(from, to, type);

        for (UMLRelationship r : relationships) {
            if (r == relat) {
                return null;
            }
        }

        relationships.add(relat);
        return relat;
    }

    public UMLClassifier findClass(String name) {

        if (classes.isEmpty()) return null;

        for (UMLClass c : classes) {
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

    public List<UMLRelationship> findAllRelat(String nameOfClass){
        List<UMLRelationship> relations = new ArrayList<>();

        for (UMLRelationship i: relationships) {
            if (i.getFromClass().equals(nameOfClass) || i.getToClass().equals(nameOfClass)) {
                relations.add(i);
            }
        }

        return relations;
    }

}
