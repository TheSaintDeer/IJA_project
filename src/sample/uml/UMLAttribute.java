package sample.uml;

public class UMLAttribute extends Element{

    private UMLClassifier type;

    public UMLAttribute(String name, UMLClassifier type) {
        super.rename(name);
        this.type = type;
    }

    public UMLClassifier getType() {
        return type;
    }

    public String toString() {
        return getName()+":"+type;
    }


}
