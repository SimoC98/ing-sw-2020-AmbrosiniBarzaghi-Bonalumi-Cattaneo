package it.polimi.ingsw.view;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.modelToView.MVEvent;
import it.polimi.ingsw.events.viewToController.VCEvent;

public class ServerView extends Observable<VCEvent> implements Observer<VCEvent>{

    private class MVEventReceiver implements Observer<MVEvent> {

        @Override
        public void update(MVEvent event) {
            //dk what this method has to do
        }
    }

    String playerName;

    public ServerView(){
        playerName = null;
    }

    public ServerView(String name){
        playerName = name;
    }

    public void startTurn(){

    }

    public void selectWorker(){

    }

    public void askMove(int x, int y){

    }

    public void askBuild(int x, int y){

    }

    public void askBuildDome(int x, int y){

    }

    public void askAction(){

    }

    public void endGame(){
        System.out.println("We're in the endgame now");
    }

    public void sendTCPMessage(String msg) {

    }


    @Override
    public void update(VCEvent event) {

    }

}
