package sample.uml;

import java.lang.String;

public class UMLAttribute extends Element{

    UMLClassifier type;

    //Constructors

    public UMLAttribute(String name, UMLClassifier type) {
        super(name);
        this.type = type;
    }

    //Methods

    public UMLClassifier getType() {
        return this.type;
    }

    public String toString() {
        String ret = name + ":" + type.toString();
        return ret;
    }
    
}
