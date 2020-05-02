package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;

public class PresentationEvent implements ServerEvent{
    private int id;

    public PresentationEvent(int id) {
        this.id = id;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        //TODO
    }
}
