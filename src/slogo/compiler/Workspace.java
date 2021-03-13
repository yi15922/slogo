package slogo.compiler;

import java.util.HashMap;
import java.util.Map;
import slogo.compiler.command.SLogoCommand;
import slogo.compiler.command.MakeUserInstruction;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoVariable;


/**
 * Keeps track of all objects that extend {@link WorkspaceEntry}.
 * Will notify the {@code WorkspaceViewController} of any changes. Internally keeps
 * the {@code WorkspaceEntry} objects in {@code Collection}s.
 *
 * {@link SLogoCommand} objects have direct access to this class because they frequently create or
 * use objects in the workspace. For example, a {@link MakeUserInstruction},
 * once performed, would place all the function's required parameters as variables in the workspace,
 * as well as the {@code Function} object it creates.
 *
 * When {@code Function} is running through its {@code Token} objects, if it comes across
 * a generic {@code Token} type with no specific subclass, it will search through the workspace
 * for a {@code Variable} or {@code Function} that matches its {@code toString()}.
 *
 * At most one {@code Workspace} instance should be created per session of SLogo, and the
 * instance should be created at startup.
 *
 * @author Yi Chen
 */
public class Workspace {
  private Map<String, WorkspaceEntry> workspaceEntryMap;

  /**
   *
   */
  public Workspace(){
    workspaceEntryMap = new HashMap<>();
  }

  /**
   * Adds the passed {@link WorkspaceEntry} to the workspace. This also notifies the
   * {@code WorkspaceViewController} of the addition. In the case that a {@code WorkspaceEntry}
   * with the same name already exists, this operation will replace it with the most recently
   * passed object.
   * @param entry the {@code WorkspaceEntry} to add to the workspace.
   */
  public void add(WorkspaceEntry entry){
    workspaceEntryMap.put(entry.toString(), entry);
  }

  /**
   * Returns the {@link WorkspaceEntry} with the matching name identifier. If not found,
   * return null.
   * @param name search {@code String}.
   * @return the {@code WorkspaceEntry} matching the search name, or {@code null}.
   */
  public WorkspaceEntry search(String name){
    try {
      return workspaceEntryMap.get(name);
    } catch (Throwable e) {
      System.out.println("Entry not found");
      return null;
    }
  }

  /**
   * Returns the {@link WorkspaceEntry} with the matching name identifier. If not found,
   * creates a new entry of the correct type, adds it to the {@link Workspace}, and
   * returns the newly created object.
   * @param name {@code String} name of the entry
   * @param entryType {@code String} description of the entry type
   * @return a {@code WorkspaceEntry} object that is found in the workspace or newly
   * created.
   */
  public WorkspaceEntry searchAndAddIfAbsent(String entryType, String name) {
    WorkspaceEntry ret = search(name);
    WorkspaceEntry newEntry;
    if (ret == null) {
      if (entryType.equals("Function")) {
        System.out.printf("Creating function %s in workspace\n", name);
        newEntry = new SLogoFunction(name);
        add(newEntry);
      } else if (entryType.equals("Variable")) {
        System.out.printf("Creating variable %s in workspace\n", name);
        newEntry = new SLogoVariable(name);
        add(newEntry);
      } else {
        System.err.println("You can only add Variable or Function objects in the workspace");
        return null;
      }
      return newEntry;
    }
    return ret;
  }
}