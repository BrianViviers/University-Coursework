/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import java.io.Serializable;

/**
 * This class represents the "abstract concept" of a person in the Fast Courier 
 * Service application
 * @author rtucker
 */
public abstract class Person implements Serializable {
    
    private String forename;
    private String surname;
    
    /**
     * Default constructor, initialises both forename and surname to "UNKNOWN".
     * Required for serialisation
     */
    public Person()
    {
        this.forename = "UNKNOWN";
        this.surname = "UNKNOWN";
    }
    
    /**
     * Constructs an object with the provided forename and surname 
     * @param forename - String being the persons forename
     * @param surname - String being the persons surname
     */
    public Person(String forename, String surname)
    {
        this();
        this.forename = forename;
        this.surname = surname;
    }

    /**
     * Accessor method to retrieve the persons forename
     * @return - A String being the persons forename
     */
    public String getForename() {
        return forename;
    }

    /**
     * Accessor method to set the persons forename
     * @param forename - A String being the forename to use for this person
     */
    public void setForename(String forename) {
        this.forename = forename;
    }

    /**
     * Accessor method to retrieve the persons surname
     * @return - A String being the persons surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Accessor method to set the persons surname
     * @param surname - A String being the surname to use for this person
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    /**
     * This method retrieves the persons full name in the format
     * forename [SPACE] surname
     * @return - A String being the persons full name
     */
    public String getFullName()
    {
        String result = this.forename + " " + this.surname;
        return result;
    }
}
