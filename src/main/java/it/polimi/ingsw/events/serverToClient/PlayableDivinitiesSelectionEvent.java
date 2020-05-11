package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

import java.util.List;

public class PlayableDivinitiesSelectionEvent implements ServerEvent{

    private final List<String> playableDivinities;

    public PlayableDivinitiesSelectionEvent(List<String> playableDivinities) {
        this.playableDivinities = playableDivinities;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.chooseDivinity(playableDivinities);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
