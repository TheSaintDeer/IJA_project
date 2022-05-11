package com.umleditor.uml;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The class (its instance) represents a class model from UML. Extends the UMLClassifier class. Contains a list of
 * attributes and operations (methods). The class can be abstract.
 */

public class UMLClass extends UMLClassifier {

    private boolean isAbstract;

//    private StringProperty className = new SimpleStringProperty();
    private final ObservableList<UMLAttribute> attributes;

    /**
     * Creates an instance representing a class model from UML. The class is not abstract.
     * @param name - Name of the class (classifier).
     */
    public UMLClass(String name) {
        super(name);
        this.isAbstract = false;
        attributes = FXCollections.observableArrayList();
    }

    /**
     * Test whether the object represents an abstract class model.
     * @return If the class is abstract, it returns true. Otherwise it returns false.
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     * Changes the object information to see if it represents an abstract class.
     * @param isAbstract - Zda se jedná o abstraktní třídu nebo ne.
     */
    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    /**
     * Inserts an attribute into the UML class model. The attribute is inserted at the end of the list (last item).
     * If the class already contains an attribute of the same name, it does nothing.
     * @param attr - Inserted attribute.
     * @return Action successful (returns if successful, otherwise false).
     */
    public boolean addAttribute(UMLAttribute attr) {
        for (UMLAttribute a: attributes) {
            if (a.getName().equals(attr.getName())) return false;
        }
        attributes.add(attr);
        return true;
    }

    /**
     * Returns the position of the attribute in the attribute list. The position is indexed from 0.
     * If the class does not contain the attribute, it returns -1.
     * @param attr - Searched attribute.
     * @return Attribute position.
     */
    public int getAttrPosition(UMLAttribute attr) {
        for (UMLAttribute a: attributes) {
            if (a.getName().equals(attr.getName())) return attributes.indexOf(a);
        }
        return -1;
    }

    /**
     * Returns the position of the attribute with a specific name in the attribute list. The position is indexed from 0.
     * If the class does not contain the attribute, it returns -1.
     * @param name - the name of the attribute
     * @return Attribute position.
     */
    public UMLAttribute getAttributeByName(String name) {
        for (UMLAttribute a : attributes) {
            if (a.getName().equals(name)) return a;
        }
        return null;
    }

    /**
     * Moves the position of the attribute to the newly specified one. The position is indexed from the value 0.
     * If the class does not contain the given attribute, it does nothing and returns -1.
     * When you move to the pos position, all existing items (attributes) move one position
     * to the right from the pos position (inclusive).
     * @param attr - Moved attribute.
     * @param pos - new position
     * @return Operation success.
     */
    public int moveAttrAtPosition(UMLAttribute attr, int pos) {

        if (getAttrPosition(attr) < 0) return -1;
        attributes.add(pos,attributes.remove(getAttrPosition(attr)));
        return pos;
    }

    /**
     * Returns an unmodifiable list of attributes. Can be used to display class attributes.
     * @return Non-modifiable attribute list.
     */
    public ObservableList getAttributes() {
        return attributes;
    }

    /**
     * Delete an attribute into the UML class model.
     * @param selected - Deleted attribute.
     */
    public void deleteAttribute(UMLAttribute selected) {
        attributes.remove(selected);
    }

}
