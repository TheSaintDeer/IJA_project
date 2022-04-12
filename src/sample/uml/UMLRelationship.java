package sample.uml;

public class UMLRelationship {


    public enum Relationship {ASSOCIACE, AGREGACE, KOMPOZICE, GENERALIZACE, ERROR}

    public String fromClass;
    public String toClass;
    public Relationship typeRelationship;

    //Constructors

    /**
     * Constructor for creating a relation.
     * @param fromClass Where is the relation coming from
     * @param toClass Where does the relation go
     * @param relationship What type of relation
     */
    public UMLRelationship (String fromClass, String toClass, String relationship) {

        char[] tmp = relationship.toCharArray();

        if (tmp[0] == '<') {
            this.fromClass = toClass;
            this.toClass = fromClass;

            switch (tmp[1]) {
                case '-':
                    this.typeRelationship = Relationship.ASSOCIACE;
                    break;
                case 'o':
                    this.typeRelationship = Relationship.AGREGACE;
                    break;
                case '*':
                    this.typeRelationship = Relationship.KOMPOZICE;
                    break;
                case '|':
                    this.typeRelationship = Relationship.GENERALIZACE;
                    break;
                default:
                    this.typeRelationship = Relationship.ERROR;
            }

        } else if (tmp[tmp.length-1] == '>') {
            this.fromClass = fromClass;
            this.toClass = toClass;

            switch (tmp[tmp.length-2]) {
                case '-':
                    this.typeRelationship = Relationship.ASSOCIACE;
                    break;
                case 'o':
                    this.typeRelationship = Relationship.AGREGACE;
                    break;
                case '*':
                    this.typeRelationship = Relationship.KOMPOZICE;
                    break;
                case '|':
                    this.typeRelationship = Relationship.GENERALIZACE;
                    break;
                default:
                    this.typeRelationship = Relationship.ERROR;
            }
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
}
