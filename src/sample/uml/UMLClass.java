package sample.uml;

import java.util.ArrayList;
import java.lang.String;
import java.util.List;
import java.util.Collections;
import java.util.Objects;

public class UMLClass extends UMLClassifier{
    
    boolean isAbstract = false;
    ArrayList<UMLAttribute> attr;

    //Constructors
    public UMLClass(String name) {
        super(name);
        attr = new ArrayList<UMLAttribute>();
    }

    //Methods
    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public boolean addAttribute(UMLAttribute attr) {
        for (UMLAttribute i: this.attr) {
            if (Objects.equals(i.name, attr.name)) {
                return false;
            }
        }

        UMLAttribute new_attr = new UMLAttribute(attr.name, attr.type);
        this.attr.add(new_attr);
        return true;
    }

    public int getAttrPosition(UMLAttribute attr) {
        for (int i = 0; i < this.attr.size(); i++) {
            if (Objects.equals(this.attr.get(i).name, attr.name)) {
                return i;
            }
        }

        return -1;
    }

    public int moveAttrAtPosition(UMLAttribute attr, int pos) {
        for (UMLAttribute i: this.attr) {
            if (Objects.equals(i.name, attr.name)) {
                ArrayList<UMLAttribute> new_attr = new ArrayList<UMLAttribute>();
                int cur_pos = 0;
                for (UMLAttribute j: this.attr) {
                    if (cur_pos == pos) {
                        new_attr.add(i);
                    }
                    
                    if (!Objects.equals(i.name, j.name)) {
                        new_attr.add(j);
                    }

                    cur_pos++;
                }
                this.attr = new_attr;
                return 0;
            }
        }

        return -1;
    }

    public List<UMLAttribute> getAttributes() {
        return Collections.unmodifiableList(attr);
    }
}
