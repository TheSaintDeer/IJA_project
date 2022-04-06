package sample.uml;

import java.util.ArrayList;
import java.lang.String;
import java.util.Objects;

public class ClassDiagram extends Element {

    ArrayList<UMLClass> array_Class;

    //Constructors

    public ClassDiagram(String name) {
        super(name);
        this.array_Class = new ArrayList<UMLClass>();
    }

    //Methods

    public UMLClass createClass(String name) {
        for (UMLClass i: array_Class) {
            if (Objects.equals(i.name, name)) {
                return null;
            }
        }

        UMLClass new_class = new UMLClass(name);
        new_class.isUserDefined = true;
        this.array_Class.add(new_class);
        return new_class;
    }

    public UMLClassifier classifierForName(String name) {
        for (UMLClass i: array_Class) {
            if (Objects.equals(i.name, name)) {
                return i;
            }
        }

        UMLClass new_classifier = new UMLClass(name);
        new_classifier.isUserDefined = false;
        this.array_Class.add(new_classifier);
        return new_classifier;
    }

    public UMLClassifier findClassifier(String name) {
        for (UMLClass i: array_Class) {
            if (Objects.equals(i.name, name)) {
                return i;
            }
        }

        return null;
    }

}
