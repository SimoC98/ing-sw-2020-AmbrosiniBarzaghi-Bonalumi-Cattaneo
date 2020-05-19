package it.polimi.ingsw.serverView;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.events.serverToClient.*;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerView extends Observable<ClientEvent> implements Observer<ServerEvent>{

    private class ClientEventReceiver implements Observer<ClientEvent> {

        @Override
        public void update(ClientEvent event) {
            sendToController(event);
        }
    }

    private String playerName;
    private ServerSocketHandler proxy;

    private final Object lock = new Object();


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

    public void playerDisconnection(String playerName) {
        sendEvent(new PlayerDisconnectionEvent(playerName));
    }

    public void chooseDivinitiesInGame(List<String> divinities, List<String> descriptions, int playerNumber) {
        sendEvent(new DivinitiesInGameEvent(divinities,descriptions,playerNumber));
    }

    public void sendDivinityInitialization(List<String> divinities) {
        sendEvent(new DivinityInitializationEvent(divinities));
    }

    public void sendWorkerInitialization() {
        sendEvent(new WorkerInitializationEvent());
    }

    /*public void startGame(List<String> players, List<Color> colors, List<String> divinities, List<String> divinitiesDescriptions) {
        sendEvent(new MatchBeginEvent(players,colors,divinities, divinitiesDescriptions));
    }*/

    public void sendGameSetupInfo(List<String> players, List<Color> colors, List<String> divinities, List<String> descriptions) {
        sendEvent(new GameSetupEvent(players,colors,divinities,descriptions));
    }

    public void sendDivinitiesSetup(List<String> player, List<String> divinities) {
        Map<String,String> playersDivinities = new HashMap<>();
        for(int i=0;i<player.size();i++) {
            playersDivinities.put(player.get(i),divinities.get(i));
        }
        sendEvent(new DivinitiesSetupEvent(playersDivinities));
    }

    private void sendEvent(ServerEvent event) {
        proxy.sendEvent(event);
    }

    public void disconnect() {
        sendEvent(new PlayerDisconnectionEvent(playerName));
        proxy.close();
    }

    @Override
    public void update(ServerEvent event) {
        sendEvent(event);

    }

}
