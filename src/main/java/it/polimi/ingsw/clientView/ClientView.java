package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.clientToServer.LoginEvent;
import it.polimi.ingsw.events.serverToClient.ServerEvent;
import it.polimi.ingsw.model.Action;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.exit;

//TODO: I'm thinking of Observer Pattern to connect ClientView and CLI/GUI
//so for example in managePossibleActions ClientView modifies the attribute and notifies the CLI/GUI
public class ClientView implements Observer<ServerEvent> {

    private ClientSocketHandler proxy;
    private BoardRepresentation board;
    private String username;
    private List<Action> possibleActions;

    public ClientView(){
//        proxy = new ClientSocketHandler();
        board = new BoardRepresentation();
        username = null;
        possibleActions = null;
    }

    public void setUser(String username) {
        this.username = username;
        board.addPlayer(username);
    }

    public BoardRepresentation getBoard() {
        return board;
    }

    public String getUsername() {
        return username;
    }

    public List<Action> getPossibleActions() {
        return possibleActions;
    }

    /*
     *-----------------------------
     *      Client -> Server
     *-----------------------------
     */
    public void startTurn(){
    }

    public void selectWorker(){

    }

    //includes Move, Build, BuildDome, EndTurn
    public void askMove(){
//        proxy.sendEvent(new PossibleActionsEvent(possibleActions));
//        ClientEvent event = new MoveQuestionEvent()
        //TODO: SISTEMARE I ClientEvent (esempio: MoveQuestionEvent ha x, y ma servono sia quelli di partenza che quelli di arrivo)
    }


    /*
     *-----------------------------
     *      Server -> Client
     *-----------------------------
     */
    public void manageMove(String username, int fromX, int fromY, int toX, int toY){
        board.moveWorker(username, fromX, fromY, toX, toY);
    }

    public void manageBuild(String playerName, int x, int y, Action action){
        //NB actually idc who built
        board.buildTile(x, y, action);
    }

    public void manageLoser(String userName){
        board.setLoser(userName);
    }

    public void manageWinner(String userName){
        board.setWinner(userName);
    }

    public void managePossibleActions(List<Action> possibleActions){
        this.possibleActions = possibleActions;
    }

    @Override
    public void update(ServerEvent event) {
        event.handleEvent(this);
    }


    public static void main(String[] args) {
        ClientView c = new ClientView();
        c.start();
    }


    public void start() {
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
        proxy.close();
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
    }



}
