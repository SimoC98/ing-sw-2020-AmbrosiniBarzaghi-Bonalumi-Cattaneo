package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.events.serverToClient.ServerEvent;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.Socket;

public class ClientSocketHandler extends Observable<ServerEvent> {

    Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    ClientView view;

    public ClientSocketHandler(){
        try {
            connectionConfigParser();
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        //listen
        while (true) {
            ServerEvent event = null;
            try {
                event = (ServerEvent) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            if(event != null)
                notify(event);
        }
    }

    //TODO: handle the sending functionality



    private void connectionConfigParser() throws IOException {
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

        socket = new Socket(hostname, port);
    }

}
