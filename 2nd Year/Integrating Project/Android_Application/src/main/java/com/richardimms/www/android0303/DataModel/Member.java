/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.richardimms.www.android0303.DataModel;

import java.sql.Date;

/**
 * This class holds all of the information relating to a member in the system
 * @author BrianV
 */
public class Member {

    private Long id;
    private String forename;
    private String surname;
    private Date dob;
    private Long balance;
    private String addline1;
    private String addline2;
    private String city;
    private String postcode;
    private String contact_number;
    private String email;
    private String is_active;
    
    // only used when first time registering
    private String password;

    public Member() {
    }

    public Member(String forename, String surname, Date dateOfBirth, Long balance, String address1, String address2, String city, String postcode, String contactNumber, String email) {
        this.forename = forename;
        this.surname = surname;
        this.dob = dateOfBirth;
        this.balance = balance;
        this.addline1 = address1;
        this.addline2 = address2;
        this.city = city;
        this.postcode = postcode;
        this.contact_number = contactNumber;
        this.email = email;
    }

    /**
     * Accessor method used to retrieve the Members ID.
     * @return - Long value being the Members ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Accessor method used to assign a Members ID.
     * @param id - Long value being the Members ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Accessor method used to retrieve a Members forename.
     * @return - String value being the Members forename.
     */
    public String getForename() {
        return forename;
    }

    /**
     * Accessor method used to assign a Members forename.
     * @param forename - String value being the Members forename.
     */
    public void setForename(String forename) {
        this.forename = forename;
    }

    /**
     * Accessor method used to retrieve the Members surname.
     * @return - String value being the Members surname.
     */
    public String getSurname() {
        return surname;
    }


    /**
     * Accessor method used to assign the Members surname.
     * @param surname - String value being the Members surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Accessor method used to retirve the Members D.O.B
     * @return - Date value being the Members D.O.B
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Accessor method used to assign the Members D.O.B
     * @param dob - Date value being the Members D.O.B
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Accessor method used to retrieve the Members balance.
     * @return - Long value being the Members current balance.
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * Accessor method used to assign the Members balance.
     * @param balance - Long value being the Members balance.
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    /**
     * Accessor method used to retrieve the address line 1 of a Members address.
     * @return - String value being the address line 1 of a Members address.
     */
    public String getAddline1() {
        return addline1;
    }

    /**
     * Accessor method used to set the address line 1 of a Members address.
     * @param addline1 - String value being the address line 1 of a Members address.
     */
    public void setAddline1(String addline1) {
        this.addline1 = addline1;
    }

    /**
     * Accessor method used to retrieve the address line 2 of a Members address.
     * @return - String value being the address line 2 of a Members address.
     */
    public String getAddline2() {
        return addline2;
    }

    /**
     * Accessor method used to assign the address line 2 of a Members address.
     * @param addline2 - String value being the address line 2 of a Members address.
     */
    public void setAddline2(String addline2) {
        this.addline2 = addline2;
    }

    /**
     * Accessor method used to retrieve the city from a Members address.
     * @return - String value being the city from a Members address.
     */
    public String getCity() {
        return city;
    }

    /**
     * Accessor method used to assign the city to a Members address.
     * @param city - String value being the city to assign to a Members address.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Accessor method used to retrieve the postcode from a Members address.
     * @return - String value being the postcode from a Members address.
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Accessor method used to assign the postcode to a Members address.
     * @param postcode - String value being the postcode to assign to a Members address.
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Accessor method used to retrieve the contact number of a Member.
     * @return - String value being the contact number.
     */
    public String getContact_number() {
        return contact_number;
    }

    /**
     * Accessor method used to assign the contact number of a Member.
     * @param contact_number - String value being the contact number.
     */
    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    /**
     * Accessor method used to retrieve the email address of a Member.
     * @return - String value being the email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Accessor method used to assign the email address of a Member.
     * @param email - String value being the email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Accessor method used to retrieve the activity status of a Member.
     * @return - String value being the activity status.
     */
    public String getIs_active() {
        return is_active;
    }

    /**
     * Accessor method used to assign the activity status of a Member.
     * @param is_active - String value being the activity status.
     */
    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    /**
     * Accessor method used to retrieve the password of a Member.
     * @return - String value being the hashed password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Accessor method used to assign the password to a Member.
     * @param password - String value being the hashed password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
