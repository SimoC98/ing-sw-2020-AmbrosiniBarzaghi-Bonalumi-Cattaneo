package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

import java.util.List;

//first event sent

/**
 * List of all divinities with their description sent to the last player who will choose as many as the number of players
 */
public class DivinitiesInGameEvent implements ServerEvent{

    private List<String> divinities;
    private List<String> description;
    private int playersNumber;
    private List<String> players;

    public DivinitiesInGameEvent(List<String> divinities, List<String> description, int playersNumber, List<String> players) {
        this.divinities = divinities;
        this.description = description;
        this.playersNumber = playersNumber;
        this.players = players;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.managePlayableDivinitiesSelection(divinities, description, playersNumber,players);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
