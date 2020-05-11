package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.events.serverToClient.Ping;

import java.util.Timer;
import java.util.TimerTask;

public class PingSender implements Observer<ClientEvent> {
    private ServerSocketHandler connection;
    private boolean ping;
    private Timer timer;
    private TimerTask task;

    public PingSender(ServerSocketHandler connection) {
        this.connection = connection;
        connection.addObserver(this);
        ping = true;
        //timer = new Timer();
    }

    public void receivePing() {
        /*ping = true;
        System.out.println("pong");*/
        timer.cancel();
        System.out.println("PING");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startPing();
    }

    /*public void startPing() {
        System.out.println("start timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                /*if(pingCounter==3) {
                    System.out.println("ping timeout");
                    timer.cancel();
                    connection.disconnect();
                }
                else {
                    pingCounter++;
                    //System.out.println("ping");
                    connection.sendEvent(new Ping());
                }*/
                //connection.sendEvent(new Ping());

            /*}
        },0,5000);
    }*/


    public void startPing() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer");
                connection.disconnect();
            }
        };
        connection.sendEvent(new Ping());
        timer.schedule(task,5000);
    }


    public void stop() {
        timer.cancel();
    }

     @Override
    public void update(ClientEvent event) {
        event.handleEvent(this);
    }
}
