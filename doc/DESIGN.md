# DESIGN Document for PROJECT_NAME
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

###Liam Idrovo
- Observer interfaces in observers directory
- classes in view directory (except StatsDisplay and TurtleView)
- `Observable` interface
- `WindowAlert`


## Design Goals
- Create a functioning SLogo implementation that fulfills all of the basic requirements and most of the complete requirements. 
- Have a robust compiler that displays informative error messages on user errors. 
- Have a program that is data driven and easily extensible (e.g. new commands, new menu options, views, etc.)
- Have a well designed system that follows design principles we have learned so far. 


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
the Function class calls a command's run() method, the command then calls the appropriate methods in Turtle, thus
modifying the model.

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

###OutputScreen
The assumption that a turtle would be allowed to wander off-screen without affecting the way the
model ran or the GUI updated. This greatly simplified the design of the GUI.
## Changes from the Plan



## How to Add New Features

### New commands
- Create a new subclass of `SLogoCommand` and implement the command's actions. 
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