package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

public class SendUserID implements ServerEvent {

    private final int id;

    public SendUserID(int id) {
        this.id = id;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.managePlayersNumber(id);
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;
    }
}
