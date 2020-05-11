package it.polimi.ingsw.events.serverToClient;

import it.polimi.ingsw.clientView.ClientView;
import it.polimi.ingsw.clientView.PingReceiver;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.serverView.ServerView;

public class WinnerEvent implements ServerEvent {

    private final Player player;

    public WinnerEvent(Player player){
        this.player = player;
    }

    @Override
    public void handleEvent(ClientView clientView) {
        clientView.manageWinner(player.getUsername());
    }

    @Override
    public void handleEvent(PingReceiver ping) {

    }
}
