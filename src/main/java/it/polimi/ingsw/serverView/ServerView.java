package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.serverToClient.ServerEvent;
import it.polimi.ingsw.events.serverToClient.PossibleActionsEvent;
import it.polimi.ingsw.events.clientToServer.VCEvent;
import it.polimi.ingsw.model.Action;

import java.util.List;

public class ServerView extends Observable<VCEvent> implements Observer<ServerEvent>{

    private String playerName;
    private ServerSocketHandler proxy;


    public ServerView(){
        playerName = null;
    }

    public ServerView(String name, ServerSocketHandler proxy){
        this.playerName = name;
        this.proxy = proxy;

    }


    /*
     *-----------------------------
     *   VCEvent-related methods
     *-----------------------------
     */
    public void startTurn(){
    }

    public void selectWorker(){

    }

    //includes Move, Build, BuildDome, EndTurn
    public void askAction(List<Action> possibleActions){
        proxy.sendEvent(new PossibleActionsEvent(possibleActions));
    }

    public void askAction(){

    }


    public void sendTCPMessage(String msg) {

    }

    /*
     *-----------------------------
     *   ServerEvent-related methods
     *-----------------------------
     */
    public void notifyMove(int fromX, int fromY, int toX, int toY){

    }

    public void notifyBuild(int x, int y, Action action){

    }

    public void notifyWinner(String playerName){

    }

    public void notifyLoser(String playerName){

    }

    public void notifyPossibleActions(List<Action> possibleActions){

    }

    public void endGame(){
        System.out.println("We're in the endgame now");
    }


    @Override
    public void update(ServerEvent event) {
        event.handleEvent(this);
    }

}
