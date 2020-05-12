package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DivinitiesSetupEvent implements ServerEvent{
    Map<String,String> playersDivinities;

    public DivinitiesSetupEvent(Map<String, String> playersDivinities) {
        this.playersDivinities = playersDivinities;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        //TODO
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
