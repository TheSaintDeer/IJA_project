package sample.uml;

import java.util.ArrayList;
import java.util.List;

public class UMLOperation extends UMLAttribute{

//    private
    private List<UMLAttribute> attributeList;

    public UMLOperation(String name, UMLClassifier type) {
        super(name, type);
        attributeList = new ArrayList<>();
    }

    public static UMLOperation create(String name, UMLClassifier type, UMLAttribute... args) {
        UMLOperation op = new UMLOperation(name, type);
        for (UMLAttribute attr : args) {
            op.addArgument(attr);
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
