package it.polimi.ingsw.clientView;

import it.polimi.ingsw.model.Action;

import java.util.List;

//TODO: I'm thinking of Observer Pattern to connect ClientView and CLI/GUI
//so for example in managePossibleActions ClientView modifies the attribute and notifies the CLI/GUI
public class ClientView {

    private ClientSocketHandler csh;
    BoardRepresentation rep;
    private String username;
    private List<Action> possibleActions;

    public ClientView(){
        csh = new ClientSocketHandler();
        rep = new BoardRepresentation();
        username = null;
        possibleActions = null;
    }

    public void setUsername(String username) {
        this.username = username;
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
    public void askAction(List<Action> possibleActions){
//        proxy.sendEvent(new PossibleActionsEvent(possibleActions));
    }

    public void doAction(Action action, int x, int y){

    }


    /*
     *-----------------------------
     *      Server -> Client
     *-----------------------------
     */
    public void manageMove(String username, int fromX, int fromY, int toX, int toY){
        //TODO
    }

    public void manageBuild(String playerName, int x, int y, Action action){
        //TODO
    }

    public void manageLoser(String userName){
        //TODO
    }

    public void manageWinner(String userName){
        //TODO
    }

    public void managePossibleActions(List<Action> possibleActions){
        //TODO
    }
}
