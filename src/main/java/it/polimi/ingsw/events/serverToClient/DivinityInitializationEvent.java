package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

import java.util.List;

/**
 * List of divinities chosen by the last player that each user can choose from. The list has a starting size equals to
 * the number of players in game and is reduced each time a user chooses a divinity.
 */
public class DivinityInitializationEvent implements ServerEvent{

    private final List<String> playableDivinities;

    public DivinityInitializationEvent(List<String> playableDivinities) {
        this.playableDivinities = playableDivinities;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageChooseDivinity(playableDivinities);
    }

    @Override
    public void handleEvent(PingReceiver ping) {
        return;
    }
}
