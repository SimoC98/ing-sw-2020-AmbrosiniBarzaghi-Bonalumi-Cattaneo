package it.polimi.ingsw.Server;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.Pair;
import it.polimi.ingsw.events.serverToClient.*;
import it.polimi.ingsw.events.clientToServer.ClientEvent;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Color;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class with a role similar to a proxy that speaks to the client sending events
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
    public void askAction(Map<Action,List<Pair<Integer,Integer>>> possibleActions){
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
     * @param playerNumber number of players in game
     * @param playersUsernames list of players' usernames
     */
    public void chooseDivinitiesInGame(List<String> divinities, List<String> descriptions, int playerNumber, List<String> playersUsernames) {
        sendEvent(new DivinitiesInGameEvent(divinities,descriptions,playerNumber,playersUsernames));
    }

    /**
     * {@link DivinityInitializationEvent}
     * @param divinities List of all the game's divinities to pick from
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
     * {@link GameSetupEvent} communicates to the client the choices of the other players.
     * @param players Players' username
     * @param colors Players's colors (automatically set)
     * @param divinities Players' chosen divinity
     * @param descriptions Description of the divinities' powers
     */
    public void sendGameSetupInfo(List<String> players, List<Color> colors, List<String> divinities, List<String> descriptions) {
        sendEvent(new GameSetupEvent(players,colors,divinities,descriptions));
    }

    /**
     * {@link DivinitiesSetupEvent}
     * @param player List of players that chose a divinity
     * @param divinities List of divinities chosen
     */
    public void sendDivinitiesSetup(List<String> player, List<String> divinities) {
        Map<String,String> playersDivinities = new HashMap<>();
        for(int i=0;i<player.size();i++) {
            playersDivinities.put(player.get(i),divinities.get(i));
        }
        sendEvent(new DivinitiesSetupEvent(playersDivinities));
    }


    /**
     * Sends a {@link EndTurnEvent}
     */
    public void endTurn() {
        proxy.sendEvent(new EndTurnEvent());
    }

    /**
     * Sends a {@link InvalidMoveEvent} communicating a bad move and sends again parameters
     * @param possileActions Old list of possible actions
     * @param wrongX Wrong x coordinate chosen by the player
     * @param wrongY Wrong y coordinate chosen by the player
     */
    public void invalidMove(Map<Action,List<Pair<Integer,Integer>>> possileActions, int wrongX, int wrongY,int startX, int startY) {
        sendEvent(new InvalidMoveEvent(possileActions,wrongX,wrongY,startX,startY));
    }

    /**
     * Sends a {@link InvalidBuildEvent} communicating a bad move and sends again parameters
     * @param possileActions Old list of possible actions
     * @param x Wrong x coordinate chosen by the player
     * @param y Wrong y coordinate chosen by the player
     */
    public void invalidBuild(Map<Action,List<Pair<Integer,Integer>>>possileActions, int x, int y, int actualX, int actualY) {
        sendEvent(new InvalidBuildEvent(possileActions,x,y,actualX,actualY));
    }

    /**
     * Sends a {@link InvalidWorkerPlacementEvent} if the player chooses a tile holding another worker or the worker will be
     * blocked once selected
     */
    public void invalidWorkerPlacement() {
        sendEvent(new InvalidWorkerPlacementEvent());
    }

    /**
     * Sends a {@link InvalidWorkerSelectionEvent} when a player chooses a blocked worker
     * @param x x tile of the blocked worker
     * @param y y tile of the blocked worker
     */
    public void invalidWorkerSelection(int x, int y) {
        sendEvent(new InvalidWorkerSelectionEvent(x,y));
    }



    /**
     * Sends the specified event to the client
     * @param event {@link ServerEvent}
     */
    private void sendEvent(ServerEvent event) {
        if(proxy!=null)  proxy.sendEvent(event);
    }

    /**
     * Closes the connection of the client
     */
    public void disconnect() {
        //sendEvent(new PlayerDisconnectionEvent(playerName));
        proxy.close();
    }

    /**
     * Stops sending pings and checking that the client is connected when a player lose. see {@link ServerSocketHandler#stopPing()}
     */
    public void stopPing() {
        if(proxy!=null) proxy.stopPing();
    }

    /**
     * Method invoked when there is a notify on the controller
     * @param event Specified {@link ServerEvent}
     */
    @Override
    public void update(ServerEvent event) {
        sendEvent(event);

    }

}
