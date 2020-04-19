package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.DivinityDecoratorWithEffects;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

/**
 * Generic abstract class that is to be implemented by divinities adding winning conditions
 */
public abstract class AdditionalWinCondition extends DivinityDecoratorWithEffects {

    public AdditionalWinCondition() { super(); }

    public AdditionalWinCondition(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    protected abstract boolean isWinner(Worker selectedWorker, Tile selectedTile);
}
