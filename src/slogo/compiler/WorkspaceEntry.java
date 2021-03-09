package slogo.compiler;

/**
 * This interface makes an object an addable object in a workspace. Calling {@code Workspace.add()}
 * on any object that implements this will add it as an entry to the workspace and make it
 * searchable by a {@code String} value.
 *
 * @author Yi Chen
 */
public interface WorkspaceEntry {

  /**
   * Sets the name of the entry
   * @param name {@code String} name of the entry
   */
  public void setName(String name);



}