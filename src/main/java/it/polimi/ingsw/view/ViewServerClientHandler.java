package it.polimi.ingsw.view;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.events.modelToView.MVEvent;
import it.polimi.ingsw.events.viewToController.VCEvent;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ViewServerClientHandler extends Observable implements Runnable {

    private Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    public ViewServerClientHandler(Socket socket) {
        System.out.println("gg");
        this.socket = socket;
        System.out.println("1 million threads are on their way here");
    }

    public void run() {
        try {
//            in = new ObjectInputStream(socket.getInptputStream());
//            out = new ObjectOutputStream(socket.getOuputStream());
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
            }

//            while(true) {
//                VCEvent event = null;
//                try {
//                    event = (VCEvent) in.readObject();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//                if (event != null) {
//                    notify(event);
//                }else {
//                    break;
//                }
//
//                //manage incoming message
//
//                out.flush();
//            }

//            in.close();
//            out.close();
//            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendMessage(VCEvent event){

    }

    public void sendMessage(MVEvent event){

    }

}
