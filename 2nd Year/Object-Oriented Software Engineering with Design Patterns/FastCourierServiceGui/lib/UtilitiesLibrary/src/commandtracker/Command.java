/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commandtracker;

import command.interfaces.ICommand;
import command.interfaces.ICommandBehaviour;

/**
 * Concrete implementation of the ICommand interface, wraps an ICommandBehaviour
 * and ads functionality to track its status as executed or undone.
 * @author Brian Viviers
 */
public class Command implements ICommand {

    private ICommandBehaviour delegate;
    private Boolean blnExecuted;

    /**
     * Constructor to 'Decorate' i.e. wrap an ICommandBehaviour object
     * @param objICommandBehav - A Command Behaviour to wrap
     */
    public Command(ICommandBehaviour objICommandBehav) {
        delegate = objICommandBehav;
        blnExecuted = false;
    }

    @Override
    public Boolean isExecuted() {
        return this.blnExecuted;
    }

    @Override
    public Boolean setExecuted(Boolean flag) {
        Boolean blnResult = false;
        if (null != flag) {
            this.blnExecuted = flag;
            blnResult = flag;
        }
        return blnResult;
    }

    @Override
    public Boolean isUndone() {
        Boolean done = false;
        String isDone = this.delegate.doCommand();
        if (!isDone.equals("")) {
            done = true;
        }
        this.blnExecuted = done;
        return done;
    }

    @Override
    public String doCommand() {
        return delegate.doCommand();
    }

    @Override
    public String undoCommand() {
        Boolean undone = false;
        String isUndone = this.delegate.undoCommand();
        if (!isUndone.equals("")) {
            undone = true;
        }
        this.blnExecuted = !undone;
        return isUndone;
    }
}
