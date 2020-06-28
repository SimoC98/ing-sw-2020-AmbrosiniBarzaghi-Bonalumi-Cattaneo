package it.polimi.ingsw.clientView;

import it.polimi.ingsw.clientView.gui.GUI;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 * Main class to start a game client side. It reads the choice of the user for the UI, ip address and port.
 * If the user inputs {@code cli}, the {@link CLI} starts; in every other case the {@link GUI} starts.
 */
public class Main {

    public static void main(String []args) {
        String ui = null;
        String ip = null;
        int port = -1;

        for(String arg : args) {
            if(arg.toLowerCase().matches("(cli)|(gui)"))
                ui = arg.toLowerCase();
            //found @ https://stackoverflow.com/questions/5284147/validating-ipv4-addresses-with-regexp
            else if(arg.matches("^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$"))
                ip = arg;
            //found @ https://stackoverflow.com/questions/12968093/regex-to-validate-port-number
            else if(arg.matches("(6553[0-5]|655[0-2][0-9]|65[0-4][0-9][0-9]|6[0-4][0-9][0-9][0-9]|[1-5](\\d){4}|[1-9](\\d){0,3})"))
                port = Integer.parseInt(arg);
        }

        //ui init
        ClientView clientView;
        if(ui==null || ui.equals("gui")) {
            System.out.println("You're going to play with the GUI interface");
            //clientView.setUI(new CLI(clientView));
            GUI gui = new GUI();
            clientView = new ClientView(ip,port,gui);
            gui.setClientView(clientView);
            clientView.setUI(gui);
            new Thread(()->{
                gui.start();
            }).start();
        } else {
            System.out.println("You're going to play with the CLI interface");
            CLI cli = new CLI();
            clientView = new ClientView(ip,port,cli);
            cli.setClientView(clientView);
            cli.start();
        }
    }

}
