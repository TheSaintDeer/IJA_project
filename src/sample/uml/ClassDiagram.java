package sample.uml;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram extends Element{

    private List<UMLClassifier> classifiers;


    public ClassDiagram(String name) {
        super.rename(name);
        classifiers = new ArrayList<>();
    }

    public UMLClass createClass(String name) {
        for (UMLClassifier c : classifiers) {
            if (c.getName() == name) return null;
        }
        UMLClass umlClass = new UMLClass(name);
        classifiers.add(umlClass);
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


}
