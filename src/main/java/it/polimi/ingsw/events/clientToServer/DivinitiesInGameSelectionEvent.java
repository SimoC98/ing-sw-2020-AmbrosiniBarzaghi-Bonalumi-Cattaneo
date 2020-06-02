package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.serverView.PingManager;

import java.util.List;

/**
 * List of divinities that will be playable, picked by the last player
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
