package slogo.observers;

public interface AlertObserver {
    void receiveAlert(String message);

    void receiveErrorAlert(String message);
}
