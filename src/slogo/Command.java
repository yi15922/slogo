package slogo;

import java.util.List;

public interface Command {

    void perform();

    //List data structure chosen to allow flexibility of
    //parameter type
    void setParameters(List<String> parameters);

    String getCommandName();

    int getNumberOfParameters();

}
