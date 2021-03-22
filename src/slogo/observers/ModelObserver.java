package slogo.observers;

public interface ModelObserver {
    void receiveNewPosition(int id, double x, double y);

    void receiveHeading(int id, double heading);

    void checkPenStatus(int id, boolean b);

    void receiveShow(int id, boolean show);

    void receiveShape(int id, int shape);

    void checkPenColor(int id, int index);

    void checkPenSize(int id, double pixels);

    void receiveTurtle(int id);
}
