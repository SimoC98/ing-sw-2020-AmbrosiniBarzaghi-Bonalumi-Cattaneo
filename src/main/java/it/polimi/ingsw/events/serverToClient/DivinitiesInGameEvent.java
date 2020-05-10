package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;

import java.util.List;

public class DivinitiesInGameEvent implements ServerEvent{

    private List<String> divinities;
    private List<String> description;
    private int playersNumber;

    @Override
    public void handleEvent(ClientView clientView) {

    }
}
