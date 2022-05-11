package com.umleditor.uml;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A class represents a named element (thing) that can be part of any part of a diagram.
 */

public class Element {

    /**
     * Creates an instance with the specified name.
     * @param name - the name of the element
     */

    public Element(String name) {
        setName(name);
    }

    /**
     * Returns the name of the element.
     * @return name - the name of the element
     */

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Renames an element.
     * @param name - new name of the element
     */

    public void setName(String name) {
        this.name.set(name);
    }

    private StringProperty name = new SimpleStringProperty();



}
