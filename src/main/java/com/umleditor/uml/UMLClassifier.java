package com.umleditor.uml;

public class UMLClassifier extends Element{

    private final boolean isUserDefined;

    /**
     * Creates an instance of the Classifier class.
     * The instance is user-defined (it is part of the diagram).
     * @param name - The name of the classifier.
     */
    public UMLClassifier(String name) {
        super(name);
        String[] nonUserDefined = {"int", "boolean", "String", "void"};
        for (String s : nonUserDefined) {
            if (s.equals(name)) {
                isUserDefined = false;
                return;
            }
        }
        isUserDefined = true;
    }

    /**
     * The factory method for creating an instance of the Classifier class for the specified name.
     * The instance represents a classifier that is not modeled in the diagram.
     * @param name - The name of the classifier.
     * @return Created classifier.
     */
    public static UMLClassifier forName(String name) {
        return new UMLClassifier(name);
    }

    /**
     * Determines whether the object represents a classifier
     * that is modeled by the user in the diagram or not.
     * @return If the classifier is user-defined (it is directly part of the diagram),
     * it returns true. Otherwise false.
     */
    public boolean isUserDefined() {
        return this.isUserDefined;
    }

    /**
     * Returns a string representing the classifier in the form "name (userDefined)",
     * where userDefined is true or false.
     * @return A string representing the classifier.
     */
    @Override
    public String toString() {
        return super.getName();
    }
}
