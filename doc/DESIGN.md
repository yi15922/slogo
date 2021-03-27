# DESIGN Document for SLogo
## NAME(s)
- Kenneth Moore III (km460)
- Liam Idrovo (lai3)
- Patrick Liu (pyl8)
- Yi Chen (yc311)

## Role(s)

### Yi Chen
- `Parser` and related classes
- `Compiler` and related classes
- `Workspace` and related classes
- `SLogoRunnable` interface
- Some `SLogoToken` classes
- `MenuBar` and properties files required
- `TurtleView` and view support for multiple clickable turtles
- `Palette`
- Multi-window support
- Ability for users to open existing `.slogo` files in a new window
- All unit tests for above classes
- Contribution of ideas and algorithms to various backend components such as user input parsing and compiling, the `Token` protocols, and recursive running mechanism to handle nested commands.

### Liam Idrovo
- Observer interfaces in observers directory
- classes in view directory (except StatsDisplay and TurtleView)
- `Observable` interface
- `WindowAlert`

### Patrick Liu
- `SLogoToken` superclass and most subclasses
- `SLogoCommand` superclass and all subclasses
    - All basic and complete commands are supported
- `SLogoFunction` and the logic behind running a sequence of command, including nested commands
- Logic behind multiple turtle commands, including handling ID numbers of different turtles
- Grouping, allowing commands to take in unlimited parameters, as handled by the `GroupHelper` class


## Design Goals
- Create a functioning SLogo implementation that fulfills all of the basic requirements and most of the complete requirements.
- Have a robust compiler that displays informative error messages on user errors.
- Have a program that is data driven and easily extensible (e.g. new commands, new menu options, views, etc.)
- Have a well designed system that follows design principles we have learned so far.
- Support all commands in the specification and allow users to create their own, whether by defining one in the console or programming their own class


## High-Level Design
The back-end of the program is comprised of the following components: the parser,
the compiler, slogo functions, and the turtle.
The parser, represented by the Parser class, checks if input is valid syntax and makes
slogo tokens out of valid input. The parser relies on input from the graphical console. Thus,
its observers data from the InputConsole class. A slogo token is a representation of potential
valid types of user input, such as constants and commands. The compiler, represented by
the Compiler class, then packages these tokens into a function, represented by the SlogoFunction
class. A function is essentially a collection of valid commands and their arguments. The Function class
does the work of running the commands it has been given. Every command has a class representing it, and
every command is a subclass of SLogoCommand, meaning that every command implements the run() method. When
the Function class calls a command's run() method, the command then calls the appropriate methods in Turtle, thus modifying the model.  
`SLogoFunction` contains the meat of the logic behind running commands, assembling a queue of all given tokens and going through them, feeding parameters to commands and then running them as soon as they are ready. Nested commands are evaluated so that their results can be fed to outer commands. In this way, our SLogo implementation catches most errors at runtime, since the syntax of commands is not checked until the command is run.  
All `SLogoCommand` subclasses are a good example of polymorphism, in that any command can be substituted for another. The difference between each subclass is the number of parameters it takes in and the functionality of the command when it is run. However, using reflection, the creation and passing of commands to other classes is extremely simple.

The front-end is centralized on the View class, which assembles all required visual components,
passes the required parameters to them, and adds them to the stage. Every visual element is represented by
its own class e.g. InputConsole for the text area where user can input commands. The manner in which
these elements are arranged is decided in View's startProgram(). The observer design pattern
was used to transfer data between classes representing visual elements and the program's model.
Whenever class A needed to be updated on new information in class B, the following steps were taken:

1. Class B implements the Observable interface, giving it the ability to hold observers of a specific type.
2. Class A implements a custom "observer" interface e.g. InputObserver found in observers  directory.
3. Custom notification methods are declared in the observer interface and implemented in class A.
4. Class B adds class A as an observer.
5. Class B notifies A of new data by calling methods defined in the custom observer interface.

## Assumptions or Simplifications
### Parser
- All tokens will be separated by a space character, with the exception of the "#" before a comment line.
- All variables declared, even if temporary (such as in user defined command declaration) will be added to the workspace and will remain there permanently.
- All user input must conform to the regex expression of one subclass of `SLogoToken`, otherwise it is a syntax error.


### Compiler
- All user input tokens in each compiling session will be used to create exactly one runnable `SLogoFunction` instance.
- Runtime errors occur while running, so the code will execute and interact with the turtle up until the point where the error occurs.

### Workspace
- Only objects that extend `WorkspaceEntry` can be added to the workspace.
- There is no way to remove an entry from the workspace
- Overwriting of an entry in the workspace does not change the object reference, simply its value. Therefore, commands relying on changes in variables in the workspace will immediate get any changes made to the variable.

### OutputScreen
The assumption that a turtle would be allowed to wander off-screen without affecting the way the
model ran or the GUI updated. This greatly simplified the design of the GUI.

### Functions/Commands
- Commands do not throw an error if the turtle moves off screen
- Users are not allowed to name a variable "ID," since in order to get the ID command working we create a variable with that name
- Grouping for less/greater than commands just returns the result of the last two inputs (aka it doesn't check if each parameters is less/greater than the next)

## Changes from the Plan
- We moved some functionality from the Function to the Parser, since we wanted the Function to know as little as possible about generic Tokens. The Function expects a collection of Tokens from the Compiler that are already parsed to their specific types (or a Variable/UserDefinedCommand if the name is not recognized)

## How to Add New Features

### New commands
- Create a new subclass of `SLogoCommand` and implement the command's actions.
    - Each parameter should be added in the constructor as an element in expectedParameters, with a helpful parameter name
    - The run() method should be overridden with the action performed by the command. The given parameters can be accessed in the expectedParameters list. The method should return a Token (doubles should be wrapped in a Constant token)
- Add the command file to the `slogo.compiler.command` package.
- Add the name of the command exactly as it appears in the class name of the newly created `SLogoCommand` class to the `.properties` files of all languages, with the format `CommandClassName=commandRegex`.

The parser will now be able to find this new command via the `.properties` files and reflection, and the command will be run when called.

### New token types
- Create a new subclass of `SLogoToken`.
- Add the new subclass to the `slogo.compiler.token` package.
- In the `Syntax.properties` file found in `src.resources.languages`, add a new entry with the format `TokenClassName=tokenRegex`.

Now the parser wil recognize the new token type when parsing rather than throwing an error.

### New views (e.g. for turtle stats, palette)
- Create a subclass of any displayable type in javafx for the new view and add it to the `slogo.view` package. If the view needs to update based on the model, have it implement the appropriate observer.
- Construct the class in the `View` class and add it to the scene.

### New menubar items
- In the `slogo.view.UIResources.properties` file, add a line for the new menubar item following the structure description found at the top of the file.
- Implement a method in `Main` that performs the action of the new menubar item, with a name matching the key added to the properties file.

When the program reloads, the new menubar item will appear with the correct action.


## Known Bugs
- The menu bar on MacOS is unresponsive unless the user switches to another app and then switch back to SLogo. This is a known Java bug in the `MenuBar` class.
- The setPalette command does not do anything because we did not implement changing palette colors. However, it still returns the correct value.
- New turtles created with the tell command appear in the top-left corner instead of the center of the screen. However, once they are moved they go to the correct location, as if they started in the center. 