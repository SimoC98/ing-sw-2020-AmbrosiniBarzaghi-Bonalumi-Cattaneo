package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;

import java.util.List;

//first event sent
public class DivinitiesInGameEvent implements ServerEvent{

    private List<String> divinities;
    private List<String> description;
    private int playersNumber;

    public DivinitiesInGameEvent(List<String> divinities, List<String> description, int playersNumber) {
        this.divinities = divinities;
        this.description = description;
        this.playersNumber = playersNumber;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.managePlayableDivinitiesSelection(divinities, description, playersNumber);
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
