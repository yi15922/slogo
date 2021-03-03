package slogo;

/**
 * This interface makes an object an addable object in a workspace. Calling {@code Workspace.add()}
 * on any object that implements this will add it as an entry to the workspace and make it
 * searchable by a {@code String} value.
 */
public interface WorkspaceEntry {
  public void() setName(String name);
}