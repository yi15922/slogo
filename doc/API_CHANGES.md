## SLogo API Changes
### Team 03
### Names
- Kenneth Moore III (km460)
- Liam Idrovo (lai3)
- Patrick Liu (pyl8)
- Yi Chen (yc311)


### Changes to the Initial APIs

#### Backend External

* Method changed: we are refactoring our Turtle class into two classes, Turtle and SingleTurtle. SingleTurtle will contain all the methods from our original Turtle class and will represent an individual turtle. Turtle will act as a composite, storing a Collection of SingleTurtle objects. When a method in Turtle is called to update turtle states, Turtle will loop through and perform the specified action on all active SingleTurtles.

  * Why was the change made?

    To better follow the Composite design pattern. Also, refactoring mitigates the need to change every Command subclass that interfaces with the turtle, since looping through the collection of turtles would be contained within the Turtle class and the commands do not need to have knowledge of the data structure.

  * Major or Minor (how much they affected your team mate's code)

    Major changes to the `Turtle` class, since the Turtle class is now "migrated" to the `SingleTurtle` class. However no change needs to be made to any other classes.

  * Better or Worse (and why)

    Better: as Duvall said in lecture, this change helps us avoid code duplication in the Command subclasses.


#### Backend Internal

* Two methods were added to `SLogoCommand`: attachTurtle() and resetCommand(). The former allows Commands to actually perform their specified functionality and affect the model. The latter allows the same instance of a command to be run multiple times with different arguments, such as in a loop.

  * Why was the change made?
    * These were methods that we did not think of when we were making our plan. We did not consider how the commands would directly affect the model, which was a misstep. The necessity of resetting commands did not come up until we were implementing loops.

  * Major or Minor (how much they affected your team mate's code)
    * Minor: these changes only affected the backend internal classes, since they are only called within the Compiler and Function.

  * Better or Worse (and why)
    * Better: these methods allowed for loops to work without a significant amount of additional code.


* Two major methods were added to `SLogoFunction`: runSingleCommand() and disableExecution(). The former runs the first command in the queue and stops, returning that one command's result. The latter prevents commands from actually executing.

  * Why was the change made?
    * Again, curveballs thrown by commands as they were being implemented. We needed to be able to run a single command to evaluate parameters given for a loop, for example, since our Function run() method runs all commands given in a collection of tokens. Disabling execution helps user-defined commands check if the syntax is correct without affecting the model.

  * Major or Minor (how much they affected your team mate's code)
    * Minor: these changes only affected other backend internal classes.

  * Better or Worse (and why)
    * Better: these methods should have been included to meet all specifications, but were edge cases and so were not considered during the planning phase.


#### Frontend External

* Method changed:

  * Why was the change made?

  * Major or Minor (how much they affected your team mate's code)

  * Better or Worse (and why)


* Method changed:

  * Why was the change made?

  * Major or Minor (how much they affected your team mate's code)

  * Better or Worse (and why)


#### Frontend Internal

* Method changed: `OutputScreen`

  * Why was the change made?

  The class originally constructs a single `Turtle` object and observes it, manipulating an `ImageView`. However, to support multiple turtles being displayed, we created a new `TurtleView` class that extends `ImageView`, which makes each turtle a clickable object. Further more, now the individual `TurtleView` classes are observers to individual turtles, so they are now active objects that will reposition themselves.

  * Major or Minor (how much they affected your team mate's code)

  Minor change, des not affect any classes other than the two involved: `TurtleView` and `OutputScreen`.

  * Better or Worse (and why)

  Better: multiple turtle views are now supported without the need for `OutputScreen` to loop through them to update individually.


* Method changed:

  * Why was the change made?

  * Major or Minor (how much they affected your team mate's code)

  * Better or Worse (and why)

