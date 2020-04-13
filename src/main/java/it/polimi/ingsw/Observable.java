package it.polimi.ingsw;

import it.polimi.ingsw.model.Match;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    List<Observer<ModelUpdateEvent>> users = new ArrayList<>();

    public void addUser(Observer<ModelUpdateEvent> user) {
        users.add(user);
    }

    public void removeUser(Observer<ModelUpdateEvent> user) {
        users.remove(user);
    }

    public void notifyUsers(Match model) {
        for(Observer o : users) {
            //update with simplified copy of model
        }
    }

}
