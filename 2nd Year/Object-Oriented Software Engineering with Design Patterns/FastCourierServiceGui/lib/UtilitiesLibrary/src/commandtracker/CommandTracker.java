/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commandtracker;

import command.interfaces.ICommand;
import command.interfaces.ICommandTracker;
import java.util.Stack;

/**
 * This class provides execution and tracking services for ICommand objects
 * @author Brian Viviers
 */
public class CommandTracker implements ICommandTracker {

    //Declare the 'Done' and 'Un-Done' stacks of ICommand objects
    private Stack<ICommand> stkDone = new Stack<>();
    private Stack<ICommand> stkUndone = new Stack<>();

    @Override
    public Boolean executeCommand(ICommand objACommand) {
        Boolean blnExecuted = false;
        String result;
        if (null != objACommand) {
            result = objACommand.doCommand();
            if (!result.equals("")) {
                this.stkDone.push(objACommand);
                blnExecuted = true;
            }
        }
        return blnExecuted;
    }

    @Override
    public String undoLastCommand() {
        String result = "";
        if (this.isUndoable()) {
            //Get the last command
            ICommand lastCommand = this.stkDone.pop();
            //Undo the command
            result = lastCommand.undoCommand();
            if (!result.equals("")) {
                //Push command to the undone stack
                this.stkUndone.push(lastCommand);
            }//Else not needed empty string is returned by default
        }
        return result;
    }

    @Override
    public String redoLastCommand() {
        String result = "";
        if (this.isRedoable()) {
            //Get last 'undone' command
            ICommand lastCommand = this.stkUndone.pop();
            //Redo the last command
            result = lastCommand.doCommand();
            if (!result.equals("")) {
                //Push command to the 'done' stack
                this.stkDone.push(lastCommand);
            }//Else not needed empty string is returned by default
        }
        return result;
    }

    @Override
    public Boolean isUndoable() {
        Boolean blnCanUndo = false;
        //If there are commands on the 'Done' stack we can undo the last command
        if (!this.stkDone.empty()) {
            blnCanUndo = true;
        }
        return blnCanUndo;
    }

    @Override
    public Boolean isRedoable() {
        Boolean blnCanRedo = false;
        //If there are commands on the 'unDone' stack we can redo the last command
        if (!this.stkUndone.empty()) {
            blnCanRedo = true;
        }
        return blnCanRedo;
    }
}
