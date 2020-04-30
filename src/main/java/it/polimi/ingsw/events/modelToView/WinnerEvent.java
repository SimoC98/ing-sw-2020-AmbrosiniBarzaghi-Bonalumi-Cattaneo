package it.polimi.ingsw.events.modelToView;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.serverView.ServerView;

public class WinnerEvent implements MVEvent {

    private Player player;

    public WinnerEvent(Player player){
        this.player = player;
    }

    @Override
    public void handleEvent(ServerView serverView) {
        serverView.notifyWinner(player.getUsername());
    }
}
