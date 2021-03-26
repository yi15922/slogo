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

* Method changed:

    * Why was the change made?

    * Major or Minor (how much they affected your team mate's code)

    * Better or Worse (and why)


#### Backend Internal

* Method changed:

    * Why was the change made?

    * Major or Minor (how much they affected your team mate's code)

    * Better or Worse (and why)


* Method changed:

    * Why was the change made?

    * Major or Minor (how much they affected your team mate's code)

    * Better or Worse (and why)


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

