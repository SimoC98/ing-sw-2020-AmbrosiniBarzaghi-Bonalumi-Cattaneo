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

public class TemporaryMain {

    public static void main(String []args) {
        String ui = null;
        String ip = null;
        int port = -1;
        Socket socket = null;

        for(String arg : args) {
            if(arg.toLowerCase().matches("(cli)|(gui)"))
                ui = arg.toLowerCase();
            //found on https://stackoverflow.com/questions/5284147/validating-ipv4-addresses-with-regexp
            else if(arg.matches("^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$"))
                ip = arg;
            //found on https://stackoverflow.com/questions/12968093/regex-to-validate-port-number
            else if(arg.matches("(6553[0-5]|655[0-2][0-9]|65[0-4][0-9][0-9]|6[0-4][0-9][0-9][0-9]|[1-5](\\d){4}|[1-9](\\d){0,3})"))
                port = Integer.parseInt(arg);
        }

        //ui init
        ClientView clientView = new ClientView();
        if(ui!=null && ui.equals("gui")) {
            System.out.println("You're going to play with the GUI interface");
            //clientView.setUI(new GUI(clientView));
        } else {
            System.out.println("You're going to play with the CLI interface");
            clientView.setUI(new CLI(clientView));
        }

        //net init
        try {
            if(ip!=null && port>=0) {
                System.out.println("User socket configuration found");
                socket = new Socket(ip, port);
            }else {
                System.out.println("Default ip and port taken from file");
                socket = connectionConfigParser();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //start
        clientView.startUI();
        clientView.startProxy(socket);
    }

    private static Socket connectionConfigParser() throws IOException {
        File xmlFile = new File("resources/connection_config.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            if (builder != null) {
                doc = builder.parse(xmlFile);
            }
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        String hostname = doc.getDocumentElement().getElementsByTagName("hostname").item(0).getTextContent();
        int port = Integer.parseInt(doc.getDocumentElement().getElementsByTagName("port").item(0).getTextContent());
        System.out.println("Config red: " + hostname + " " + port);

        return new Socket(hostname, port);
    }
}
