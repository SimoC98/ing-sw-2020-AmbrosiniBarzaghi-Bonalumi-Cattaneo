package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.Pair;
import it.polimi.ingsw.events.clientToServer.*;
import it.polimi.ingsw.events.serverToClient.ServerEvent;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.*;


public class ClientView implements Observer<ServerEvent> {

    private ClientSocketHandler proxy;
    private BoardRepresentation board;
    private UI ui;
    private String username;
    private Color color;

    private int userID;

     private final String ip;
     private final int port;

    private Object lock = new Object();

    /*public ClientView(String ip, int port){
        this.ip = ip;
        this.port = port;
        board = new BoardRepresentation();
        username = null;
        userID = -1;      //may become userID but we have no method to tell for now


    }*/

    public ClientView(UI ui,String ip,int port) {
        this.ip = ip;
        this.port = port;
        board = new BoardRepresentation();
        username = null;
        userID = -1;      //may become userID but we have no method to tell for now

        this.ui = ui;


    }

    public ClientView(String ip, int port, UI ui) {
        board = new BoardRepresentation();
        this.ip = ip;
        this.port = port;
        this.ui = ui;
    }


    //USED JUST FOR TEST
    //TODO: REMOVE
//    public ClientView(){
//        pingTimer = new Timer();
//        board = new BoardRepresentation();
//        username = null;
//        userID = -1;      //may become userID but we have no method to tell for now
//    }

    public void startProxy(Socket socket) {
        proxy = new ClientSocketHandler(socket);
        proxy.addObserver(this);
        new Thread(proxy).start();
    }

    public UI getUi() {
        return this.ui;
    }

    public void startUI() {
        ui.start();
    }

    public BoardRepresentation getBoard() {
        return board;
    }

    public String getUsername() {
        return username;
    }

    public Color getColor() {
        return this.color;
    }

