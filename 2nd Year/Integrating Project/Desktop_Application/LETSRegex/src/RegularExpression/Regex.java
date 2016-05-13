/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RegularExpression;


import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 *
 * @author PRCSA
 */
public class Regex {
    
        
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_POSTCODE_REGEX = 
            Pattern.compile("[A-Z]{1}[A-Z0-9]{2}[ ]{1}[0-9]{1}[A-Z]{2}", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_WORDS_ONLY_REGEX = 
            Pattern.compile("[A-Za-z]+",Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_NUMBERS_ONLY_REGEX = 
            Pattern.compile("[0-9]+",Pattern.CASE_INSENSITIVE);

    /**
     *  Method used to see if the emails match the regex
     * @param email - TextField to assign the regex too.
     */
    public Boolean emailRegexM(JTextField email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText());
        Boolean valid = false;
        if (matcher.matches() == true) {
            email.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            valid  = true;
        } else if (matcher.matches() == false) {
            email.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        return valid;
    }

    /**
     * Method used to see if the assigned values regex matches the regex set.
     * @param txtForenameM - Forename value to search for match
     * @param txtSurnameM - Surname value to search for match
     * @param txtCityM - City value to search for match.
     */
    public Boolean nameRegexM(JTextField txtForenameM, JTextField txtSurnameM, JTextField txtCityM) {
        Matcher matcherFName = VALID_WORDS_ONLY_REGEX.matcher(txtForenameM.getText());
                Boolean valid = false;

        if (matcherFName.matches() == true) {
            txtForenameM.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            valid = true;
        } else if (matcherFName.matches() == false) {
            txtForenameM.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }

        Matcher matcherSName = VALID_WORDS_ONLY_REGEX.matcher(txtSurnameM.getText());

        if (matcherSName.matches() == true) {
            txtSurnameM.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            valid = true;
        } else if (matcherSName.matches() == false) {
            txtSurnameM.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }

        Matcher matcherCity = VALID_WORDS_ONLY_REGEX.matcher(txtCityM.getText());
        if (matcherCity.matches() == true) {
            txtCityM.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            valid = true;
        } else if (matcherCity.matches() == false) {
            txtCityM.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        
        return valid;
    }

    /**
     * Method used to check the regex of contact number
     * @param txtContactM - Value to check if the regex matches
     */
    public Boolean numberRegexM(JTextField txtContactM) {
        Matcher matchContactNumber = VALID_NUMBERS_ONLY_REGEX.matcher(txtContactM.getText());
        Boolean valid = false;

        if (matchContactNumber.matches() == true) {
            txtContactM.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            valid = true;
        } else if (matchContactNumber.matches() == false) {
            txtContactM.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        return valid;
    }

    /**
     * Method used to see if the regex matches on the advert pane
     * @param txtEmailA - Text field to see if input matches regex
     */
    public Boolean emailRegexA(JTextField txtEmailA) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(txtEmailA.getText());
        Boolean valid = false;

        if (matcher.matches() == true) {
            txtEmailA.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            valid = true;
        } else if (matcher.matches() == false) {
            txtEmailA.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        return valid;
    }

    /**
     * Method used to see if the assigned values regex matches the regex set.
     * @param txtForenameA - Forename value to search for match
     * @param txtSurnameA - Surname value to search for match
     * @param txtCityA - City value to search for match.
     */
    public Boolean nameRegexA(JTextField txtForenameA, JTextField txtSurnameA, JTextField txtCityA) {
        Matcher matcherFName = VALID_WORDS_ONLY_REGEX.matcher(txtForenameA.getText());
        Boolean valid = false;

        if (matcherFName.matches() == true) {
            txtForenameA.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            valid = true;
        } else if (matcherFName.matches() == false) {
            txtForenameA.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }

        Matcher matcherSName = VALID_WORDS_ONLY_REGEX.matcher(txtSurnameA.getText());

        if (matcherSName.matches() == true) {
            txtSurnameA.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            valid = true;
        } else if (matcherSName.matches() == false) {
            txtSurnameA.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }

        Matcher matcherCity = VALID_WORDS_ONLY_REGEX.matcher(txtCityA.getText());
        if (matcherCity.matches() == true) {
            txtCityA.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            valid = true;
        } else if (matcherCity.matches() == false) {
            txtCityA.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        return valid;
    }

    /**
     * Method used to check the regex of contact number
     * @param txtContactA - Value to check if the regex matches
     */
    public Boolean numberRegexA(JTextField txtContactA) {
        Matcher matchContactNumber = VALID_NUMBERS_ONLY_REGEX.matcher(txtContactA.getText());
        Boolean valid = false;

        if (matchContactNumber.matches() == true) {
            txtContactA.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            valid = true;
        } else {
            txtContactA.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        return valid;
    }

    /**
     * Method used to check the regex of the password.
     * @param txtPostcodeM - Text field to check the input matches the regex.
     */
    public Boolean postcodeRegexM(JTextField txtPostcodeM) {
        Matcher matchPostcodeM = VALID_POSTCODE_REGEX.matcher(txtPostcodeM.getText());
        Boolean valid = false;

        if (matchPostcodeM.matches() == true) {
            txtPostcodeM.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            valid = true;
        } else {
            txtPostcodeM.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        return valid;
    }

    /**
     * Method used to check the regex of the password.
     * @param txtPostcodeA - Text field to check the input matches the regex.
     */
    public Boolean postcodeRegexA(JTextField txtPostcodeA) {
        Matcher matchPostcodeA = VALID_POSTCODE_REGEX.matcher(txtPostcodeA.getText());
        Boolean valid = false;

        if (matchPostcodeA.matches() == true) {
            txtPostcodeA.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            valid = true;
        } else {
            txtPostcodeA.setBorder(BorderFactory.createLineBorder(Color.RED));
            valid = false;
        }
        return valid;
    }
}
