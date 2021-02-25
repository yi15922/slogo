# API Lab Discussion
# SLogo API Design

## Names and NetIDs

* Yi Chen (yc311)
* Kenneth Moore III (km460)
* Patrick Liu (pyl8)
* Liam Idrovo (lai3)


### Planning Questions

* What behaviors (methods) should the turtle have?
    * `move`
    * `turn`
    * `penUp`
    * `penDown`

* When does parsing need to take place and what does it need to start properly?
    * When the user enters the code command.
    * It needs text entered into the text editor

* What is the result of parsing and who receives it?
    * A `Compiler` object (class that translates strings into commands) receives the result of the parsing

* When are errors detected and how are they reported?
    * Errors are detected by checking the user entered string in a map of valid commands - performed by the `Compiler`
    * Each `Command` also checks whether the parameters they're given conform to requirements.

* What do commands know, when do they know it, and how do they get it?
    * Objects that extend `Command` know what they do because they are implemented for their specific purpose and constructed when a parsed command is valid.
    * They they are initialized with the parameter the user gave the command
    * They communicate with the `Turtle` to update the turtle's state

* How is the GUI updated after a command has completed execution?
    * The View receives the turtle's next position, updates the position of the turtle on the screen, and then draws a line representing the path if the pen is on

* What behaviors does the result of a command need to have to be used by the front end?
    * The command needs to be valid in order to spawn a new `Command` subclass
    * The parameter needs to be valid for the `Command` to make a change to the `Turtle`
    * The result of the command should include the updated "state" of the turtle (position, orientation, whether or not the pen is on)

### APIs

#### Backend External API


#### Frontend External API
Main goals:
* Provide other programmers the functionality to update the turtle's visual representation on the screen (position, orientation)
* Provide the user the ability to enter and run commands
* Provide the user the ability to customize the turtle/background color
* Provide other programmers the ability to access the current workspace state

#### Backend Internal API
Main goals:
* Provide backend programmers the ability to add recognized turtle commands

#### Frontend Internal API



### Design

#### Backend Design CRCs


#### Frontend Design CRCs



#### Use Cases

* The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.

* The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.

* The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).

* The user changes the color of the environment's background.
