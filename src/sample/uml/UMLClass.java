package sample.uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UMLClass extends UMLClassifier {

    private boolean isAbstract;

    private List<UMLAttribute> attributes;

    public UMLClass(String name) {
        super(name);
        this.isAbstract = false;
        attributes = new ArrayList<>();

    }


    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public boolean addAttribute(UMLAttribute attr) {
        for (UMLAttribute a: attributes) {
            if (a.getName() == attr.getName()) return false;
        }
        attributes.add(attr);
        return true;
    }

    public int getAttrPosition(UMLAttribute attr) {
        for (UMLAttribute a: attributes) {
            if (a.getName() == attr.getName()) return attributes.indexOf(a);
        }
        return -1;
    }

    public int moveAttrAtPosition(UMLAttribute attr, int pos) {

        if (getAttrPosition(attr) < 0) return -1;
        attributes.add(pos,attributes.remove(getAttrPosition(attr)));
        return pos;
    }

    public List<UMLAttribute> getAttributes() {
        return Collections.unmodifiableList(attributes);
    }


}
