package sample.uml;

public class UMLAttribute extends Element{

    private final UMLClassifier type;
    private final String visibility;

    public UMLAttribute(String visibility, String name, UMLClassifier type) {
        super(name);
        this.visibility = visibility;
        this.type = type;
    }

    public UMLClassifier getType() {
        return type;
    }

    public String toString() {
        return getName()+":"+type;
    }


}
