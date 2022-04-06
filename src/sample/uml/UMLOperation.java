package sample.uml;

import java.util.ArrayList;
import java.lang.String;
import java.util.List;
import java.util.Collections;

public class UMLOperation extends UMLAttribute{
    
    ArrayList<UMLAttribute> arg;

    //Constructors

    UMLOperation(String name, UMLClassifier type) {
        super(name, type);
        this.arg = new ArrayList<UMLAttribute>();
    }

    //Methods

    public static UMLOperation create(String name, UMLClassifier type, UMLAttribute... args) {
        UMLOperation new_arg = new UMLOperation(name, type); 
        for (int i = 0; i < args.length; i++) {
            new_arg.addArgument(args[i]);
        }

        return new_arg;
    }

    public boolean addArgument(UMLAttribute arg) {
        for (UMLAttribute i: this.arg) {
            if (i.name == arg.name) {
                return false;
            }
        }

        UMLAttribute new_arg = new UMLAttribute(arg.name, arg.type);
        this.arg.add(new_arg);
        return true;
    }

    public List<UMLAttribute> getArguments() {
        List<UMLAttribute> list = Collections.unmodifiableList(arg);
        return list;
    }

}
