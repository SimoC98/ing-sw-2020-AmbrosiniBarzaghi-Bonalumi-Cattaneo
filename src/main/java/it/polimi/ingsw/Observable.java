package it.polimi.ingsw;

import it.polimi.ingsw.model.Match;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {
    List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    public void notify(T event) {
        for(int i=0;i<observers.size();i++) {
            observers.get(i).update(event);
        }
    }

}
