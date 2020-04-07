package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SetEffectOnOpponent extends DivinityDecoratorWithEffects {
    private boolean hasMovedUp;
    private String playerUsername;

    public SetEffectOnOpponent() {
        super();
    }

    public SetEffectOnOpponent(Divinity divinity) {
        super(divinity);
    }

    /**
     * if the worker tries to go to a higher level tile, other player's divinities are decorated
     * with a class that prevents them to go to a higher level tile
     * @param selectedWorker
     * @param selectedTile
     */
    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getLevel()>selectedWorker.getPositionOnBoard().getLevel()) {
            hasMovedUp = true;
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

    /**
     * if the previous turn a current player's worker has moved up, the last decoration of all other player's divinity
     * has to be removed
     * @param possibleActions
     */
    @Override
    public void setupDivinity(Set<Action> possibleActions) {
        if(hasMovedUp==true) {
            ArrayList<Player> players =  Game.getMatch().getPlayers();
            for(Player p:players) {
                if(!p.equals(Game.getMatch().getCurrentPlayer())) {
                    p.setDivinity(p.getPlayerDivinity().getDivinity());
                }
            }
        }

        hasMovedUp = false;
        super.setupDivinity(possibleActions);
    }

    public boolean isHasMovedUp() {
        return hasMovedUp;
    }
}
