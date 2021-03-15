package slogo.observers;

public interface ModelObserver {
    void receiveNewPosition(double x, double y);

    void receiveHeading(double heading);

    void checkPen(boolean b);
}
