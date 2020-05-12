package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;
import it.polimi.ingsw.model.Color;

import java.util.List;

public class GameSetupEvent implements ServerEvent{
    private List<String> players;
    private List<Color> colors;
    private List<String> divinities;
    private List<String> descriptions;

    public GameSetupEvent(List<String> players, List<Color> colors, List<String> divinities, List<String> descriptions) {
        this.players = players;
        this.colors = colors;
        this.divinities = divinities;
        this.descriptions = descriptions;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.playersSetup(players, colors);
        clientView.setDivinitiesDescriptions(divinities,descriptions);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
