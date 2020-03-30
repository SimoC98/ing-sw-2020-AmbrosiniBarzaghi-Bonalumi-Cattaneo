package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

public class SetEffectOnOpponent extends DivinityDecoratorWithEffects {
    private boolean hasMovedUp;
    private String playerUsername;

    public SetEffectOnOpponent() {
        super();
    }

    public SetEffectOnOpponent(Divinity divinity) {
        super(divinity);
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getLevel()>selectedWorker.getPositionOnBoard().getLevel()) {
            playerUsername = selectedWorker.getPlayer().getUsername();
            ArrayList<Player> players =  Game.getMatch().getPlayers();
            for(Player p:players) {
                if(!p.getUsername().equals(playerUsername)) {
                    p.setDivinity(new BlockLevelUp(p.getPlayerDivinity().getDivinity()));
                }
            }
        }
        super.move(selectedWorker, selectedTile);
    }

    @Override
    public void setupDivinity(List<Phase> possibleActions) {
        if(hasMovedUp==true) {
            ArrayList<Player> players =  Game.getMatch().getPlayers();
            for(Player p:players) {
                if(!p.getUsername().equals(Game.getMatch().getCurrentPlayerUsername())) {
                    p.setDivinity(p.getPlayerDivinity().getDivinity());
                }
            }
        }

        hasMovedUp = false;
        super.setupDivinity(possibleActions);
    }
}
