package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.events.serverToClient.Ping;

import java.util.Timer;
import java.util.TimerTask;

public class PingSender implements Observer<ClientEvent> {
    private ServerSocketHandler connection;
    private int pingCounter;
    private Timer timer;

    public PingSender(ServerSocketHandler connection) {
        this.connection = connection;
        connection.addObserver(this);
        pingCounter = 0;
        timer = new Timer();
    }

    public void receivePing() {
        pingCounter=0;
    }

    public void startPing() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(pingCounter==3) {
                    timer.cancel();
                    connection.disconnect();
                }
                else {
                    pingCounter++;
                    //System.out.println("ping");
                    connection.sendEvent(new Ping());
                }

            }
        },0,3000);
    }

    @Override
    public void update(ClientEvent event) {
        event.handleEvent(this);
    }
}
