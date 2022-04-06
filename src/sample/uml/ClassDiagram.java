package sample.uml;

import java.util.ArrayList;
import java.lang.String;

public class ClassDiagram extends Element {

    ArrayList<UMLClass> array_Сlass;

    //Constructors

    public ClassDiagram(String name) {
        super(name);
        this.array_Сlass = new ArrayList<UMLClass>();
    }

    //Methods

    public UMLClass createClass(String name) {
        for (UMLClass i: array_Сlass) {
            if (i.name == name) {
                return null;
            }
        }

        UMLClass new_class = new UMLClass(name);
        new_class.isUserDefined = true;
        this.array_Сlass.add(new_class);
        return new_class;
    }

    public UMLClassifier classifierForName(String name) {
        for (UMLClass i: array_Сlass) {
            if (i.name == name) {
                return i;
            }
        }

        UMLClass new_classifier = new UMLClass(name);
        new_classifier.isUserDefined = false;
        this.array_Сlass.add(new_classifier);
        return new_classifier;
    }

    public UMLClassifier findClassifier(String name) {
        for (UMLClass i: array_Сlass) {
            if (i.name == name) {
                return i;
            }
        }

        return null;
    }

}
