package it.polimi.ingsw.clientView;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.serverToClient.ServerEvent;
import it.polimi.ingsw.model.Action;

import java.util.List;

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
}
