package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;

public class PresentationQuestionEvent implements ClientEvent{
    private int playerNumber=-1;
    private String username;

    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void handleEvent(Controller controller) {
        return;
    }
}
