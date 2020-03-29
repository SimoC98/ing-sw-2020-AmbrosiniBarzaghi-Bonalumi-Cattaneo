package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

public abstract class AdditionalWinCondition extends DivinityDecoratorWithEffects {

    public AdditionalWinCondition(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    protected abstract boolean isWinner(Worker selectedWorker, Tile selectedTile);
}
