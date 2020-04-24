package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Athena prevents other players from moving up during their next turns, if she has moved up.
 * This effects lasts until her next turn.
 * <p>
 * To achieve such condition every opponent's divinity is decorated with another player that modifies {@code legalMove}
 */
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
     * If the worker goes to a higher level than their current one, other players' divinities are decorated
     * with a class that blocks their ascension
     */
    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        if(selectedTile.getLevel()>selectedWorker.getPositionOnBoard().getLevel()) {
            hasMovedUp = true;
            playerUsername = selectedWorker.getPlayer().getUsername();
            ArrayList<Player> players =  Game.getMatch().getPlayers();
            for(Player p:players) {
                if(!p.getUsername().equals(playerUsername)) {
                    p.setDivinity(new BlockLevelUp(p.getDivinity()));
                }
            }
        }
        super.move(selectedWorker, selectedTile);
    }


    /**
     * If Athena had moved up during her previous turn, all the affected divinities
     * needs to get rid of the "blocking" layer. Such condition is checked through a flag
     */
    @Override
    public void setupDivinity(List<Action> possibleActions) {
        if(hasMovedUp==true) {
            ArrayList<Player> players =  Game.getMatch().getPlayers();
            for(Player p:players) {
                if(!p.equals(Game.getMatch().getCurrentPlayer())) {
                    p.setDivinity(p.getDivinity());
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
