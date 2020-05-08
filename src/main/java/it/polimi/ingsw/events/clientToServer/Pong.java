package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.serverView.PingSender;

public class Pong implements ClientEvent{

    @Override
    public void handleEvent(Controller controller) {
        return;
    }

    @Override
    public void handleEvent(PingSender pingSender) {
        pingSender.receivePing();
    }
}
