package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.Pair;
import it.polimi.ingsw.events.clientToServer.*;
import it.polimi.ingsw.events.serverToClient.ServerEvent;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

import static java.lang.System.exit;

public class ClientView implements Observer<ServerEvent> {

    private ClientSocketHandler proxy;
    private BoardRepresentation board;
    private UI ui;
    private String username;
    private int userID;
    private Timer pingTimer;

    public ClientView(){
//        proxy = new ClientSocketHandler();
        board = new BoardRepresentation();
        username = null;
        userID = -1;      //may become userID but we have no method to tell for now
        pingTimer = new Timer();
    }

    public ClientView(UI ui){
//        proxy = new ClientSocketHandler();
        pingTimer = new Timer();
        board = new BoardRepresentation();
        this.ui = ui;
        username = null;
        userID = -1;      //may become userID but we have no method to tell for now
    }



    public BoardRepresentation getBoard() {
        return board;
    }

    public String getUsername() {
        return username;
    }

    //USED JUST FOR TEST
    public void setUsername(String username) {
        this.username = username;
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

    //IF USERID==0
    public void playersNumberQuestion(int num) {
        proxy.sendEvent(new PlayersNumberQuestionEvent(num));
    }

    //IF USERID==PLAYERSNUMBER
    public void playableDivinitiesSelection(List<String> playableDivinities) {
        proxy.sendEvent(new DivinitiesInGameSelectionEvent(playableDivinities));
    }

    public void divinitySelectionAndWorkerPlacement(String divinity, int x1, int y1, int x2, int y2) {
        proxy.sendEvent(new DivinitySelectionAndWorkersPlacementEvent(divinity, x1, y1, x2, y2));
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
    public void manageWrongUsername(List<String> usernames) {
        //ui.wrongUsername
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void managePlayersNumber() {
        ui.selectPlayersNumber();
    }

    public void managePlayableDivinitiesSelection(List<String> divinitiesNames, List<String> divinitiesDescriptions, int playersNumber) {
        ui.selectPlayableDivinities(divinitiesNames, divinitiesDescriptions, playersNumber);
    }

    public void playersSetup(List<String> playersNames, List<Color> colors) {

        for(int i=0; i<playersNames.size(); i++) {
            board.addPlayer(playersNames.get(i), colors.get(i));
        }
    }

    public void setDivinitiesDescriptions(List<String> divinitiesNames, List<String> divinitiesDescriptions) {
        Map<String, String> divinities = new HashMap<String, String>();

        for(int i=0; i<divinitiesNames.size(); i++)
            divinities.put(divinitiesNames.get(i), divinitiesDescriptions.get(i));

        board.setDivinities(divinities);
    }

    public void manageChooseDivinity(List<String> availableDivinities) {
        ui.selectDivinity(availableDivinities);
        ui.placeWorkers();

        PlayerRepresentation player = board.getPlayersMap().get(username);
        List<Pair<Integer, Integer>> workers = player.getWorkers();

        divinitySelectionAndWorkerPlacement(player.getDivinity(), workers.get(0).getFirst(), workers.get(0).getSecond(), workers.get(1).getFirst(), workers.get(1).getSecond());
    }

    public void manageTextMessage(String msg) {
        //ui.textMessage(msg);
    }

    public void manageStartTurn() {
        //ui.startTurn();
    }

    public void managePossibleActions(List<Action> possibleActions){
        //ui.performAction();
    }

    public void manageMove(String username, int fromX, int fromY, int toX, int toY){
        board.moveWorker(username, fromX, fromY, toX, toY);
    }

    //NB actually idc who built
    public void manageBuild(String playerName, int x, int y, Action action){
        board.buildTile(x, y, action);
    }

    public void manageLoser(String username){
        board.setLoser(username);
    }

    public void manageWinner(String username){
        board.setWinner(username);
    }

    @Override
    public void update(ServerEvent event) {
        event.handleEvent(this);
    }









    //temp main and start
    public static void main(String[] args) {
        ClientView c = new ClientView();
        c.start();
    }

    public void start() {
        this.ui = new CLI(this);
        System.out.println("start");
        System.out.println("Username?");
        Scanner scanner = new Scanner(System.in);
        this.username = scanner.nextLine();

        Socket socket = null;

        try {
            socket = new Socket("127.0.0.1",4000);
            System.out.println("connection established");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClientSocketHandler proxy = new ClientSocketHandler(socket);
        proxy.addObserver(this);

        this.proxy = proxy;

        new Thread(proxy).run();


    }

    public void lobbyFull() {
        System.out.println("Lobby Full");
        disconnect();
    }

    public void disconnect() {
        //proxy.close();
        System.out.println("disconnecting");
        exit(0);
    }

    public void login(int id) {
        if(id==0) {
            System.out.println("player number");
            Scanner scanner = new Scanner(System.in);
            int playernumber = scanner.nextInt();
            proxy.sendEvent(new LoginEvent(playernumber,this.username));
        }
        else {
            proxy.sendEvent(new LoginEvent(this.username));
        }


        pingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("PING");
                proxy.sendEvent(new Ping());
            }
        },0,5000);

    }


}
