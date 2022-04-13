package sample.uml;

/**
 * A class represents an attribute that has a name and a type.
 * It is derived (extended) from the Element class.
 * The attribute type is represented by the UMLClassifier class.
 * Can be used as a UML class attribute or an operation argument.
 */
public class UMLAttribute extends Element{

    private UMLClassifier type;
    private String visibility;

    /**
     * Creates an attribute instance.
     * @param visibility - the visibility of the attribute
     * @param name - the name of the attribute
     * @param type - the type of the attribute
     */
    public UMLAttribute(String visibility, String name, UMLClassifier type) {
        super(name);
        this.visibility = visibility;
        this.type = type;
    }

    /**
     * Provides information about the type of attribute.
     * @return Attribute type.
     */
    public UMLClassifier getType() {
        return type;
    }

    /**
     * Returns a string representing the state of the attribute in the form "name: type".
     * @return A string representing an attribute.
     */
    @Override
    public String toString() {
        return type+":"+getName();
    }


    public void setVisibility(String newVisibilityStr) {
        this.visibility = newVisibilityStr;
    }

    public void setType(String newTypeStr) {
        this.type = new UMLClassifier(newTypeStr);
    }
}
