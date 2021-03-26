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


## Design Goals
- Create a functioning SLogo implementation that fulfills all of the basic requirements and most of the complete requirements. 
- Have a robust compiler that displays informative error messages on user errors. 
- Have a program that is data driven and easily extensible (e.g. new commands, new menu options, views, etc.)
- Have a well designed system that follows design principles we have learned so far. 


## High-Level Design
TODO: most of what we wrote in our DESIGN_PLAN document can be added here. 

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