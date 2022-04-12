package sample.uml;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UMLClass extends UMLClassifier {

    private boolean isAbstract;

//    private StringProperty className = new SimpleStringProperty();
    private final ObservableList<UMLAttribute> attributes;

    public UMLClass(String name) {
        super(name);
        this.isAbstract = false;
        attributes = FXCollections.observableArrayList();
    }


    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public boolean addAttribute(UMLAttribute attr) {
        for (UMLAttribute a: attributes) {
            if (a.getName().equals(attr.getName())) return false;
        }
        attributes.add(attr);
        return true;
    }

    public int getAttrPosition(UMLAttribute attr) {
        for (UMLAttribute a: attributes) {
            if (a.getName().equals(attr.getName())) return attributes.indexOf(a);
        }
        return -1;
    }

    public UMLAttribute getAttributeByName(String name) {
        for (UMLAttribute a :
                attributes) {
            if (a.getName().equals(name)) return a;
        }
        return null;
    }

    public int moveAttrAtPosition(UMLAttribute attr, int pos) {

        if (getAttrPosition(attr) < 0) return -1;
        attributes.add(pos,attributes.remove(getAttrPosition(attr)));
        return pos;
    }

    public ObservableList getAttributes() {
        return attributes;
    }

    public void deleteAttribute(UMLAttribute selected) {
        attributes.remove(selected);
    }

}
