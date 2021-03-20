package slogo.observers;

public interface AlertObserver {
    String receiveAlert(String message);

    String receiveErrorAlert(String message);
}
