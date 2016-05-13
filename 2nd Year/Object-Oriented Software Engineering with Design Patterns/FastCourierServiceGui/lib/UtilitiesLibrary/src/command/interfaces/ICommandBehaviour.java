/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command.interfaces;

/**
 * An Interface that expresses the concept of a command with a do / undo behaviour
 * @author Brian Viviers
 */
public interface ICommandBehaviour {
    
    /**
     * Executes this command behaviour
     * @return String - Explanation of what the command has just performed
     */
    public String doCommand();
    
    /**
     * Reverses the command behaviour cancelling its effects
     * @return String - Explanation of what the command has just performed
     */
    public String undoCommand();
}
