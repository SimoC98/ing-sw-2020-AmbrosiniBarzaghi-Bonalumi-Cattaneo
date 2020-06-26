package it.polimi.ingsw.serverView;

import it.polimi.ingsw.events.serverToClient.SocketPortEvent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MasterServer {

    public static final Object masterLock = new Object();
    private static boolean started;
    private final int port = 4000;
    private int dynamicPort;


    public MasterServer() {
        dynamicPort = port + 100;
        started = false;
    }

    //TODO: JavaDoc
    public void startMasterServer() {
        ServerSocket masterServSock = null;
        ServerSocketHandler masterHandler = null;
        Server homeServer = new Server(port);   //listens on 4000
        System.out.println("Hello there");
        new Thread(this::createNewServer).start();


        try {
            masterServSock = new ServerSocket(port);
            while(true) {
                Socket socket = masterServSock.accept();
                masterHandler = new ServerSocketHandler(socket, homeServer);
                masterHandler.sendEvent(new SocketPortEvent(dynamicPort));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: JavaDoc
    public void createNewServer(){
        System.out.println("ciao");
        while(true) {
            synchronized (masterLock) {
                started = false;
                dynamicPort++;
                System.out.println("Tryin to start a server");
                Server gameServ = new Server(dynamicPort);
                new Thread(gameServ::startServer).start();

                while (!started) {
                    try {
                        masterLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("From now should be all broken");
                    }
                }

                masterLock.notifyAll();
            }
        }
    }

    public static void main(String []args){
        int portNumber;
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if(inetAddress != null)
            System.out.println("Hello, I'm on: " + inetAddress.getHostAddress());
        else
            System.out.println("Hello, I'm on: 127.0.0.1");


//        if(args.length >= 1) {
//            portNumber = Integer.parseInt(args[1]);
//        }
//        else
//        {
//            //should be taken from mxl file
//            portNumber = 4000;
//        }

        MasterServer master = new MasterServer();
        master.startMasterServer();
//        Server server = new Server(portNumber);
//        server.startServer();
    }

    public void serverPrint(int port, String msg){
        System.out.println("Message from server at " + port + " --> " + msg);
    }

    public boolean isStarted() {
        return started;
    }

    public static void setStarted(boolean started) {
        MasterServer.started = started;
    }
}
