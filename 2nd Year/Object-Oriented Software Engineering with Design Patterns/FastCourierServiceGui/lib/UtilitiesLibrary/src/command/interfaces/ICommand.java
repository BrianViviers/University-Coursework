/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command.interfaces;

/**
 * A Decorator to add functionality to an ICommandBehaviour object. Added
 * functions support tracking if the command has been executed or undo after
 * execution
 * @author Brian Viviers
 */
public interface ICommand extends ICommandBehaviour{
    
    /**
     * Accessor to test if this command has been executed
     * @return Boolean True if this command has been executed and not undone,
     * False otherwise
     */
    public Boolean isExecuted();
    
    /**
     * Accessor to set the boolean flag which indicates if this command has been executed
     * @param flag - Boolean being the new value for the flag
     * @return Boolean True if the flag was set to the value given, False otherwise
     */
    public Boolean setExecuted(Boolean flag);
    
    /**
     * Accessor to test if this command has been undone
     * @return Boolean True if this command has NOT been executed or if it
     * was undone after execution, False otherwise.
     */
    public Boolean isUndone();
    
}
