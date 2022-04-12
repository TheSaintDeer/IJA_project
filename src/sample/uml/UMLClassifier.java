package sample.uml;

public class UMLClassifier extends Element{

    private final boolean isUserDefined;

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

    public UMLClassifier(String name, boolean isUserDefined) {
        super(name);
        this.isUserDefined = isUserDefined;
    }

    public static UMLClassifier forName(String name) {
        return new UMLClassifier(name);
    }

    public boolean isUserDefined() {
        return this.isUserDefined;
    }

    public String toString() {
        return super.getName()+"("+isUserDefined+")";
    }
}
