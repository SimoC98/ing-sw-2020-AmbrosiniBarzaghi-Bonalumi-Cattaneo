package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Observer;

import it.polimi.ingsw.events.clientToServer.Ping;
import it.polimi.ingsw.events.serverToClient.ServerEvent;

import java.util.Timer;
import java.util.TimerTask;

public class PingReceiver implements Observer<ServerEvent> {
    private ClientSocketHandler connection;

    public PingReceiver(ClientSocketHandler connection) {
        this.connection = connection;
        connection.addObserver(this);
    }


    public void receivePing() {
        connection.sendEvent(new Ping());
    }


    @Override
    public void update(ServerEvent event) {
        event.handleEvent(this);
    }
}