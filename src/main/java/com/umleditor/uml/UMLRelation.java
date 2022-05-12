package com.umleditor.uml;

public class UMLRelation {


    public enum Relationship {ASSOCIATION, AGGREGATION, COMPOSITION, GENERALIZATION, ERROR}

    public String fromClass;
    public String toClass;
    public Relationship typeRelationship;

    //Constructors

    /**
     * Constructor for creating a relation.
     * @param fromClass Where is the relation coming from
     * @param toClass Where does the relation go
     * @param type What type of relation
     */
    public UMLRelation(String fromClass, String toClass, String type) {

        this.fromClass = fromClass;
        this.toClass = toClass;
        switch (type) {
            case "AGGREGATION":
                this.typeRelationship = Relationship.AGGREGATION;
                break;
            case "COMPOSITION":
                this.typeRelationship = Relationship.COMPOSITION;
                break;
            case "GENERALIZATION":
                this.typeRelationship = Relationship.GENERALIZATION;
                break;
            case "ASSOCIATION":
            default:
                this.typeRelationship = Relationship.ASSOCIATION;
        }


    }


    // Methods

    /**
     * Find out the recipient
     * @return Class recipient
     */
    public String getToClass () {
        return this.toClass;
    }

    /**
     * Find out the sender
     * @return Class sender
     */
    public String getFromClass () {
        return this.fromClass;
    }

    /**
     * Find out the relation
     * @return Type of relation
     */
    public Relationship getTypeRelationship () {
        return this.typeRelationship;
    }

    @Override
    public String toString() {
        return String.format("{from:%s;to:%s;type:%s;}", this.fromClass,this.toClass,this.typeRelationship);
    }
}
