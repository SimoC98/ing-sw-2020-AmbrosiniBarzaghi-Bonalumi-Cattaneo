package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.serverToClient.*;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Worker;
import jdk.javadoc.internal.tool.Start;

import java.util.List;

public class ServerView extends Observable<ClientEvent> implements Observer<ServerEvent>{

    private class ClientEventReceiver implements Observer<ClientEvent> {

        @Override
        public void update(ClientEvent event) {
            sendToController(event);
        }
    }

    private String playerName;
    private ServerSocketHandler proxy;

    public ServerView(){
        playerName = null;
    }

    public ServerView(String name, ServerSocketHandler proxy){
        this.playerName = name;
        this.proxy = proxy;
        proxy.addObserver(new ClientEventReceiver());
    }

    private void sendToController(ClientEvent event) {
        notify(event);
    }

    public String getUsername() {
        return this.playerName;
    }

    public void startTurn(String username){
        sendEvent(new StartTurnEvent(username));
    }

    public void selectWorker(){
        sendEvent(new WorkerSelectionEvent());
    }

    public void askAction(List<Action> possibleActions){
        sendEvent(new PossibleActionsEvent(possibleActions));
    }

    public void showMessage(String message) {
        sendEvent(new TextMessageEvent(message));
    }

    private void sendEvent(ServerEvent event) {
        proxy.sendEvent(event);
    }

    public void disconnectPlayer() {
        //send disconnectionEvent
        proxy.close();
    }

    @Override
    public void update(ServerEvent event) {
        sendEvent(event);
    }

}
