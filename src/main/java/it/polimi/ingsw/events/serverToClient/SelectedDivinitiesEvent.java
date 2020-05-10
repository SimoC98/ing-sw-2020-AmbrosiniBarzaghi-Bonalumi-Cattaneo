package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;

import java.util.List;

public class SelectedDivinitiesEvent implements ServerEvent{
    private List<String> divinities;

    @Override
    public void handleEvent(ClientView clientView) {

    }
}
