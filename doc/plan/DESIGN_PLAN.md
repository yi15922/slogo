# SLogo Design Plan
### Team Number
3
### Names
Patrick Liu (pyl8)
Liam Idrovo (lai3)
Kenneth Moore III (km460)
Yi Chen (yc311)

## Introduction
Our team is trying to create a user interface that lets users run basic SLogo commands and craft programs. The commands directly interact with a turtle on the screen. It should be flexible on the front-end to support a variety of languages and styling options, and flexible on the back-end to support more complex turtle commands and data structures. Hence, the structure of a command and how the turtle's position is changed should be closed for modification, but the commands should be open for extension in order to support new commands. In essence, text entered by the user in the GUI should be passed to a compiler that parses through the String and calls the appropriate commands in the back-end, which then updates the turtle position/orientation in the front-end.

## Overview
* `slogo.model.Turtle`: the model of the application, also the backend external API. The backend updates the turtle's state through this class, and the front end also interfaces to update the visual state of the turtle.
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

* `SLogoRunnable`: an interface that marks an object as runnable and expects 0 or more parameters.
    * `isReady()`: tells whether the `SLogoRunnable` is ready to be run, this requires it to have all its expected tokens.
    * `giveNextExpectedToke()`: passes a `Token` as a parameter.
    * `run()`: runs the runnable and returns a result.

* `Command`: a abstract class that extends `Token` and `SLogoRunnable`. `Command` is a backend internal API that backend programmers can use to create new commands that are recognized by the `Parser`
    * Concrete implementations will override the `run()` method to perform the function of the command, potentially by updating the state of the `slogo.model.Turtle` by calling its API. `run()` is called at runtime and will return the return value of the given `Command`.
    * Contains instance variables `commandName`and `numberOfExpectedTokens`. `commandName` is the command token user writes to call the command, and `numberOfExpectedTokens` is the number of parameters expected by each command.
    * Has direct access to `Workspace` to access or create `WorkspaceEntry` objects in the workspace.

* `Compiler`: Gets `Token`s from `Parser` and creates `Function` objects with these tokens.
    * Also identifies syntax errors in user entered code by checking against its internal `Map` of allowed commands.

* `Parser`: Used by the compiler to package user inputs into `Token` objects of the correct type.
    * Has standard scanning methods like `getNextToken()` and `hasNextToken()`.
    * Detects using regex the type of `Token` to create tokens of the correct types (possibly using a _Factory_ design pattern).
    * Checks each `String` input against a list of default command strings.
        * If no match, create a generic `Token` holding the string. This can later be used as a user defined `Function` name or a `Variable` name.
        * If there is a match, constructs the corresponding `Command` subclass (e.g. `ForwardCommand`).
    * Detects end of user input
    * Detects the beginning of lists and creates `List` objects.
    * Handles white spaces


* `Function`: extends `SLogoRunnable` and `WorkspaceEntry`, holds a collection of `Token` objects.
    * Is constructed by passing it a collection of `Token`
    * Overrides `run()`: attempts to go through all its `Token` objects in order, calling `run()` on each token of type `SLogoRunnable`.
    * `run(runnable)`: calls run() recursively to take care of nested `SLogoRunnable`s:
        * If `runnable.isReady()` is true, call `run()` on the it and return the return value.
        * If runnable is not ready, call `getNextToken()` and check what type of token it is.
        * If it is type `Constant`, pass it to the `SLogoRunnable` using `runnable.giveNextExpectedToken()` and make recursive `run(runnable)` call on it again.
        * If the next token is a generic `Token`, check the `Workspace` and get the concrete token type. If the type is `SLogoRunnable`, make recursive `run()` call on the inner runnable, passing the return value to the outer runnable using `runnable.giveNextExpectedToken()`.
    * Can be added as a searchable entry in the `Workspace`.

* `Variable`: extends `WorkspaceEntry`
    * Holds a `int` value and `String` name and can be queried using `getValue` and `setValue`.
    * Can be added as an entry to the `Workspace`


* `Workspace`: Keeps a collection of user defined `WorkspaceEntry` objects
    * `add()`: adds the passed `WorkspaceEntry` object.
    * `search()` returns the `WorkspaceEntry` by name and returns the respective objects. If not found, returns `null`.
    * Communicates with `WorkspaceViewController` to update the variable display in the workspace view.


* `View`: extends `PanelView`, extended by `TurtleView`, `ConsoleView`, `WorkspaceView`. `View` is a front-end internal API because it will be used by other front-end programmers to create the panels in the GUI.
    * Each subclass has a corresponding controller extending `ViewController`, which is the front-end external API.
    * View will not communicate with controller when interpreting input affecting visuals such as turtle image, background color, and pen color, since the Model has no need to interact with such features.

## User Interface

## Design Details

