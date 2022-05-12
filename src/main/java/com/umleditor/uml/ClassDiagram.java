package com.umleditor.uml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

/**
 * The class represents a class diagram. It is derived from the Element class (has a name).
 * Contains a list of classes (instances of the UMLClass class) or classifiers for user-undefined
 * types (instances of the UMLClassifier class).
 */

public class ClassDiagram extends Element{

    private ObservableList<UMLClass> classes;
    private List<UMLClassifier> classifiers;
    private List<UMLRelation> relations;
    private List<ClassSequence> sequences;

    /**
     * Constructor for creating a diagram instance. Each diagram has its own name.
     * @param name - the name of the diagram
     */
    public ClassDiagram(String name) {
        super(name);
        classes = FXCollections.observableArrayList();
        classifiers = new ArrayList<>();
        relations = FXCollections.observableArrayList();
        sequences = new ArrayList<>();
    }

    /**
     * Creates an instance of the UML class and inserts it into the diagram.
     * If a class with the same name already exists in the diagram, it does nothing.
     * @param name - the name of the diagram
     * @return An object (instance) representing a class. Returns null if the class
     * with the given name already exists.
     */

    public UMLClass createClass(String name) {
        for (UMLClassifier c : classes) {
            if (Objects.equals(c.getName(), name)) return null;
        }
        UMLClass umlClass = new UMLClass(name);
        classes.add(umlClass);
        return umlClass;
    }

    /**
     * Finds a classifier by name in the diagram. If it does not exist,
     * it creates an instance of the Classifier class representing a classifier
     * that is not captured in the diagram (see UMLClassifier.forName (java.lang.String));
     * used, for example, for modeling the type of a variable that is not in the diagram.
     * This instance is included in the diagram structures, that this already created
     * instance will be used the next time you try to search.
     * @param name - the name of the classifier.
     * @return  Found or created classifier.
     */

    public UMLClassifier classifierForName(String name) {

        UMLClassifier classifier = findClassifier(name);
        if (classifier == null) {
            classifier = UMLClassifier.forName(name);
        }
        classifiers.add(classifier);
        return classifier;
    }

    /**
     * Finds a classifier by name in the diagram.
     * @param name - the name of the classifier.
     * @return An object (instance) representing a class. Returns null if the class
     * Classifier found. If the classifier of the given name does not exist, it returns null.
     */

    public UMLClassifier findClassifier(String name) {

        if (classifiers.isEmpty()) return null;

        for (UMLClassifier c : classifiers) {
            if (c.getName().equals(name)) return c;
        }
        return null;
    }

    /**
     * Creates an instance of the UML class and inserts it into the diagram.
     * If a class with the same name already exists in the diagram, it does nothing.
     * @param from - the name of the class from which the link comes
     * @param to - the name of the class to which the link goes
     * @param type - the type of the relation
     * @return An object (instance) representing a relation. Returns null if the relation
     * with the given name already exists.
     */

    public UMLRelation createRelat (String to, String from, String type) {
        UMLRelation relat = new UMLRelation(from, to, type);

        for (UMLRelation r : relations) {
            if (r == relat) {
                return null;
            }
        }

        relations.add(relat);
        return relat;
    }

    /**
     * Creates an instance of the UML class.
     * If a class with the same don't exist in the diagram, it does nothing.
     * @param name - the name of the diagram
     * @return An object (instance) representing a class. Returns null if the relation
     * with the given name already exists.
     */

    public UMLClass findClass(String name) {

        if (classes.isEmpty()) return null;

        for (UMLClass c : classes) {
            if (c.getName().equals(name)) return c;
        }
        return null;
    }

    /**
     * Function to get all classes in a diagram
     * @return List representing all classes in the diagram.
     */

    public ObservableList<UMLClass> getAll() {
        return classes;
    }

    /**
     * Function to get all relations in a diagram
     * @return List representing all relations in the diagram.
     */

    public List<UMLRelation> getAllRelationsObservable() {
        return relations;
    }

    /**
     * Function to get all relations in a diagram
     * @return List representing all relations in the diagram.
     */
    public List<UMLRelation> getAllRelations() {
        return Collections.unmodifiableList(relations);
    }

    /**
     * Function to get last class in a diagram
     * @return An object (instance) representing last class.
     */
    public UMLClass getLast() {
        return classes.get(classes.size()-1);
    }



    public List<UMLClass> getClasses() {
        return Collections.unmodifiableList(classes);
    }

    /**
     * Finding all relations with the desired class.
     * @param nameOfClass - class name for which we want to find all relations
     * @return List of relation in which the desired class is involved
     */
    public List<UMLRelation> findAllRelat(String nameOfClass){
        List<UMLRelation> relations = new ArrayList<>();


        for (UMLRelation i: this.relations) {
            if (i.getFromClass().equals(nameOfClass) || i.getToClass().equals(nameOfClass)) {
                relations.add(i);
            }
        }

        return relations;
    }
    @Override
    public String toString() {
        return String.format("{name:%s;classes:%s;relations:%s;sequences:%s;}", this.getName(),this.classes,this.relations,this.sequences);
    }

    public void addClass(UMLClass c) {
        if (findClass(c.getName()) == null) {
            classes.add(c);
        }
    }

    /**
     * @return get all command in sequence diagram
     */

    public List<ClassSequence> getSequences() {
        return sequences;
    }



    /**
     * Function for adding commands `activate <nameOfClass>` or `deactivate <nameOfClass>`
     * @param nameOfClass - name of class, which need start or finish lifeline
     * @return new ClassSequence if class is existed, else null
     */
    public ClassSequence createNewLifeline(String nameOfClass, boolean switcher) {
        for (UMLClass c : classes) {
            if (c.getName().equals(nameOfClass)) {
                ClassSequence newSequence = new ClassSequence(nameOfClass);
                c.setActive(switcher);
                sequences.add(newSequence);
                return newSequence;
            }
        }
        return null;
    }

    /**
     * Function for adding commands `<nameFrom> -> <nameTo>: <nameRelation>`
     * @param nameRelation - text, which describe relation
     * @param nameFrom - the name of the class from which the link comes
     * @param nameTo - the name of the class to which the link goes
     * @return
     */
    public ClassSequence createNewSeqRelation(String nameRelation, String nameFrom, String nameTo) {
        boolean from = false;
        boolean to = false;

        for (UMLClass c : classes) {
            if (c.getName().equals(nameFrom))
                from = true;
            if (c.getName().equals(nameTo))
                to = true;
        }

        if (from && to) {
            ClassSequence newSequence = new ClassSequence(nameRelation, nameFrom, nameTo);
            sequences.add(newSequence);
            return newSequence;
        }

        return null;
    }

    public void addRelation(UMLRelation relation) {
        relations.add(relation);
    }

    public void addSequence(ClassSequence sequence) {
        sequences.add(sequence);
    }

    public void removeAllRelations() {
        relations.clear();
    }

    public void removeAllSequences() {
        sequences.clear();

    }

    public void removeAllClasses() {
        classes.clear();
    }
}
