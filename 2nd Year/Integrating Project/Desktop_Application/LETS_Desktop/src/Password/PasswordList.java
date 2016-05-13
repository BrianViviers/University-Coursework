/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Password;



import java.util.ArrayList;

/**
 * Class for the list of passwords for admins.
 * @author PRCSA
 */
public class PasswordList {
     
    private ArrayList<Password> passwordList;
      
    /**
     * Default constructor for the password list class.
     */
    public PasswordList()
    {
        passwordList = new ArrayList<>();
    }
    
    /**
     * Method used to add a password to list of passwords.
     * @param pass - Password value being the password to add.
     */
    public void addPassword(Password pass)
    {
        passwordList.add(pass);
    }
        
    /**
     * Method used to find the index of a username
     * @param username - String value being the username
     * @return - int value being the index assigned to the username.
     */
    public int findIndexOfUsername(String username)
    {
        int index = -1; 
        
        for(int i =0 ; i< passwordList.size();i++)
        {
            if(passwordList.get(i).username.equals(username))
            {
               index = i;
            }
        }
        
        if(index == -1)
        {
            System.out.println("Username incorrect");
        }
        
        return index;
    }
    
    /**
     * Method used to return the password
     * @param index - int value being the index to search through.
     * @return - Password being the value found at the index.
     */
    public Password returnPassword(int index)
    {
        
        if(index >= 0)
        {
            return passwordList.get(index);
        }
        else
        {
            System.out.println("Username incorrect");
        }
        
        return null;
    }
    
    /**
     * Password List size.
     * @return - int value being the size of the password list.
     */
    public int passListSize()
    {
        return passwordList.size();
    }
}