We will be utilizing the [Command design pattern](https://www.oodesign.com/command-pattern.html) to set up communication between Compiler and slogo.model.Turtle. The reasons we chose this design are the following:

- Command interface allows easy addition of concrete commands to program. All that is needed is write a concrete command extending command interface, instantiate it in Commands class.
- Commands can be queued in Function.

The Command interface holds a `commandName` instance variable because it isolates the assocation of a certain token with a command to the command's class. The great thing about this is that the only thing that must be adjusted to change the token associated with a command is that command's `commandName`. The `commandName` is loaded as a key in the HashMap returned by Commands `loadCommands()` static method. This means that Compiler will always be comparing user-inputted tokens directly with the tokens assigned to `commandName`. This obeys the open/closed principle because the Compiler class is closed to modification, but open to extension when creating a new command by implementing Command interface with a new command class. The only modification required is the instantiation of this new class in the Commands class.
## Test Plan
We will conduct both positive tests, what a good user would do, and negative tests which try to break the program.  These test will see if program can handle invalid info / edge cases.

Strategies to make API's more readable
* Test benches (a whole bunch of tests put together) for functionality that has chance at going wrong
* Useful parameter and return values for tests to be easy to run
* Throw exceptions so that we know when and where the program goes bad

Tests for slogo.model.Turtle Feature
* Check that turtle moves
    * check if it moves in x direction
        * expected outcome: x internal state is updated
    * check if it moves in y direction
        * expected outcome: y interanl state is updated
    * give direction for out of bounds
        * expected outcome: throws Exception
* Check that turtle turns
    * make sure turtle turns appropriate number of degrees
        * expected outcome: direction internal state is updated
        * make sure turtle informs view it has changed
        * turtle makes call to controller using what just learned in class
* Check turtle pen
    * ensure pen can turn on and off
* Check turtle show
    * ensure turtles show state can change

Tests for Commands
There should be a test for each command supported by SLogo to test its proper function. Each command should be accompanied by positive tests that a typical user might enter, and negative tests that may be syntactically correct but may throw a runtime error. Three specific test scenarios are detailed below:
* HIDETURTLE and SHOWTURTLE
    * A test passes the commands "HT ST HT ST" to the Parser
    * The Compiler should create a separate Function for each Command since they take no parameters
    * After each Function is run, the tester will assert that the visual state of the turtle (hidden or shown) is correct, and that the return value of the Command is correct (0 for HT, 1 for ST)
* LESS?
    * A test contains many possible forms of a LESS? comparison and their expected result, including:
        * Two constants (i.e. LESS? 1 2)
        * Two variables (i.e. LESS? x y)
        * One constant, one variable (i.e. LESS? x 2)
    * After each Function is run, the tester will assert that the return value matches the expected result
* FORWARD (negative test case)
    * A test passes the command "FD x" to the Parser, where x is a number greater than either dimension of the allowable turtle space
    * The Command will attempt to update the slogo.model.Turtle's position, but should throw an exception after learning that the new position is out of bounds (i.e. it is an illegal action)
    * The tester will assert that the Command threw the proper exception

Tests for View
The front-end of this project should accurately display the state of the turtle and give users the option to style certain aspects of the GUI.
* Set Background Color
    * A test would simulate a click from the user attempting to change the background color, followed by a click on each available color.
    * The tester will assert that the property BackgroundColor in the appropriate Panel is set to the expected color after each click
* Set slogo.model.Turtle Image
    * A test would simulate a click from the user attempting to change the turtle image, followed by a click on each available image.
    * The tester will assert that the Image variable used to display the turtle is set the expected value after each click
* See Available Variables
    * A test would create variables with varying names and values (i.e. x = 25, probabilityOfRain = 80, metersInGigaMeters = 1000000000)
    * The test would consist of user commands of the form 'make expr value'
    *  After each command, the tester will assert that the WorkspacePanel contains the corresponding variable and that its name and value are displayed correctly

## Design Considerations
A controller class could possibly be added later in the communication flow between View and Compiler if non-text user input that affects the model is implemented at a later point. However, for our Basic implementation we will assume that the only interaction the user has with the slogo.model.Turtle comes in the form of text input, and the basic styling options that should be on the GUI.

Another design decision we discussed at length was the responsibilites and communication between the Compiler and Function. Currently, our plan is to have the Compiler, given a list of Tokens from the Parser, create Functions with one or more Commands that can "run" themselves. Essentially, when the Compiler encounters a Command token, it creates a new Function object with the remaining tokens, and the Function is responsible for parsing through the remaining tokens to run the given Command. This structure takes advantage of recursion to handle nested commands/functions.
One alternative we strongly considered was having the Compiler create Stacks of Commands for each runnable Command, and then running the Commands in order. For example, a nested command like 'fd fd 50' would result in a Stack with the inner 'fd 50' command on top. The Compiler would then have more responsibilities, since it is in charge of turning the given tokens into a form that is runnable. This approach has the advantage of being easier to visualize, since Stacks of Commands are a helpful way to picture nested commands. However, we ultimately decided this approach gave the Compiler too much responsibility. We are consciously separate the Compiler from knowledge of what specific Commands need to run.

## Team Responsibilities

* Yi - Parser & Compiler, communication between Compiler and Function

* Liam - View (the three panels), communication between each View and its respective controller

* Patrick - Token inheritance hierarchy (Command, Variable, Constant, List, Comment) and Function, communication between Compiler and Function

* Kenneth - slogo.model.Turtle and controllers for the View panels 