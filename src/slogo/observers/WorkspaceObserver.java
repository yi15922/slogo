package slogo.observers;

import slogo.compiler.WorkspaceEntry;

/**
 * The WorkspaceObserver should be implemented by any class seeking to
 * keep track of new items added to the programs Workspace
 *
 * @author Liam Idrovo
 */
public interface WorkspaceObserver {
    void receiveWorkspaceEntry(WorkspaceEntry entry);
}
