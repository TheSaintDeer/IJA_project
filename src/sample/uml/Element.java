package sample.uml;

public class Element {

    public Element(String name) {
        setName(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    private StringProperty name = new SimpleStringProperty();



}
