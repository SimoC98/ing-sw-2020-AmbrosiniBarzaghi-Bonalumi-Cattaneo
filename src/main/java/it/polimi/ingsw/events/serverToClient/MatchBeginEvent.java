package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;
import it.polimi.ingsw.model.Color;

import java.util.List;

public class MatchBeginEvent implements ServerEvent {
    private List<String> players;
    private List<String> divinities;
    private List<String> divinitiesDescriptions;
    private List<Color> colors;

    public MatchBeginEvent(List<String> players, List<Color> colors, List<String> divinities, List<String> divinitiesDescriptions) {
        this.players = players;
        this.divinities = divinities;
        this.colors = colors;
        this.divinitiesDescriptions = divinitiesDescriptions;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.setDivinitiesDescriptions(divinities, divinitiesDescriptions);
        clientView.playersSetup(players, colors);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
