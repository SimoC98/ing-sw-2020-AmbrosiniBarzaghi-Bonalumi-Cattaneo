package it.polimi.ingsw.events.modelToView;

import it.polimi.ingsw.serverView.ServerView;

import java.io.Serializable;

public interface MVEvent extends Serializable {
    void handleEvent(ServerView serverView);
}
