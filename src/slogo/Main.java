package slogo;


import slogo.view.View;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {
    /**
     * A method to test (and a joke :).
     */
    public double getVersion () {
        return 0.001;
    }

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        Turtle turtle = new Turtle();
        View view = new View(turtle);
        view.runApplication(args);
    }
}
