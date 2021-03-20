
##Design Considerations

####Observable

The intent of this interface is assure that its implementations have observers of the correct type.
For example, InputConsole implements Observable<InputObserver> so that it is guaranteed it will
have ways of adding and removing observers *of type InpuObserver*. That way, when implementations 
notify their observers of changes in data, they know what methods they can call because they are aware
of what type their observers are. 

Moreover, notification methods were not included in the Observable interface so that the individual
implementations of the interface can decide how to notify observers of the specified type. For example,
InputConsole and SlogoModel notify their observers of different things, therefore, they implement
different notify methods. 

This interface was originally a class extended by SlogoModel, but it had to be made into an interface so
that it wouldn't cause conflicts if a class was already extending another class. For example, an Observable 
class could not be *extended* by InputConsole because it already extends VBox.

###View
####InputLog
We decided to have the input log display any user input, regardless of if it's correct syntax.
We made this design decision so that the user can copy their invalid input from the log and paste
it into the input console to correct it.

####InputConsole

Created InputConsole class extending JavaFX TextArea modifications could be 
done to the input console on the GUI without changing code inside View, thereby leaving
View open but closed.

Added listeners to the input button so that potential future components requiring user input can be added 
without modifying InputConsole.

####OutputScreen

myobject is of type Node to accommodate either images or JavaFX shapes. A try catch is placed in the 
constructor; The try assigns myObject to the default image specified as ObjectImage in Settings.properties,
while the catch assigns it to a Javafx Circle.

##Resources used 

[comment]: <> (- [Accessing private methods and variables for unit testing]&#40;https://stackoverflow.com/questions/34571/how-do-i-test-a-private-function-or-a-class-that-has-private-methods-fields-or&#41;)

