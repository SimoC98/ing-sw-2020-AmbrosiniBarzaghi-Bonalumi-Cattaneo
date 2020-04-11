package it.polimi.ingsw;

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

    public void notifyUsers(ModelUpdateEvent model) {
        for(Observer o : users) {
            o.update(model);
        }
    }

}
