package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.PingReceiver;


//TODO: JavaDoc
public class SocketPortEvent implements ServerEvent{

    private final int port;

    public SocketPortEvent(int port) {
        this.port = port;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageSocketPort(port);
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;
    }
}