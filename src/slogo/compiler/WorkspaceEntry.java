package slogo.compiler;

import slogo.compiler.token.SLogoToken;

/**
 * This interface makes an object an addable object in a workspace. Calling {@code Workspace.add()}
 * on any object that implements this will add it as an entry to the workspace and make it
 * searchable by a {@code String} value.
 *
 * @author Yi Chen
 */
public class WorkspaceEntry extends SLogoToken {

  public WorkspaceEntry(String name){
    super(name);
  }



}