package sample.uml;

public class UMLRelationship {

    public enum Relationship {ASSOCIACE, AGREGACE, KOMPOZICE, GENERALIZACE, ERROR}

    public String fromClass;
    public String toClass;
    public Relationship typeRelationship;

    //Constructors

    public UMLRelationship (String fromClass, String toClass, String relationship) {

        char[] tmp = relationship.toCharArray();

        if (tmp[0] == '<') {
            this.fromClass = toClass;
            this.toClass = fromClass;

            switch (tmp[1]) {
                case '-':
                    this.typeRelationship = Relationship.ASSOCIACE;
                    break;
                case '0':
                    this.typeRelationship = Relationship.AGREGACE;
                    break;
                case '1':
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
                case '0':
                    this.typeRelationship = Relationship.AGREGACE;
                    break;
                case '1':
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

    public String getToClass () {
        return this.toClass;
    }

    public String getFromClass () {
        return this.fromClass;
    }

}
