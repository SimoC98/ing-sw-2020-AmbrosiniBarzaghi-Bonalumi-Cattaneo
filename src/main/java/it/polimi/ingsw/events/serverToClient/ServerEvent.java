package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.serverView.ServerView;

import java.io.Serializable;

public interface ServerEvent extends Serializable {
    void handleEvent(ServerView serverView);
}
