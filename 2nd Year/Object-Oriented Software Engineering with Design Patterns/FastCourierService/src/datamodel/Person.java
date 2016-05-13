/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel;

import java.io.Serializable;

/**
 * This is an abstract class holding attributes for a person’s name.
 * @author Brian Viviers
 */
public abstract class Person implements Serializable {
    private String forename;
    private String surname;
    
    /**
     * Default constructor required for serialisation initialises
     * forename and surname to UNKNOWN.
     */
    public Person(){
        forename = "UNKNOWN";
        surname = "UNKNOWN";
    }
    
    /**
     * Constructor setting the forename and surname of a person.
     * @param forename - String being the forename of a person.
     * @param surname - String being the surname of a person.
     */
    public Person(String forename, String surname){
        this.setForenameHelper(forename);
        this.setSurnameHelper(surname);
    }

    /**
     * Accessor method to retrieve the forename of a person.
     * @return - String being the forename of a person.
     */
    public String getForename() {
        return forename;
    }

    /**
     * Accessor method to store the forename of a person.
     * @param forename - String being the value to store as the forename.
     * The forename can contain only letters.
     */
    public void setForename(String forename) {
        this.setForenameHelper(forename);
    }
    
    private void setForenameHelper(String forename){
        if (forename != null && !forename.isEmpty()) {
            if (forename.matches("[a-zA-Z]+")) {
                this.forename = forename;
            } else {
                System.out.println("The forename should contain only letters.");
            }
        } else {
            System.out.println("The forename is null or empty.");
        }
    }

    /**
     * Accessor method to retrieve the surname of a person.
     * @return - String being the surname of a person.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Accessor method to store the surname of a person.
     * @param surname - String being the value to store as surname.
     * The surname can contain only letters.
     */
    public void setSurname(String surname) {
        this.setSurnameHelper(surname);
    }
    
    private void setSurnameHelper(String surname) {
        if (surname != null && !surname.isEmpty()) {
            if (surname.matches("[a-zA-Z]+")) {
                this.surname = surname;
            } else {
                System.out.println("The surname should contain only letters.");
            }
        } else {
            System.out.println("The surname is null or empty.");
        }
    }
    
    /**
     * Method to retrieve the full name of a person.
     * @return - String being the forename concatenated with a whitespace and the surname.
     */
    public String getFullName(){
        return forename + " " + surname;
    }

    @Override
    public String toString() {
        return forename + " " + surname;
    }
}