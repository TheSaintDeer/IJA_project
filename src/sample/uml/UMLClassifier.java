package sample.uml;

import java.lang.String;

public class UMLClassifier extends Element {

    boolean isUserDefined = false;

    //Constructors
    public UMLClassifier(String name) {
        super(name);
        isUserDefined = true;
    }

    public UMLClassifier(String name, boolean isUserDefined) {
        super(name);
        this.isUserDefined = isUserDefined;
    }

    //Methods

    public static UMLClassifier forName(String name) {
       return new UMLClassifier(name, false);
    }
    
    public boolean isUserDefined() {
        return isUserDefined;
    }

    public String toString() {
        String ret = name + "(" + isUserDefined + ")";
        return  ret;
    }
}
