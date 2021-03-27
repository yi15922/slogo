package slogo.compiler.command;

import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

/**
 * Helper class that runs a given function multiple times, as determined by start, end, and
 * increment integers. This class is compatible with all looping SLogo commands: Repeat, Dotimes,
 * and For.
 */
public class LoopHelper {
  private int loopStart;
  private int loopEnd;
  private int loopIncrement;
  private SLogoFunction functionToRun;
  private SLogoVariable counterVariable;

  /**
   * Initializes the helper class with all parameters necessary to construct a for loop
   */
  public LoopHelper(int start, int end, int increment, SLogoFunction function, SLogoVariable counter) {
    loopStart = start;
    loopEnd = end;
    loopIncrement = increment;
    functionToRun = function;
    counterVariable = counter;
  }

  /**
   * Runs the given command list multiple times by constructing a basic for loop.
   * The counter variable, used by all loop commands in SLogo, is updated each run.
   * @return - result of final command run, or 0 if no commands are run.
   */
  public SLogoToken run() {
    SLogoToken returnToken = new SLogoConstant(0);
    for (int i = loopStart; i <= loopEnd; i += loopIncrement) {
      counterVariable.setValue(i);
      returnToken = functionToRun.run();
    }
    return returnToken;
  }
}
