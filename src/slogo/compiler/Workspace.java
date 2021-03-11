package slogo.compiler;

import slogo.compiler.Workspace;
import slogo.compiler.WorkspaceEntry;


/**
 * Keeps track of all objects that extend {@link WorkspaceEntry}.
 * Will notify the {@code WorkspaceViewController} of any changes. Internally keeps
 * the {@code WorkspaceEntry} objects in {@code Collection}s.
 *
 * {@code Command} objects have direct access to this class because they frequently create or
 * use objects in the workspace. For example, a {@code NewFunctionCommand}, once performer,
 * would place all the function's required parameters as variables in the workspace, as well as the
 * {@code Function} object it creates.
 *
 * When {@code Function} is running through its {@code Token} objects, if it comes across
 * a generic {@code Token} type with no specific subclass, it will search through the workspace
 * for a {@code Variable} or {@code Function} with a matching name.
 *
 * @author Yi Chen
 */
public class Workspace {

  /**
   * Adds the passed {@code WorkspaceEntry} to the workspace. This also notifies the
   * {@code WorkspaceViewController} of the addition. In the case that a {@code WorkspaceEntry}
   * with the same name already exists, this operation will replace it with the most recently
   * passed object.
   * @param entry The {@code WorkspaceEntry} to add to the workspace.
   * @return Returns {@code true} if the addition is successful, otherwise return {@code false}.
   */
  public boolean add(WorkspaceEntry entry);

  /**
   * Returns the {@code WorkspaceEntry} with the matching name identifier. If not found,
   * return null.
   * @param name search {@code String}.
   * @return the {@code WorkspaceEntry} matching the search name, or {@code null}.
   */
  public WorkspaceEntry search(String name);

}