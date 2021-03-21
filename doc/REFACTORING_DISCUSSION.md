## Refactoring Lab Discussion
### Team 03
### Names
 - Kenneth Moore III (km460)
 - Liam Idrovo (lai3)
 - Patrick Liu (pyl8)
 - Yi Chen (yc311)


### Issues in Current Code

#### Multiple Turtles Implementation
* Originally we were planning to pass a collection of `Turtle` objects to each command. However, this would require all `Command` objects to loop through the collection of turtles when performing actions.

* To refactor, use the composite design pattern and create a class that controls all active turtles.

#### Looping Commands
* There is code duplication in the Command subclasses that loop through a sequence of commands, such as For, DoTimes, and Repeat.

* To refactor, I can extract the duplicated code into a helper class (LoopCommand) that takes in fives arguments: start, end, increment, a list of commands, and a counter variable. The for loop will pass the first three arguments easily, and DoTimes and Repeat will have to calculate them.

#### Top level `View` code
* The main currently assumes that the application will only have one window.
* Create a new top level `Main` class that can create multiple stages and therefore multiple windows.


### Refactoring Plan

* What are the code's biggest issues?
    - Make GUI data driven with variables read from data files.
    - Refactor Turtle into SingleTurtle and TurtleGroup to have single method calls communicate with all individual turtles.
    - Make application support multiple windows

* Which issues are easy to fix and which are hard?
    - Supporting multiple windows is easy because one simply needs to create new instances of the entire stage for the application.
    - It is slightly more challenging to implement support for multiple turtles.

* What are good ways to implement the changes "in place"?
    - We replace methods in `Turtle`, which is currently a single turtle, with methods that control a collection of `SingleTurtle` objects, so that the only changes needed are in these two classes and nothing in the rest of the code should not need to change.


### Refactoring Work

* Issue chosen: Fix and Alternatives
    * We are going to use the Composite Design Pattern to fix the multiple turtles pattern.  As previously stated, our initial design was to have a list of turtles passed around.  Once we learned there were better ways, we debated a couple of options. One option was to not use the interface and just have composite turtle extend turtle.  This was decided against because it does not follow the design pattern.  We also considered having the interface as an object.  This was deciced against because there would be a lot of repetion / no difference between the turtle super class and individual turtle sub class.


* Issue chosen: Fix and Alternatives
