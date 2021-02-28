# SLogo Design Plan
### Team Number
3
### Names
Patrick Liu (pyl8)
Liam Idrovo (lai3)
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
* `Turtle`: the model of the application
    * Keeps track of its current position and orientation
    * Keeps a record of its history (all positions it had been to)
    * Has states that can be updated such as:
        * `position`
        * `orientation`
        * `penUp`
    * Has methods such as:
        * `move()`
        * `turn()`
        * `penUp()`

* `Command`: an interface that makes a change to the `Turtle`.
    * Has a `perform` method that performs the function of the command, potentially by updating the state of the `Turtle` by calling its API.
    * Contains instance variables `commandName`and `numberOfParameters`. `commandName` is the command token user writes to call the command, and `numberOfParameters` is the number of parameters expected by each command.
* `Commands:` contains all possible command objects and loads them to Compiler
    * Contains instantiations of all objects implementing Command interface.
    * Contains static method `loadCommands()` that returns HashMap with command tokens as keys and command objects as values. Method is static so that instantiaion of Commands class is not required for Compiler to call `loadCommands()`.
* `Compiler`: parses user entered strings and creates `Command` objects
    * Also identifies syntax errors in user entered code
    * Passes each `Command` object created to `ProgramCounter`
    * Notifies `ProgramCounter` when parsing is done.
    * Identifies functions from `FunctionEditor`, and creates `Function` objects that with a collection of `Command`.
    * Also adds `Variable` objects to the `Workspace` if the user declares any.

* `Function`: extends `Collection` stores `Command` objects created by `Compiler` and runs them sequentially
    * Either using `LinkedList` or `ArrayList`
    * Can store one or more `Command` objects
    * has method `run()` that will call `perform()` on all its `Command`s in order.
    * A function can contain other `Functions`
* `Variable`: created when the user declares a new variable, can hold any data type.
* `WorkSpace`: Keeps track of user defined `Variables`
    * `Variables` can be checked and updated using methods `updateVariable()` and `getVariable()`.
    * `Variable`s can be added using `addVariable()`.
* `View`: extends `PanelView`, extended by `TurtlePanelView`, `ConsolePanelView`, `FunctionPanelView`.
    * Each subclass has a corresponding controller
    * View will not communicate with controller when interpreting input affecting visuals such as turtle image, background color, and pen color, since the Model has no need to interact with such features.

## User Interface
View guy

## Design Details

We will be utilizing the [Command design pattern](https://www.oodesign.com/command-pattern.html) to set up communication between Compiler and Turtle. The reasons we chose this design are the following:

- Command interface allows easy addition of concrete commands to program. All that is needed is write a concrete command extending command interface, instantiate it in Commands class.
- Commands can be queued in Function.

The Command interface holds a `commandName` instance variable because it isolates the assocation of a certain token with a command to the command's class. The great thing about this is that the only thing that must be adjusted to change the token associated with a command is that command's `commandName`. The `commandName` is loaded as a key in the HashMap returned by Commands `loadCommands()` static method. This means that Compiler will always be comparing user-inputted tokens directly with the tokens assigned to `commandName`. This obeys the open/closed principle because the Compiler class is closed to modification, but open to extension when creating a new command by implementing Command interface with a new command class. The only modification required is the instantiation of this new class in the Commands class.
## Test Plan
Group

## Design Considerations
A controller class could possibly be added later in the communication flow between View and Compiler if non-text user input that affects the model is implemented at a later point.
## Team Responsibilities

* Team Member #1

* Team Member #2

* Team Member #3

* Team Member #4