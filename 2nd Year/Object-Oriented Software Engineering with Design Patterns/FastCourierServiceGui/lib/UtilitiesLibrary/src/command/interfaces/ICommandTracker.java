/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command.interfaces;

/**
 * An Interface expressing the concept of a command tracker which 'tracks' 
 * a collection of commands and allows them to be undone or redone.
 * @author Brian Viviers
 */
public interface ICommandTracker {
    
    /**
     * Executes the provided command and if it completes adds it to the collection
     * of executed commands being tracked
     * @param objACommand - Interface to the command object to execute
     * @return Boolean True if command completed and was added to the 
     * collection of executed commands, False otherwise.
     */
    public Boolean executeCommand(ICommand objACommand);
    
    /**
     * This method reverses the last command added to the collection of executed commands.
     * Repeated calls to this method will provide an in order reversal of executed commands.
     * Undone commands will be added to their own collection
     * @return String - Explanation of what command was undone.
     */
    public String undoLastCommand();
    
    /**
     * This method executes the last command added to the collection of undone commands.
     * @return String - Explanation of what command was redone.
     */
    public String redoLastCommand();
    
    /**
     * This method tests if a call to undoLastCommand will find a command that can be undone.
     * @return Boolean True if a executed command exists that can be undone, False otherwise.
     */
    public Boolean isUndoable();
    
    /**
     * This method tests if a call to redoLastCommand will find a command that can be executed.
     * @return Boolean True if a undone command exists that can be executed, False otherwise.
     */
    public Boolean isRedoable();
}
