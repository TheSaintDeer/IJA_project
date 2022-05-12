package com.umleditor.uml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassSequence {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setNameClassFrom(String nameClassFrom) {
        this.nameClassFrom = nameClassFrom;
    }

    public void setNameClassTo(String nameClassTo) {
        this.nameClassTo = nameClassTo;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

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

    @Override
    public String toString() {
       return String.format("{name:%s;from:%s;to:%s;isActive:%s;}", this.getName(),this.nameClassFrom,this.nameClassTo,this.isActive);

    }
}
