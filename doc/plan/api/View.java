package slogo;

/**
 * An interface declaring methods implemented by all view classes, such as
 * TurtleView, ConsoleView, WorkspaceView, and LogView.
 */
public interface View {

    /**
     * Creates window and displays it on screen
     */
    public void runGUI();

    /**
     * Removes all text shown inside window.
     */
    public void emptyText();

    /**
     * Adds to text to window. How text is displayed is decided by
     * implementation.
     *
     * @param   text to be displayed
     */
    public void addText(String text);

    /**
     * Displays message directed at user containing information.
     *
     * @param user-directed information
     */
    public void displayInfo(String info);

    /**
     * Displays alert directed at user containing error message.
     * @param error
     */
    public void displayError(String error);

    /**
     * Reduces window to small bar
     */
    public void minimizeWindow();

    /**
     * Returns window to default size
     */
    public void maximizeWindow();
}