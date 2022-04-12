package sample.uml;

import java.util.ArrayList;
import java.util.List;

public class UMLClassifier extends Element{

    private boolean isUserDefined;
    private String[] nonUserDefined = {"int","boolean","String","void"};

    public UMLClassifier(String name) {
        super(name);
        for (String s : nonUserDefined) {
            if (s == name) {
                isUserDefined = false;
                return;
            }
        }
        isUserDefined = true;
    }

    public UMLClassifier(String name, boolean isUserDefined) {
        super(name);
        this.isUserDefined = isUserDefined;
    }

    public static UMLClassifier forName(String name) {
        return new UMLClassifier(name);
    }

    public boolean isUserDefined() {
        return this.isUserDefined;
    }

    public String toString() {
        return super.getName()+"("+isUserDefined+")";
    }
}
