# SLogo Design Plan
### Team Number
3
### Names
Patrick Liu (pyl8)
Liam Idrovo (lai3)
Kenneth Moore III (km460)
#### Examples

Here is a graphical look at my design:

![This is cool, too bad you can't see it](online-shopping-uml-example.png "An initial UI")

made from [a tool that generates UML from existing code](http://staruml.io/).


Here is our amazing UI:

![This is cool, too bad you can't see it](29-sketched-ui-wireframe.jpg "An alternate design")

taken from [Brilliant Examples of Sketched UI Wireframes and Mock-Ups](https://onextrapixel.com/40-brilliant-examples-of-sketched-ui-wireframes-and-mock-ups/).


## Introduction
Our team is trying to create a user interface that lets users run basic SLogo commands and craft programs. The commands directly interact with a turtle on the screen. It should be flexible on the front-end to support a variety of languages and styling options, and flexible on the back-end to support more complex turtle commands and data structures. Hence, the structure of a command and how the turtle's position is changed should be closed for modification, but the commands should be open for extension in order to support new commands. In essence, text entered by the user in the GUI should be passed to a compiler that parses through the String and calls the appropriate commands in the back-end, which then updates the turtle position/orientation in the front-end.

## Overview
* `Turtle`: the model of the application, also the backend external API. The backend updates the turtle's state through this class, and the front end also interfaces to update the visual state of the turtle.
    * Keeps track of its current position, orientation, pen state and visibility state
    * Keeps a record of its history (all positions it had been to)
    * Has states that can be updated such as:
        * `position`
        * `orientation`
        * `penUp`
        * `isVisible`
    * Has methods such as:
        * `move()`
        * `turn()`
        * `penUp()`
        * `visibility()`
* `Token`: an interface that is extended by `Command`, `Constant`, `Var`, `List`, `Comment`. The `Compiler` converts user entered strings into these objects.

* `Command`: an interface that makes a change to the `Turtle`. `Command` is a backend internal API that backend programmers can use to create new commands that are recognized by the `Parser`
    * Has a `perform()` method that performs the function of the command, potentially by updating the state of the `Turtle` by calling its API. `perform` is called at runtime and will return the return value of the given `Command`.
    * Has `giveNextExpectedToken()` which passes a `Token` to the next token the command expects.
    * `isReady()`: tells whether the command is ready to be run, this requires it to have all its expected tokens.
    * Once a `Command` object has all its required tokens, it will be executed and the return value passed to the next command in the stack in the case of nested commands.
    * Contains instance variables `commandName`and `numberOfExpectedTokens`. `commandName` is the command token user writes to call the command, and `numberOfExpectedTokens` is the number of parameters expected by each command.
    * Has direct access to `Workspace` to access or create `Variable` and `Function` objects in the workspace.

* `Compiler`: Gets `Token`s from `Parser` and creates `Function` objects with these tokens.
    * Also identifies syntax errors in user entered code by checking against its internal `Map` of allowed commands.
    * Detects the beginning and end of a user defined function to determine when to create new `Function` objects.

* `Parser`: Used by the compiler to package user inputs into `Token` objects of the correct type.
    * Has standard scanning methods like `getNextToken()` and `hasNextToken()`.
    * Detects using regex the type of `Token` to create tokens of the correct types.
    * Checks each `String` input against a list of default command strings.
        * If no match, create a generic `Token` holding the string. This can be used as a user defined `Function` name or a `Variable` name.
        * If there is a match, constructs the corresponding `Command` subclass (e.g. `ForwardCommand`).

    * Detects end of user input
    * Detects the beginning of lists and creates `List` objects.

    * Handles white spaces


* `Function`: Holds a collection of `Token` objects
    * Is constructed by passing it a collection of `Token`
    * `run()`: attempts to go through all its `Token` objects in order, calling `runCommand()` on each token of type `Command`.
    * `runCommand(command)`: runs the command recursively to take care of nested commands:
        * If `Command.isReady()` is true, call `perform()` on the command and return the return value.
        * If command is not ready, call `getNextToken()` and check what type of token it is.
        * If it is the expected type (`Variable` or `Constant`), pass it to the `Command` using `Command.giveNextExpectedToken()` and make recursive `runCommand(command)` call on it again.
        * If the next token is another `Command`, make recursive `runCommand()` call on the inner command, passing the return value to the outer command using `Command.giveNextExpectedToken()`.

* `Variable`: holds a `int` value and `String` name and can be queried using `getValue` and `setValue`.


* `Workspace`: Keeps a collection of user defined `Variable` and `Function`
    * `Variable`s can be added using `addVariable()`.
    * `Function` can be added using `addFunction()`.
    * Calling `getVariable()` or `getFunction()` gets them by name and returns the respective objects. If not found, throw an exception.
    * Communicates with `WorkspaceViewController` to update the variable display in the workspace view.


* `View`: extends `PanelView`, extended by `TurtleView`, `ConsoleView`, `WorkspaceView`. `View` is a front-end internal API because it will be used by other front-end programmers to create the panels in the GUI.
    * Each subclass has a corresponding controller extending `ViewController`, which is the front-end external API.
    * View will not communicate with controller when interpreting input affecting visuals such as turtle image, background color, and pen color, since the Model has no need to interact with such features.

## User Interface
View guy

## Design Details

We will be utilizing the [Command design pattern](https://www.oodesign.com/command-pattern.html) to set up communication between Compiler and Turtle. The reasons we chose this design are the following:

- Command interface allows easy addition of concrete commands to program. All that is needed is write a concrete command extending command interface, instantiate it in Commands class.
- Commands can be queued in Function.

The Command interface holds a `commandName` instance variable because it isolates the assocation of a certain token with a command to the command's class. The great thing about this is that the only thing that must be adjusted to change the token associated with a command is that command's `commandName`. The `commandName` is loaded as a key in the HashMap returned by Commands `loadCommands()` static method. This means that Compiler will always be comparing user-inputted tokens directly with the tokens assigned to `commandName`. This obeys the open/closed principle because the Compiler class is closed to modification, but open to extension when creating a new command by implementing Command interface with a new command class. The only modification required is the instantiation of this new class in the Commands class.
## Test Plan
We will conduct both positive tests, what a good user would do, and negative tests which try to break the program.  These test will see if program can handle invalid info / edge cases.

Strategys to make API's more readable
* Test benches (a whole bunch of tests put together) for functionality that has chance at going wrong
* Useful parameter and return values for tests to be easy to run
* Throw exceptions so that we know when and where the program goes bad



## Design Considerations
A controller class could possibly be added later in the communication flow between View and Compiler if non-text user input that affects the model is implemented at a later point.
## Team Responsibilities

* Team Member #1

* Team Member #2

* Team Member #3

* Team Member #4