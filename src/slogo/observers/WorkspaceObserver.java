package slogo.observers;

import slogo.compiler.WorkspaceEntry;

public interface WorkspaceObserver {
    void receiveWorkspaceEntry(WorkspaceEntry entry);
}
