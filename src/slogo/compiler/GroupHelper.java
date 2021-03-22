package slogo.compiler;

import slogo.compiler.token.SLogoList;

/**
 * This class reads from a properties file to determine how to handle extra parameters for a
 * given command. The "types" of commands are as follows:
 * 1. Stackable: command calls should be stacked one after the other using the appropriate
 * number of parameters each time. An error should be thrown if an incorrect multiple of parameters
 * is given. Examples: {@code fd 20 30 40} becomes {@code fd 20 fd 30 fd 40}, and {@code towards
 * 10 10 20 20} becomes {@code towards 10 10 towards 20 20}
 *
 * 2. Nestable: command calls should be nested in one another so the parameters are all 'computed'
 * at once. Examples: {@codw sum 10 20 30} becomes {@code sum sum 10 20 30}, and {@code and 10 20 30}
 * becomes {@code and and 10 20 30}
 *
 * 3. No parameter: any parameters given should be ignored. Examples: {@code pd, pi, turtles}
 *
 * 4. Equal: the Equal command is a special case. In order to correctly return whether all parameters
 * given are equal, Equal must be run on every pair of parameters, and all of these results must be
 * And'ed together
 *
 * 5. NotEqual: the NotEqual command is also a special case. In order to correctly return whether all
 * parameters given are not equal, NotEqual must be run on every pair of parameters, and all of these
 * results must be Or'ed together
 */
public class GroupHelper {
  private SLogoList groupList;

  public GroupHelper(SLogoList tokenList) {
    groupList = tokenList;
  }

  public 
}
