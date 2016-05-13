/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataModel;

import java.sql.Date;

/**
 * This class represents an abstract concept : a person.
 * @author PRCSA
 */
public abstract class AbstractPerson {
    

    private String forename;

    private String surname;

    private Date dateOfBirth;

    private String email;

    private String contactNo;
    
    /**
     * Default Constructor initialising variables to "UKNOWN".
     */
    public AbstractPerson()
    {
        this.forename = "UNKNOWN";
        this.surname = "UNKNOWN";
        this.email = "UKNOWN";
    }
    
    /**
     * Default Constructor initialising variables to there associated values.
     * @param forename - String value being the forename of a Person.
     * @param surname - String value being the surname of a Person.
     * @param email  - String value being the email of a Person.
     * @param contactNo  - String value being the contact number of a Person.
     * @param dateOfBirth  - Date value being the date the person was born.
     */
    public AbstractPerson(String forename, String surname, String email, String contactNo, Date dateOfBirth)
    {
        this();
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.contactNo = contactNo;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Accessor method used to retrieve the Persons forename.
     * @return - String value being the forename of a Person.
     */
    public String getForename() {
        return forename;
    }

    /**
     * Accesor method used to set the Persons forename.
     * @param forename - String value being the forename of a Person.
     */
    public void setForename(String forename) {
        this.forename = forename;
    }

    /**
     * Accessor method used to retrieve the Persons surname.
     * @return - String value being the surname of a Person.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Accessor method used to set the Persons surname.
     * @param surname - String value being the surname of a Person.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Accessor method used to retrieve the date of birth of a Person.
     * @return - Date value being the date of birth of a Person.
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Accessor method used to set the date of birth of a Person.
     * @param dateOfBirth  - Date value being the date of birth of a Person.
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Accessor method used to retrieve the email of a Person.
     * @return - String value being the email of a Person.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Accessor method used to set the email of a Person.
     * @param email - String value being the email of a Person.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Accessor method used to retrieve the contact number of a Person.
     * @return - String value being the contact number of a Person.
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * Accessor method used to set the contact number of a Person.
     * @param contactNo  - String value being the contact number of a Person.
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     * Method used to get the full name of a Person.
     * @return - String value being the full name of a Person.
     */
    public String getFullName()
    {
        String result = this.forename + " " + this.surname;
        return result;
    }
}
