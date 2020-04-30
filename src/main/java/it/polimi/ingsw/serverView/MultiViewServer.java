package it.polimi.ingsw.serverView;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiViewServer{
    private int port;

    public MultiViewServer(int port){
        this.port = port;
    }

    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int i = 0;
        while(true){
            System.out.println("while(true), just me and you");
            try{
                Socket clientSock = serverSocket.accept();
                System.out.println("Here comes the client #" + i + "...");
                i++;
                executor.submit(new ViewServerClientHandler(clientSock));
                System.out.println("#" + i + "managed!");
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        executor.shutdown();
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


        if(args.length >= 1) {
            portNumber = Integer.parseInt(args[1]);
        }
        else
        {
            //should be taken from mxl file
            portNumber = 4000;
        }

        MultiViewServer server = new MultiViewServer(portNumber);
        server.startServer();
    }
}
