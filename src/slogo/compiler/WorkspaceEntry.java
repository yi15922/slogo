package slogo.compiler;

import slogo.compiler.token.SLogoToken;

/**
 * Makes an object an addable object in a {@link Workspace}. Calling {@code Workspace.add()}
 * on any object that implements this will add it as an entry to the workspace and make it
 * searchable by a {@code String} value.
 *
 * A {@code WorkspaceEntry} is visible by default, however it can be set to be
 * invisible. Invisible workspace entries will not appear on the UI workspace.
 *
 * @author Yi Chen
 */
public class WorkspaceEntry extends SLogoToken {
  private boolean isVisible = true;

  public WorkspaceEntry(String name){
    super(name);
  }

  /**
   * Sets the visibility of the {@link WorkspaceEntry}.
   * @param visible a {@code boolean} for the visibility of the entry
   */
  public void setVisibility(boolean visible){
    isVisible = visible;
  }

  /**
   * Returns the visibility status of this {@link WorkspaceEntry}.
   * @return a {@code boolean} for the visibility of the entry
   */
  public boolean isVisible(){
    return isVisible;
  }




}