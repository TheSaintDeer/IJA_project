package sample.uml;

import java.util.ArrayList;
import java.util.List;

public class UMLOperation extends UMLAttribute{

//    private
    private final List<UMLAttribute> attributeList;

    public UMLOperation(String visibility, String name, UMLClassifier type) {
        super(visibility, name, type);
        attributeList = new ArrayList<>();
    }

    public static UMLOperation create(String visibility, String name, UMLClassifier type, Object[] args) {
        UMLOperation op = new UMLOperation(visibility, name, type);

        for (Object attr : args) {
            if (attr.getClass() == UMLAttribute.class) {
                op.addArgument( (UMLAttribute) attr);
            }
        }
        return op;
    }

    public boolean addArgument(UMLAttribute arg) {
        attributeList.add(arg);
        return true;
    }

    public java.util.List<UMLAttribute> getArguments() {
        return attributeList;
    }

}
