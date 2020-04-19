package it.polimi.ingsw;

import it.polimi.ingsw.model.Match;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {
    List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> user) {
        observers.add(user);
    }

    public void removeObserver(Observer<T> user) {
        observers.remove(user);
    }

    public void notifyUsers(Match model) {
        for(Observer o : observers) {
            //update with simplified copy of model
        }
    }

}
