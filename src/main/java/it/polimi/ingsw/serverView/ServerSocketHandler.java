package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.events.serverToClient.ServerEvent;
import it.polimi.ingsw.events.clientToServer.ClientEvent;

import java.io.*;
import java.net.Socket;

public class ServerSocketHandler extends Observable<ClientEvent> implements Runnable {

    private Socket socket;
    private Server server;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ServerSocketHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;

        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        //
        try {
            while(true) {
                ClientEvent event = (ClientEvent) in.readObject();
                notify(event);
            }
        }catch (Exception e) {
            //
        }
    }

    public void sendEvent(ServerEvent event) {
        try {
            out.writeObject(event);
            out.flush();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            socket.close();
            //server.deregisterConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


            /*in = new ObjectInputStream(socket.getInptputStream());
            out = new ObjectOutputStream(socket.getOuputStream());
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            out.println("gg u made it til here, u must be the hero of some ancient prophecy");
            out.flush();
            while(true) {
                String line = in.nextLine();
                if (line.equals("quit")) {
                    break;
                } else {
                    out.println("Received: " + line);
                    out.flush();
                }
            }*/

           /* while(true) {
                VCEvent event = null;
                try {
                    event = (VCEvent) in.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if (event != null) {
                    notify(event);
                }else {
                    break;
                }

                //manage incoming message

                out.flush();
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendMessage(VCEvent event){

    }

    public void sendMessage(ServerEvent event){

    }*/

}
