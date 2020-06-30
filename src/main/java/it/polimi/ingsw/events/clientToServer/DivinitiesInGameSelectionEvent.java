package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.Server.PingManager;

import java.util.List;

/**
 * List of divinities that will be playable, picked by the last player alongside the player that will begin the game.
 * The list's size is equal to the number of players in game; such list is made based on the complete gods's list passed by the server.
 * This event will begin the next phase of the game (see {@link Controller#startGame(List, String)}
 */
public class DivinitiesInGameSelectionEvent implements ClientEvent {

    private List<String> divinities;
    private String startPlayer;


    public DivinitiesInGameSelectionEvent(List<String> divinities) {
        this.divinities = divinities;
    }

    public DivinitiesInGameSelectionEvent(List<String> divinities, String startPlayer) {
        this.divinities = divinities;
        this.startPlayer = startPlayer;
    }

    @Override
    public void handleEvent(Controller controller) {
        controller.startGame(divinities,startPlayer);
    }

    @Override
    public void handleEvent(PingManager pingSender) {

    }
}
