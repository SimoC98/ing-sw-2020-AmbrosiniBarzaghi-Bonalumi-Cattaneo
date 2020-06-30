package it.polimi.ingsw.events.clientToServer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.Server.PingManager;

/**
 * Event sent by the first logged player, specifying how many players will be joining the match
 */
//TODO: should I remove this?.
public class PlayersNumberQuestionEvent implements ClientEvent {

    private final int number;

    public PlayersNumberQuestionEvent(int number) {
        this.number = number;
    }

    @Override
    public void handleEvent(Controller controller) {
        //TODO
    }

    @Override
    public void handleEvent(PingManager pingSender) {
        return;
    }
}
