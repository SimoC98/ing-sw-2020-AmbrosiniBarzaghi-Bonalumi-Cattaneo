package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.events.serverToClient.Ping;

import java.util.Timer;
import java.util.TimerTask;

public class PingManager implements Observer<ClientEvent> {
    private ServerSocketHandler connection;
    private boolean ping;
    private boolean isNew = true;
    private Timer pinger;
    private Timer waitNextPing;
    private TimerTask task;

    public PingManager(ServerSocketHandler connection) {
        this.connection = connection;
        connection.addObserver(this);
        ping = true;
    }



    public void receivePing() {
        ping = true;

    }



    public void startPing() {
        /*pinger = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if(ping==false) {
                    System.out.println("TIMEEER");
                    pinger.cancel();
                    connection.disconnect();
                }
                else {
                    ping = false;
                    connection.sendEvent(new Ping());
                    //System.out.print("ping");
                }
            }
        };

        //2 sec to receive pong
        pinger.schedule(task,0,2000);*/

    }



    public void stop() {
        pinger.cancel();
    }

    @Override
    public void update(ClientEvent event) {
        event.handleEvent(this);
    }
}