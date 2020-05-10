package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.model.Color;

import java.util.List;

public class MatchBeginEvent implements ServerEvent {
    private List<String> players;
    private List<String> divinities;
    private List<Color> colors;


    @Override
    public void handleEvent(ClientView clientView) {

    }
}
