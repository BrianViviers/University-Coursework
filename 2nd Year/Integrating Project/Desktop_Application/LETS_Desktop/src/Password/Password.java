/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Password;
/**
 * Password Class to assign how a password is formed
 * @author PRCSA
 */
public class Password {
    
    String username = "UKNOWN";
    String userPassword = "UKNOWN";
    int admin_id = 0;
    
    /**
     * Default Constructor for the password.
     */
    public Password()
    {
        username = "UNKNOWN";
        userPassword = "UKNOWN";
        admin_id = 0;
    }
    
    /**
     * Default Constructor for the password
     * @param username - Username of the admin
     * @param password - Password of the admin
     * @param admin_id - Admin ID.
     */
    public Password(String username,String password,int admin_id)
    {
        this.username = username;
        this.userPassword = password;
        this.admin_id = admin_id;
    }

    /**
     * Accessor method used to retrieve the username
     * @return - String value being the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Accessor method used to set the username
     * @param username - String value being the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Accessor method used to retrieve the password
     * @return - String value being the password.
     */
    public String getPassword() {
        return userPassword;
    }

    /**
     * Accessor method used to set the password
     * @param password - String value being the set password.
     */
    public void setPassword(String password) {
        this.userPassword = password;
    }

    /**
     * Accessor method used to retrieve the admin ID
     * @return - int value being the admin ID.
     */
    public int getAdmin_id() {
        return admin_id;
    }

    /**
     * Accessor method used to set the admin ID
     * @param admin_id - int value being the admin ID.
     */
    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }
    
    
}
