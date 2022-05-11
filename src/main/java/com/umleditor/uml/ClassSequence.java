package com.umleditor.uml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassSequence {

    private String name;
    private String nameClassFrom;
    private String nameClassTo;
    private boolean isActive = false;

    /**
     * @param nameRelation - name of relation between classes
     * @param nameClassFrom - the name of the class from which the link comes
     * @param nameClassTo - the name of the class to which the link goes
     */

    public ClassSequence (String nameRelation, String nameClassFrom, String nameClassTo) {
        this.name = nameRelation;
        this.nameClassFrom = nameClassFrom;
        this.nameClassTo = nameClassTo;
    }

    /**
     * @param nameClass - the name of the class which active
     */
    public ClassSequence (String nameClass) {
        this.nameClassFrom = nameClass;
        this.isActive = true;
    }

    /**
     * @return the name of the class from which the link comes
     */

    public String getNameClassFrom() {
        return nameClassFrom;
    }

    /**
     * @return the name of the class to which the link goes
     */
    public String getNameClassTo() {
        return nameClassTo;
    }

    /**
     * @return the name of the relation
     */
    public String getName() {
        return name;
    }

    /**
     * @return switcher of lifeline
     */
    public boolean isActive() {
        return isActive;
    }
}
