package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.modelToView.MVEvent;
import it.polimi.ingsw.events.viewToController.VCEvent;
import it.polimi.ingsw.model.Action;

import java.util.List;

public class ServerView extends Observable<VCEvent> implements Observer<MVEvent>{

    String playerName;

    public ServerView(){
        playerName = null;
    }

    public ServerView(String name){
        playerName = name;
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
    public void askAction(Action action, int x, int y){

    }

    public void askAction(){

    }


    public void sendTCPMessage(String msg) {

    }

    /*
     *-----------------------------
     *   MVEvent-related methods
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
    public void update(MVEvent event) {

    }

}
