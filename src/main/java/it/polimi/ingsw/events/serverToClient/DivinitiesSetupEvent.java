package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Informs a client about which divinity each player chose
 */
public class DivinitiesSetupEvent implements ServerEvent{
    Map<String,String> playersDivinities;

    public DivinitiesSetupEvent(Map<String, String> playersDivinities) {
        this.playersDivinities = playersDivinities;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.managePlayersDivinities(playersDivinities);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
