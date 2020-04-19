package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Divinity;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.Set;

/**
 * General class to decorate a divinity with special effects in accordance to
 * Decorator Pattern. All methods are overridden and a setup for the decoration
 * shell is declared.
 */
public class DivinityDecoratorWithEffects implements Divinity {

    private Divinity divinity;

    /**
     * Constructor used in tests
     */
    public DivinityDecoratorWithEffects() {
        divinity = null;
    }

    /**
     * Main constructor to decorate a divinity.
     * @param divinity Interface passed to realize Decorator Pattern
     */
    public DivinityDecoratorWithEffects(Divinity divinity) {
        this.divinity = divinity;
    }


    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        divinity.move(selectedWorker,selectedTile);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        divinity.build(selectedWorker,selectedTile);
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return divinity.legalMove(selectedWorker,selectedTile);
    }

    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        return divinity.legalBuild(selectedWorker,selectedTile);
    }

    @Override
    public Set<Action> updatePossibleActions(Set<Action> possibleActions) {
        return divinity.updatePossibleActions(possibleActions);
    }

    @Override
    public void setupDivinity(Set<Action> possibleActions) {
        divinity.setupDivinity(possibleActions);
    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }

    public void setDivinity(Divinity decoratedDivinity) {
        this.divinity = decoratedDivinity;
    }
}
