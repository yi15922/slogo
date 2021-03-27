package slogo;

import slogo.observers.InputObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * The Observable interface should be implemented by any class
 * that needs to notify other classes of changes in its data.
 *
 * The methods that subclass of Observable calls is dependent on what
 * type of observer the subclass expects.
 *
 * @param <T>   type of observer
 *
 * @author Liam Idrovo
 */
public interface Observable<T> {


    /**
     * Checks if specified observer is currently an observer
     * of the Observable subclass
     *
     * @param observer      specified observer
     * @return true if specified observer is an observer of subclass. false otherwise.
     */
    boolean isObserver(T observer) ;

    /**
     * Add specified observer as observer of Observable subclass.
     *
     * @param observer  object of type T
     */
    void addObserver(T observer);

    /**
     * Remove specified observer as observer of Observable subclass.
     *
     * @param observer  object of type T
     */
    void removeObserver(T observer);

}
