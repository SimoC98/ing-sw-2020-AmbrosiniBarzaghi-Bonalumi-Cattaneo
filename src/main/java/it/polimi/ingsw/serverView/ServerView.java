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

/**
 * Class with a role similar to a proxy that tells the speaks to the client sending events
 */
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

    /**
     * Notifies the controller of changes on the client
     * @param event {@link ClientEvent}
     */
    private void sendToController(ClientEvent event) {
        notify(event);
    }

    public String getUsername() {
        return this.playerName;
    }

    /**
     * {@link StartTurnEvent}
     * @param username
     */
    public void startTurn(String username){
        sendEvent(new StartTurnEvent());
    }

    /**
     * {@link WorkerSelectionEvent}
     */
    public void selectWorker(){
        sendEvent(new WorkerSelectionEvent());
    }

    /**
     * {@link PossibleActionsEvent}
     * @param possibleActions list taken from controller and thus from model
     */
    public void askAction(List<Action> possibleActions){
        sendEvent(new PossibleActionsEvent(possibleActions));
    }

    /**
     * {@link TextMessageEvent}
     * @param message text message
     */
    public void showMessage(String message) {
        sendEvent(new TextMessageEvent(message));
    }

    public void playerDisconnection(String playerName) {
        sendEvent(new PlayerDisconnectionEvent(playerName));
    }

    /**
     * {@link DivinitiesInGameEvent}
     * @param divinities list of all divinities from model
     * @param descriptions descriptions of divinities
     * @param playerNumber
     * @param playersUsernames
     */
    public void chooseDivinitiesInGame(List<String> divinities, List<String> descriptions, int playerNumber, List<String> playersUsernames) {
        sendEvent(new DivinitiesInGameEvent(divinities,descriptions,playerNumber,playersUsernames));
    }

    /**
     * {@link DivinityInitializationEvent}
     * @param divinities
     */
    public void sendDivinityInitialization(List<String> divinities) {
        sendEvent(new DivinityInitializationEvent(divinities));
    }

    /**
     * {@link WorkerInitializationEvent}
     */
    public void sendWorkerInitialization() {
        sendEvent(new WorkerInitializationEvent());
    }

    /*public void startGame(List<String> players, List<Color> colors, List<String> divinities, List<String> divinitiesDescriptions) {
        sendEvent(new MatchBeginEvent(players,colors,divinities, divinitiesDescriptions));
    }*/

    /**
     * {@link GameSetupEvent} communicates to the client the choices of the other players
     * @param players
     * @param colors
     * @param divinities
     * @param descriptions
     */
    public void sendGameSetupInfo(List<String> players, List<Color> colors, List<String> divinities, List<String> descriptions) {
        sendEvent(new GameSetupEvent(players,colors,divinities,descriptions));
    }

    /**
     * {@link DivinitiesSetupEvent}
     * @param player
     * @param divinities
     */
    public void sendDivinitiesSetup(List<String> player, List<String> divinities) {
        Map<String,String> playersDivinities = new HashMap<>();
        for(int i=0;i<player.size();i++) {
            playersDivinities.put(player.get(i),divinities.get(i));
        }
        sendEvent(new DivinitiesSetupEvent(playersDivinities));
    }


    public void endTurn() {
        proxy.sendEvent(new EndTurnEvent());
    }

    public void invalidMove(List<Action> possileActions, int wrongX, int wrongY) {
        sendEvent(new InvalidMoveEvent(possileActions,wrongX,wrongY));
    }

    public void invalidBuild(List<Action> possileActions, int x, int y) {
        sendEvent(new InvalidBuildEvent(possileActions,x,y));
    }

    public void invalidWorkerPlacement() {
        sendEvent(new InvalidWorkerPlacement());
    }

    public void invalidWorkerSelection(int x, int y) {
        sendEvent(new InvalidWorkerSelection(x,y));
    }



    /**
     * Sends the specified event to the client
     * @param event
     */
    private void sendEvent(ServerEvent event) {
        proxy.sendEvent(event);
    }

    /**
     * {@link PlayerDisconnectionEvent}
     */
    public void disconnect() {
        //sendEvent(new PlayerDisconnectionEvent(playerName));
        proxy.close();
    }

    public void stopPing() {
        proxy.stopPing();
    }

    /**
     * Method invoked when there is a notify on the controller
     * @param event
     */
    @Override
    public void update(ServerEvent event) {
        sendEvent(event);

    }

}
