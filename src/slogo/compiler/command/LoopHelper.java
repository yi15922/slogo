package slogo.compiler.command;

import javax.swing.event.MenuDragMouseListener;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

public class LoopHelper {
  private int loopStart;
  private int loopEnd;
  private int loopIncrement;
  private SLogoFunction functionToRun;
  private SLogoVariable counterVariable;

  public LoopHelper(int start, int end, int increment, SLogoFunction function, SLogoVariable counter) {
    loopStart = start;
    loopEnd = end;
    loopIncrement = increment;
    functionToRun = function;
    counterVariable = counter;
  }

  public SLogoToken run() {
    SLogoToken returnToken = new SLogoConstant(0);
    for (int i = loopStart; i <= loopEnd; i += loopIncrement) {
      counterVariable.setValue(i);
      System.out.println("Running inner function");
      returnToken = functionToRun.runFunction();
    }
    return returnToken;
  }
}
