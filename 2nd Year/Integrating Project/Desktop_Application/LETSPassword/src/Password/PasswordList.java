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
     
    private ArrayList<Password> passList;
      
    /**
     * Default constructor for the password list class.
     */
    public PasswordList()
    {
        passList = new ArrayList<>();
    }
    
    /**
     * Method used to add a password to list of passwords.
     * @param pass - Password value being the password to add.
     */
    public void addPassword(Password pass)
    {
        passList.add(pass);
    }
        
    /**
     * Method used to find the index of a username
     * @param username - String value being the username
     * @return - int value being the index assigned to the username.
     */
    public int findIndex(String username)
    {
        int index = -1; 
        
        for(int i =0 ; i< passList.size();i++)
        {
            if(passList.get(i).username.equals(username))
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
            return passList.get(index);
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
        return passList.size();
    }
}
