package slogo.observers;

public interface ModelObserver {
    void receiveXCor(double x);

    void receiveYCor(double y);

    void receiveHeading(double heading);

    void isPenDown(boolean b);
}
