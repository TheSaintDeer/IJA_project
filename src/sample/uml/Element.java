package sample.uml;

public class Element {

    private String name;

    public Element(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

//    public StringProperty nameProperty() {
//        return name;
//    }

    public void setName(String name) {
        this.name = name;
    }


}
