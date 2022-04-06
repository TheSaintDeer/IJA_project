package sample.uml;

import java.lang.String;

public class Element {

    String name;

    //Constructors

    public Element(String name) {
        this.name = name;
    }

    //Methods

    public String getName() {
        return name;
    }

    public void rename(String newName) {
        this.name = newName; 
    }
    
}