    //USED JUST FOR TEST
    public void setUsername(String username) {
        this.username = username;
    }
    public void setUI(UI ui){
        this.ui = ui;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    /*
     *-----------------------------
     *      Client -> Server
     *-----------------------------
     */
    public void loginQuestion(String username) {
        this.username = username;
        proxy.sendEvent(new LoginEvent(username));
    }

    //TODO: This should be temporary
//    public void loginQuestion2(int playersNumber, String username) {
//        this.username = username;
//        proxy.sendEvent(new LoginEvent(playersNumber, username));
//    }

    //IF USERID==0
    public void playersNumberQuestion(int num) {
        proxy.sendEvent(new PlayersNumberQuestionEvent(num));
    }

    //IF USERID==PLAYERSNUMBER
    public void playableDivinitiesSelection(List<String> playableDivinities,String start) {
        proxy.sendEvent(new DivinitiesInGameSelectionEvent(playableDivinities,start));
    }

    public void divinitySelection(String divinity){
        proxy.sendEvent(new DivinitySelectionEvent(divinity));
    }

    public void workerPlacement(int x1, int y1, int x2, int y2) {
        proxy.sendEvent(new WorkerPlacementSelectionEvent(x1, y1, x2, y2));
    }

    public void selectWorkerQuestion(int x, int y){
        proxy.sendEvent(new WorkerSelectionQuestionEvent(x, y));
    }

    public void actionQuestion(Action action, int x, int y) {
        proxy.sendEvent(new ActionQuestionEvent(action, x, y));
    }



    /*
     *-----------------------------
     *      Server -> Client
     *-----------------------------
     */

    public void manageLogin(int id){
        setUserID(id);  //TEMP
        ui.login();


    }

    public void manageWrongUsername(List<String> usernames) {
        ui.failedLogin(usernames);
    }


    public void managePlayableDivinitiesSelection(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber, List<String> players) {
        ui.selectPlayableDivinities(divinitiesNames, divinitiesDescriptions, playersNumber,players);
    }

    public void playersSetup(List<String> playersNames, List<Color> colors) {

        for(int i=0; i<playersNames.size(); i++) {
            board.addPlayer(playersNames.get(i), colors.get(i));
        }
        int thisPlayer = playersNames.indexOf(this.username);
        this.color = colors.get(thisPlayer);
        //ui.printPlayersInGame();
    }

    public void setDivinitiesDescriptions(List<String> divinitiesNames, List<String> divinitiesDescriptions) {
        Map<String, String> divinities = new HashMap<String, String>();

        for(int i=0; i<divinitiesNames.size(); i++)
            divinities.put(divinitiesNames.get(i), divinitiesDescriptions.get(i));

        board.setDivinities(divinities);
    }

    public void manageChooseDivinity(List<String> availableDivinities) {
        ui.selectDivinity(availableDivinities);
    }

    public void manageWorkersPlacementRequest() {
        ui.placeWorkers();
    }

    public void manageWorkersInitialPlacement(String username, int x1, int y1, int x2, int y2) {
        board.getPlayersMap().get(username).addWorker(x1,y1);
        board.getPlayersMap().get(username).addWorker(x2,y2);

        ui.workerPlacementUpdate(username,x1,y1,x2,y2);
    }

    public void managePlayersDivinities(Map<String, String> divinities) {
        for(String player : board.getPlayersNames()) {
            board.getPlayersMap().get(player).setDivinity(divinities.get(player));
        }
        ui.playersDivinities();
    }

    public void manageTextMessage(String msg) {
        ui.textMessage(msg);
    }

    public void manageStartTurn() {
        ui.startTurn();
    }

    public void manageNewWorkerSelection() {
        ui.selectWorker();
    }

    public void managePossibleActions(Map<Action,List<Pair<Integer,Integer>>> possibleActions){
        ui.performAction(possibleActions);
    }

    public void manageMove(String username, int fromX, int fromY, int toX, int toY){
        board.moveWorker(username, fromX, fromY, toX, toY);
        ui.moveUpdate(username,fromX,fromY,toX,toY);
    }

    //NB actually idc who built
    public void manageBuild(String playerName, int x, int y, Action action){
        board.buildTile(x, y, action);
        ui.buildUpdate(playerName,x,y);
    }

    public void manageLoser(String username){
        board.setLoser(username);
        ui.loser(username);
    }

    public void manageWinner(String username){
        board.setWinner(username);
        ui.winner(username);
    }

    public void managePlayerDisconnection(String username) {
        //ui.textMessage("Internal error, game crashed, disconnecting...");
        //exit(0);

        System.out.println("player disconnection");

        ui.playerDisconnection(username);
    }

    public void manageInLobby() {
        ui.inLobby();
    }

    public void manageEndTurnEvent() {
        ui.endTurn();
    }

    public void manageInvalidMove(Map<Action,List<Pair<Integer,Integer>>> possibleActions, int wrongX, int wrongY) {
        ui.invalidMove(possibleActions,wrongX,wrongY );
    }

    public void manageInvalidBuild(Map<Action,List<Pair<Integer,Integer>>> possibleActions,int wrongX,int wrongY) {
        ui.invalidBuild(possibleActions, wrongX, wrongY);
    }

    public void manageInvalidWorkerPlacement() {
        ui.invalidWorkerPlacement();
    }

    public void manageInvalidWorkerSelection(int wrongX, int wrongY) {
        ui.invalidWorkerSelection(wrongX, wrongY);
    }

    public void startGame() {
        ui.startGame();
    }

    @Override
    public void update(ServerEvent event) {
        /*synchronized (lock) {
        }*/
        event.handleEvent(this);

    }









//    //temp main and start
//    public static void main(String[] args) {
//        ClientView c = new ClientView();
//        //c.start();
//    }

    public void startConnection() {
        /*this.ui = new CLI(this);
        ui.start();*/

        //this.board = new BoardRepresentation();
        Socket socket = null;

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

        ClientSocketHandler proxy = new ClientSocketHandler(socket);
        proxy.addObserver(this);

        this.proxy = proxy;

        new Thread(proxy).start();
    }

    public void lobbyFull() {
        ui.lobbyFull();
    }

    public void disconnect() {
        proxy.close();
    }

//    public void login(int id) {
//        if(id==0) {
//            System.out.println("player number");
//            Scanner scanner = new Scanner(System.in);
//            int playernumber = scanner.nextInt();
//            proxy.sendEvent(new LoginEvent(playernumber,this.username));
//        }
//        else {
//            proxy.sendEvent(new LoginEvent(this.username));
//        }
//
//
//        pingTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
////                System.out.println("PING");
//                proxy.sendEvent(new Ping());
//            }
//        },0,5000);
//
//    }


    private Socket connectionConfigParser() throws IOException {
        //File xmlFile = new File("src/main/resources/connection_config.xml");

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
                //doc = builder.parse(xmlFile);
                doc = builder.parse(getClass().getResource("/connection_config.xml").toExternalForm());
            }
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        String hostname = doc.getDocumentElement().getElementsByTagName("hostname").item(0).getTextContent();
        int port = Integer.parseInt(doc.getDocumentElement().getElementsByTagName("port").item(0).getTextContent());
        System.out.println("Config red: " + hostname + " " + port);

        return new Socket(hostname, port);
    }

    public void resetBoard() {
        this.board = new BoardRepresentation();
    }

}
