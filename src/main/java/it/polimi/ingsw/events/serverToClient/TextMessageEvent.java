package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;

public class TextMessageEvent implements ServerEvent{
    private String message;

    public TextMessageEvent(String message) {
        this.message = message;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        //TODO
    }
}
