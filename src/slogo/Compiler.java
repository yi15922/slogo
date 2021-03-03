package slogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.web.HTMLEditorSkin.Command;

/**
 * Parses user input to check if it is valid Slogo syntax. If so, orders instance of
 * Function to execute corresponding Command implementations.
 */
public class Compiler {

    private Map<String, Command> myCommands;

    public Compiler() {
        myCommands = Commands.loadCommands();
    }

    /**
     * Extracts commands from user input and applies commands to turtle.
     *
     * @param input     user input
     * @return          empty string if input processed into commands succesfully
     *                  otherwise, string with first error marked is returned
     */
//    public String processInput(String input) {
//        String correctedInput = getCorrectedInput(input);
//        if (!input.equals(correctedInput)) return correctedInput;
//        String[] splitInput = splitInputIntoCommands(correctedInput);
//        Function function = new Function();
//
//        int parserPointer = 0;
//        while (parserPointer < splitInput.length) {
//            Command command = myCommands.get(splitInput[parserPointer++]);
//            reading command parameters
//            List<String> parameters = new ArrayList<>();
//            for (int i=1; i <= command.get(); ++i) {
//                parameters.add(splitInput[parserPointer++]);
//            }
//            command.setParameters(parameters);
//            function.addCommand(command);
//        }
//        return "";
//    }



    /**
     * Checks if user input is correct Slogo syntax.
     *
     * @param input     user-inputted commands
     * @return          true if input follows syntax rules.
     */
    private boolean isInputValidSyntax(String input) {
        return input.equals(getCorrectedInput(input));
    }

    /**
     * Finds incorrect Slogo syntax in input string and replaces first mistake caught with special
     * character to signify error. If input is correct syntax, original input String is returned.
     *
     * @param input     user input
     * @return          string with first error marked. If input has no errors,
     *                  original input returned.
     */
    private String getCorrectedInput(String input) {
        return null;
    }

    /**
     * Splits input by token into a string array. Should be called on input conforming to
     * valid Slogo syntax. However, method checks input in implementation once more and returns
     * string array of length 0 if input is invalid.
     *
     * @param input user input
     * @return      String array containing commands and parameters.
     *              If user input is invalid, string array of length 0 is returned.
     */
    private String[] splitInputIntoCommands(String input) {
        return new String[0];
    }
}
