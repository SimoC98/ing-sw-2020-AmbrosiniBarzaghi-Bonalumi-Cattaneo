package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.serverView.PingManager;

import java.util.List;

public class DivinitiesInGameSelectionEvent implements ClientEvent {

    private List<String> divinities;

    public DivinitiesInGameSelectionEvent(List<String> divinities) {
        this.divinities = divinities;
    }

    @Override
    public void handleEvent(Controller controller) {
        controller.startGame(divinities);
    }

    @Override
    public void handleEvent(PingManager pingSender) {

    }
}
