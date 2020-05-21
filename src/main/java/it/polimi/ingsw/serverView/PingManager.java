package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.events.serverToClient.Pong;

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


    /**
     * METODO FUNZIONANTE
     */
    /*public void receivePing() {
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
        },20000);
    }*/


    public void receivePing() {
        ping = true;

    }



    public void startPing() {
        pinger = new Timer();
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
                    connection.sendEvent(new Pong());
                    //System.out.print("ping");
                }
            }
        };

        //8 sec to receive pong
        pinger.schedule(task,0,5000);

    }


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
        //timer.cancel();
    }

    @Override
    public void update(ClientEvent event) {
        event.handleEvent(this);
    }
}