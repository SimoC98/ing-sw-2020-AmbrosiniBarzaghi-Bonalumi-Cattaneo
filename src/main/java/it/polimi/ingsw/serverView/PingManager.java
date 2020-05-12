package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.clientToServer.ClientEvent;

import java.util.Timer;
import java.util.TimerTask;

public class PingManager implements Observer<ClientEvent> {
    private ServerSocketHandler connection;
    private boolean ping;
    private boolean isNew = true;
    private Timer timer;
    private TimerTask task;

    public PingManager(ServerSocketHandler connection) {
        this.connection = connection;
        connection.addObserver(this);
        ping = true;
        timer = new Timer();
    }

    public void receivePing() {
        if(!isNew) {
            timer.cancel();
        }
        else isNew = false;

        ping = true;
        //System.out.println("PING");

        Timer waitPing = new Timer();
        waitPing.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!ping) {
                    System.out.println("Timer --> disconnection");
                    waitPing.cancel();
                    connection.disconnect();
                }
                else ping = false;
            }
        },10000);
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


    /*public void startPing() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                System.out.println("timer");
                connection.disconnect();
            }
        };
        connection.sendEvent(new Ping());
        timer.schedule(task,5000);
    }*/


    public void stop() {
        timer.cancel();
    }

    @Override
    public void update(ClientEvent event) {
        event.handleEvent(this);
    }
}