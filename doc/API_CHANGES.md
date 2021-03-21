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
      Major change that severely affects the Turtle class, since the Turtle needs to be able to apply the given instruction on every SingleTurtle.

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

* Method changed:

    * Why was the change made?

    * Major or Minor (how much they affected your team mate's code)

    * Better or Worse (and why)


* Method changed:

    * Why was the change made?

    * Major or Minor (how much they affected your team mate's code)

    * Better or Worse (and why)

