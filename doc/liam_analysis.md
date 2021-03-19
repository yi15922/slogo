
##Design Considerations

###InputLog
We decided to have the input log display any user input, regardless of if it's correct syntax.
We made this design decision so that the user can copy their invalid input from the log and paste
it into the input console to correct it.

###InputConsole

Created InputConsole class extending JavaFX TextArea modifications could be 
done to the input console on the GUI without changing code inside View, thereby leaving
View open but closed.

Added listeners to the input button so that potential future components requiring user input can be added 
without modifying InputConsole.

##Resources used 

[comment]: <> (- [Accessing private methods and variables for unit testing]&#40;https://stackoverflow.com/questions/34571/how-do-i-test-a-private-function-or-a-class-that-has-private-methods-fields-or&#41;)

