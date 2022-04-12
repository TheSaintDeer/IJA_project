package sample.uml;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Element {

    private StringProperty name = new SimpleStringProperty();

    public Element(String name) {
        rename(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void rename(String name) {
        this.name.set(name);
    }


}
