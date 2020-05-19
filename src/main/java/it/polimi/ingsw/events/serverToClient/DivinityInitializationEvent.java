package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

import java.util.List;

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
