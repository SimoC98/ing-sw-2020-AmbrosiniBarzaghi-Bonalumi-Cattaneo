package it.polimi.ingsw.model;

import java.util.List;

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
    public void updatePossibleActions(List<Action> possibleActions) {
        divinity.updatePossibleActions(possibleActions);
    }

    @Override
    public void setupDivinity(List<Action> possibleActions) {
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
