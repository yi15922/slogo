SLogo
====

This project implements a development environment that helps users write programs to draw using a turtle.

Names:  
Yi Chen  
Liam Idrovo  
Patrick Liu  
Kenneth Moore III

### Timeline

Start Date:  
02/25/2021

Finish Date:  
03/22/2021

Hours Spent:  
40 hours per person, 160 hours total

### Primary Roles
* Yi: Parser, Compiler, Workspace, and View (implementing turning String input into a Token queue, multiple windows, and the menu bar)
* Liam: View (implementing Basic windows, user-friendly settings, and Observable interface to get turtle updates)
* Patrick: Model (implementing SLogoCommand and subclasses, SLogoToken and subclasses, SLogoFunction and underlying logic)
* Kenneth: Model, Controller (implementing Turtle and SingleTurtle, observers to notify the View of changes)

### Resources Used
Many Oracle help pages were consulted in the making of this project.  
We also used the [Composite design pattern](https://refactoring.guru/design-patterns/composite) to support multiple turtles.

### Running the Program

Main class:
Main (in the slogo folder)

Data files needed:
All data files needed are in the data and resources folder. **In order to run the project, you must mark the resources folder as Resources Root.**

Features implemented:
* Text input recognizes all Basic and Complete commands. User can enter multiple commands in a line and SLogo will run them one at a time. User can also define their own commands.
    * Commands can be nested. For example, `fd fd 50` will move forward by 100.
    * Commands can create variables and place them in the workspace, which can then be accessed by future commands.
    * Users can define their own commands, which can take in parameters. These commands can then be called later.
    * Users can give commands unlimited parameters, but each command handles these parameters differently. These behaviors are detailed in the Notes/Assumptions section.
* An alert will appear on screen when a command has invalid syntax.
* There is a UI with turtles spawning in the center. Turtles move and rotate based on commands entered by the user. Turtles can enable or disable their pen, which draws a line underneath their path.
* Users can see previously run commands and all available variables in the workspace.
* Users can change various graphical elements of the program, such as background color or pen color.
* Users can change the language of the program.
* Users can click on a turtle to learn more about it.
* Users can browse through the menu bar (a la Apple) for convenient access to settings.


### Notes/Assumptions

Assumptions or Simplifications:
* All commands can theoretically take in unlimited parameters, but how they use these parameters differs. Commands boil down to five types:
    * Stackable: command calls are stacked one after the other using the appropriate number of parameters each time. An error is thrown if an incorrect multiple of parameters is given. Examples: `fd 20 30 40` is equivalent to `fd 20 fd 30 fd 40`, and `towards 10 10 20 20` is equivalent to `towards 10 10 towards 20 20`.
    * Nestable: command calls are nested in one another so the entire command is 'computed' at once. Examples: `sum 10 20 30` is equivalent to `sum sum 10 20 30`, and `and 10 20 30` is equivalent to `and and 10 20 30`.
    * No parameters: any parameters given are ignored. Examples: `pd, pi, turtles`.
    * Equal: the Equal command is a special case. In order to correctly return whether all parameters given are equal, Equal is run on every pair of parameters, and all of those results are placed in nested And commands. Example: `equal? 1 2 3` is equivalent to `and and equal? 1 2 equal? 1 3 equal? 2 3`.
    * NotEqual: the NotEqual command is also a special case. In order to correctly return whether any parameters given are not equal, NotEqual is run on every pair of parameters, and all of those results are placed in nested Or commands. Example: `notequal? 1 2 3` is equivalent to `or or notequal? 1 2 notequal? 1 3 notequal? 2 3`.
* Some commands were deemed to be stackable to make implementing extra parameters easier (i.e. `less?`).

Interesting data files:  
We did not use data files to run SLogo programs, but we could implement that by using a Scanner to read from a file and pass lines in to the Compiler.

Known Bugs:
* Some commands related to angles return values that are slightly off due to rounding.

Extra credit:


### Impressions
This assignment was quite challenging and required a lot of attention to detail. Learning reflection earlier in the project would have helped us get off the ground quicker. 
