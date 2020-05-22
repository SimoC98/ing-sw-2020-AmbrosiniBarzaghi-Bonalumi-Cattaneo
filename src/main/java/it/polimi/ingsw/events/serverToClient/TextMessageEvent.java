package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

/**
 * Text message to tell something to a client
 */
public class TextMessageEvent implements ServerEvent{
    private String message;

    public TextMessageEvent(String message) {
        this.message = message;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageTextMessage(message);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
