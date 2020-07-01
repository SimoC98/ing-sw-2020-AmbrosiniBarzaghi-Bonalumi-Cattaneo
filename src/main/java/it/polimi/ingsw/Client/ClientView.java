package it.polimi.ingsw.Client;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.Pair;
import it.polimi.ingsw.Client.gui.GUI;
import it.polimi.ingsw.events.clientToServer.*;
import it.polimi.ingsw.events.serverToClient.ServerEvent;
import it.polimi.ingsw.events.serverToClient.UsernameAlreadyUsed;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

import static java.lang.System.exit;

/**
 * This class is the core to manage a player during a game, observing the {@link ServerEvent} to update the client
 * and calling the methods to send an event on the {@link ClientSocketHandler}.
 */
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

    /**
     * Returns a simplified version of the {@link it.polimi.ingsw.model.Board}
     * @return {@link BoardRepresentation}
     */
    public BoardRepresentation getBoard() {
        return board;
    }

    public String getUsername() {
        return username;
    }

    /**
     * This user's {@link Color}
     * @return One of three colors
     */
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

    /**
     * Called when logging into a game passing the selected username. see {@link LoginEvent}
     * @param username Name of the player
     */
    public void loginQuestion(String username) {
        this.username = username;
        proxy.sendEvent(new LoginEvent(username));
    }

    //IF USERID==0
    public void playersNumberQuestion(int num) {
        proxy.sendEvent(new PlayersNumberQuestionEvent(num));
    }

    //IF USERID==PLAYERSNUMBER

    /**
     * Sends the list of divinities that will be in game and the user that will start. It is sent after {@link it.polimi.ingsw.events.serverToClient.DivinitiesInGameEvent}
     * is received. See {@link DivinitiesInGameSelectionEvent}
     * @param playableDivinities List of up to three divinities' names
     * @param start Username of the starting player
     */
    public void playableDivinitiesSelection(List<String> playableDivinities,String start) {
        proxy.sendEvent(new DivinitiesInGameSelectionEvent(playableDivinities,start));
    }

    /**
     * Calls a method on the socket to send the  select the divinity.
     * Sends {@link DivinitySelectionEvent} after {@link it.polimi.ingsw.events.serverToClient.DivinityInitializationEvent}
     * is received.
     * @param divinity Divinity chosen by the player
     */
    public void divinitySelection(String divinity){
        //System.out.println(divinity);
        proxy.sendEvent(new DivinitySelectionEvent(divinity));
    }

    /**
     * Calls a method on the socket to place the workers on the model's board. It sends a {@link WorkerPlacementSelectionEvent}
     * after a {@link it.polimi.ingsw.events.serverToClient.WorkerPlacementEvent} is received.
     * @param x1 First x woorker's coordinate
     * @param y1 First y woorker's coordinate
     * @param x2 Second x woorker's coordinate
     * @param y2 Second y woorker's coordinate
     */
    public void workerPlacement(int x1, int y1, int x2, int y2) {
        proxy.sendEvent(new WorkerPlacementSelectionEvent(x1, y1, x2, y2));
    }

    /**
     * Sends an even to select the worker that will perform the actions until the end of the turn.
     * {@link WorkerSelectionQuestionEvent} responding to {@link it.polimi.ingsw.events.serverToClient.WorkerSelectionEvent}
     * @param x Selected worker's x coordinate
     * @param y Selected worker's y coordinate
     */
    public void selectWorkerQuestion(int x, int y){
        proxy.sendEvent(new WorkerSelectionQuestionEvent(x, y));
    }

    /**
     * Posts the action that the selected worker will perform on the specified position.
     * @param action One of the {@link Action}s. If the action is END, the coordinate will be (-1,-1)
     * @param x x coordinate to perform the action on
     * @param y y coordinate to perform the action on
     */
    public void actionQuestion(Action action, int x, int y) {
        proxy.sendEvent(new ActionQuestionEvent(action, x, y));
    }



    /*
     *-----------------------------
     *      Server -> Client
     *-----------------------------
     */

    public void manageSocketPort(int gamePort) {
        try {
            ClientSocketHandler gameProxy = new ClientSocketHandler(new Socket(ip, gamePort));
            gameProxy.addObserver(this);
            this.proxy = gameProxy;
            new Thread(proxy).start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Login into Game Server failed");
        }
    }

    /**
     * It handles the login of the user on the selected UI. It is called through {@link it.polimi.ingsw.events.serverToClient.WelcomeEvent}.
     * See {@link CLI#login()} and {@link it.polimi.ingsw.Client.gui.LoginController}
     * @param id Unique id labelling the player
     */
    public void manageLogin(int id){
        setUserID(id);  //TEMP
        ui.login();
    }

    /**
     * Manages the case when a user selected an unavailable username or one with an unaccepted format.
     * Caused by {@link UsernameAlreadyUsed}
     * @param usernames List of already taken nicknames.
     */
    public void manageWrongUsername(List<String> usernames) {
        ui.failedLogin(usernames);
    }


    /**
     * Called when {@link it.polimi.ingsw.events.serverToClient.DivinitiesInGameEvent} is received. The last user will
     * get a list of all the divinities in the game with their description, the number of players and their names.
     * Then it will select the divinities to make available during the game and the starting player.
     * See {@link CLI#selectPlayableDivinities(List, List, int, List)} and {@link it.polimi.ingsw.Client.gui.DivinitySelectionController}
     * @param divinitiesNames names of all the divinities
     * @param divinitiesDescriptions description of the divinities' effects
     * @param playersNumber number of players in game
     * @param players names of the players
     */
    public void managePlayableDivinitiesSelection(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber, List<String> players) {
        ui.selectPlayableDivinities(divinitiesNames, divinitiesDescriptions, playersNumber,players);
    }

    //TODO: why players are put in random order???
    /**
     * {@link it.polimi.ingsw.events.serverToClient.GameSetupEvent} informs the client which color each player has
     * @param playersNames list of players' names
     * @param colors list of colors for each player
     */
    public void playersSetup(List<String> playersNames, List<Color> colors) {

        for(int i=0; i<playersNames.size(); i++) {
            board.addPlayer(playersNames.get(i), colors.get(i));
        }
        int thisPlayer = playersNames.indexOf(this.username);
        this.color = colors.get(thisPlayer);
        //ui.printPlayersInGame();
    }

    /**
     * {@link it.polimi.ingsw.events.serverToClient.GameSetupEvent} informs the client which divinity each player selected
     * @param divinitiesNames List containing the name of each player's divinity
     * @param divinitiesDescriptions List of descriptions relative to each divinity
     */
    public void setDivinitiesDescriptions(List<String> divinitiesNames, List<String> divinitiesDescriptions) {
        Map<String, String> divinities = new HashMap<String, String>();

        for(int i=0; i<divinitiesNames.size(); i++)
            divinities.put(divinitiesNames.get(i), divinitiesDescriptions.get(i));

        board.setDivinities(divinities);
    }

    /**
     * Called when {@link it.polimi.ingsw.events.serverToClient.DivinityInitializationEvent} is received.
     * It bears the divinities a user can choose from. See {@link CLI#selectDivinity(List)} and {@link it.polimi.ingsw.Client.gui.PlayerDivinitySelectionController}
     * @param availableDivinities
     */
    public void manageChooseDivinity(List<String> availableDivinities) {
        ui.selectDivinity(availableDivinities);
    }

    /**
     * Called when {@link it.polimi.ingsw.events.serverToClient.WorkerInitializationEvent} is received.
     * It tells the player that it is their turn to place their workers
     */
    public void manageWorkersPlacementRequest() {
        ui.placeWorkers();
    }

    public void manageWorkersInitialPlacement(String username, int x1, int y1, int x2, int y2) {
        board.getPlayersMap().get(username).addWorker(x1,y1);
        board.getPlayersMap().get(username).addWorker(x2,y2);

        ui.workerPlacementUpdate(username,x1,y1,x2,y2);
    }


    /**
     * After {@link it.polimi.ingsw.events.serverToClient.DivinitiesSetupEvent}.
     * Maps each player to their divinities.
     * {@link GUI#playersDivinities()} and {@link CLI#playersDivinities()}
     * @param divinities {@code Map}
     */
    public void managePlayersDivinities(Map<String, String> divinities) {
        for(String player : board.getPlayersNames()) {
            //System.out.println(board.getPlayersMap().get(player).getUsername());
            board.getPlayersMap().get(player).setDivinity(divinities.get(player));
        }
        ui.playersDivinities();
    }

    /**
     * Manages a {@link it.polimi.ingsw.events.serverToClient.TextMessageEvent}
     * {@link CLI#textMessage(String)} and {@link GUI#textMessage(String)}
     * @param msg
     */
    public void manageTextMessage(String msg) {
        ui.textMessage(msg);
    }

    /**
     * After {@link it.polimi.ingsw.events.serverToClient.StartTurnEvent}.
     * {@link CLI#startTurn()} and {@link GUI#startTurn()}
     */
    public void manageStartTurn() {
        ui.startTurn();
    }

    /**
     * After {@link it.polimi.ingsw.events.serverToClient.WorkerSelectionEvent}.
     * {@link CLI#selectWorker()} and {@link GUI#selectWorker()}
     */
    public void manageNewWorkerSelection() {
        ui.selectWorker();
    }

    /**
     * After {@link it.polimi.ingsw.events.serverToClient.PossibleActionsEvent}.
     * It informs the client which action are available and where they can be done.
     * {@link CLI#performAction(Map)} and {@link GUI#performAction(Map)}
     * @param possibleActions list of {@link Action} a user can perform
     */
    public void managePossibleActions(Map<Action,List<Pair<Integer,Integer>>> possibleActions){
        ui.performAction(possibleActions);
    }

    /**
     * Tells the user where the specified player moved, updating the {@link BoardRepresentation}.
     * {@link CLI#moveUpdate(String, int, int, int, int)} and {@link GUI#moveUpdate(String, int, int, int, int)}
     * @param username player that moved
     * @param fromX initial moving position
     * @param fromY initial moving position
     * @param toX destination position
     * @param toY destination position
     */
    public void manageMove(String username, int fromX, int fromY, int toX, int toY){
        board.moveWorker(username, fromX, fromY, toX, toY);
        ui.moveUpdate(username,fromX,fromY,toX,toY);
    }

    /**
     * Tells the user where the specified player built, updating the {@link BoardRepresentation}.
     * {@link CLI#buildUpdate(String, int, int)} and {@link GUI#buildUpdate(String, int, int)}
     * @param playerName player building
     * @param x building position
     * @param y building position
     * @param action BUILD or BUILDDOME
     */
    public void manageBuild(String playerName, int x, int y, Action action){
        board.buildTile(x, y, action);
        ui.buildUpdate(playerName,x,y);
    }

    /**
     * Tells that the specified user lost. {@link it.polimi.ingsw.events.serverToClient.LoserEvent}
     * {@link CLI#loser(String)} and {@link GUI#loser(String)}
     * @param username losing player
     */
    public void manageLoser(String username){
        board.setLoser(username);
        ui.loser(username);
    }

    /**
     * Tells that the specified user won. {@link it.polimi.ingsw.events.serverToClient.WinnerEvent}
     * {@link CLI#winner(String)} and {@link GUI#winner(String)}
     * @param username winning player
     */
    public void manageWinner(String username){
        proxy.removeObserver(this);
        board.setWinner(username);
        ui.winner(username);
    }

    /**
     * Caused by a user disconnecting. {@link it.polimi.ingsw.events.serverToClient.PlayerDisconnectionEvent}
     * {@link CLI#playerDisconnection(String)} and {@link GUI#playerDisconnection(String)}
     * @param username disconnected user
     */
    public void managePlayerDisconnection(String username) {
        //System.out.println("player disconnection");
        proxy.removeObserver(this);
        ui.playerDisconnection(username);
    }

    /**
     * Informs a player that they correctly joined a lobby. {@link it.polimi.ingsw.events.serverToClient.InLobbyEvent}
     * {@link CLI#inLobby()} and {@link GUI#inLobby()}
     */
    public void manageInLobby() {
        ui.inLobby();
    }

    /**
     * Called at the end of a player's turn. {@link it.polimi.ingsw.events.serverToClient.EndTurnEvent}
     * {@link CLI#endTurn()} and {@link GUI#endTurn()}
     */
    public void manageEndTurnEvent() {
        ui.endTurn();
    }

    /**
     * Caused by an incorrect move {@link it.polimi.ingsw.events.serverToClient.InvalidMoveEvent}.
     * A map with the available actions and positions is sent alongside the incorrect coordinate.
     * {@link UI#invalidMove(Map, int, int, int, int)} and {@link UI#invalidMove(Map, int, int, int, int)}
     * @param possibleActions map of actions and list of positions available
     * @param wrongX wrong position sent
     * @param wrongY wrong position sent
     */
    public void manageInvalidMove(Map<Action,List<Pair<Integer,Integer>>> possibleActions, int wrongX, int wrongY,int startX, int startY) {
        ui.invalidMove(possibleActions,wrongX,wrongY,startX,startY );
    }

    /**
     * Caused by an incorrect build {@link it.polimi.ingsw.events.serverToClient.InvalidBuildEvent}.
     * A map with the available actions and positions is sent alongside the incorrect coordinate.
     * {@link UI#invalidBuild(Map, int, int, int, int)}  and {@link UI#invalidBuild(Map, int, int, int, int)}
     * @param possibleActions map of actions and list of positions available
     * @param wrongX wrong position sent
     * @param wrongY wrong position sent
     */
    public void manageInvalidBuild(Map<Action,List<Pair<Integer,Integer>>> possibleActions,int wrongX,int wrongY,int actualX,int actualY) {
        ui.invalidBuild(possibleActions, wrongX, wrongY,actualX,actualY);
    }

    /**
     * When a player places a worker on another one {@link it.polimi.ingsw.events.serverToClient.InvalidWorkerPlacement}
     * {@link CLI#invalidWorkerPlacement()}  {@link GUI#invalidWorkerPlacement()}
     */
    public void manageInvalidWorkerPlacement() {
        ui.invalidWorkerPlacement();
    }

    /**
     * Consequence of a bad worker selection. It returns the bad coordinated {@link it.polimi.ingsw.events.serverToClient.InvalidWorkerSelection}
     * {@link CLI#invalidWorkerSelection(int, int)} {@link GUI#invalidWorkerSelection(int, int)}
     * @param wrongX wrong coordinate
     * @param wrongY wrong coordinate
     */
    public void manageInvalidWorkerSelection(int wrongX, int wrongY) {
        ui.invalidWorkerSelection(wrongX, wrongY);
    }

    /**
     * Called when the game begins
     * {@link CLI#startGame()} {@link GUI#startGame()}
     */
    public void startGame() {
        ui.startGame();
    }

    /**
     * Called from {@link it.polimi.ingsw.events.serverToClient.LobbyFullEvent} because the user tried to connected
     * once the game already started.
     */
    public void lobbyFull() {
        ui.lobbyFull();
    }

    public void manageServerDisconnection() {
        System.out.println("server disconnection!");
        ui.serverDisconnection();
    }

    /**
     * Called when a {@link ServerEvent} is received. The package is extracted and the method on the incoming
     * event overrides a method on ClientView
     * @param event Incoming event
     */
    @Override
    public void update(ServerEvent event) {
        event.handleEvent(this);
    }


    /**
     * Opens the client connection starting a {@link ClientSocketHandler}.
     * The ip and the port are either passed as parameters in the constructor of ClientView or they are retrieved from
     * an xml file.
     */
    public void startConnection() {
        Socket socket = null;

        try {
            if(ip!=null && port>=0) {
                System.out.println("User socket configuration found");
                socket = new Socket(ip, port);
            }else {
                System.out.println("Default ip and port taken from file");
                socket = connectionConfigParser();
            }
        } catch (Exception e) {
            System.out.println("\n\nSERVER NOT AVAILABLE AT THE SPECIFIED IP AND PORT!\n\n");
            exit(0);
        }

        ClientSocketHandler proxy = new ClientSocketHandler(socket);
        proxy.addObserver(this);

        this.proxy = proxy;

        new Thread(proxy).start();
    }

    /**
     * Method that causes to close the client socket. It is caused by a generic user's connection problem, by a full lobby event
     * or by the presence of winner
     */
    public void disconnect() {
        proxy.close();
    }

    /**
     * Retrieves the connection configuration file to be read to get the ip and the port, that will be used
     * if the user did not specify them in when opening the jar. If the retrieval is successful, a socket is opened.
     * @return {@code Socket} used in the {@link ClientSocketHandler}
     * @throws IOException Thrown if it fails to read the document
     */
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


    /**
     * Resets the client's {@link BoardRepresentation} at the end of the game, building a new one
     */
    public void resetBoard() {
        this.board = new BoardRepresentation();
    }

}
