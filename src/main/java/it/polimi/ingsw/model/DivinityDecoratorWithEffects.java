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
    public void move(Board board, Worker selectedWorker, Tile selectedTile) {
        divinity.move(board,selectedWorker,selectedTile);
    }

    @Override
    public void build(Board board,Worker selectedWorker, Tile selectedTile) {
        divinity.build(board,selectedWorker,selectedTile);
    }

    @Override
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile) {
        return divinity.legalMove(board,selectedWorker,selectedTile);
    }

    @Override
    public boolean legalBuild(Board board,Worker selectedWorker, Tile selectedTile) {
        return divinity.legalBuild(board,selectedWorker,selectedTile);
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
    public boolean hasSetEffectOnOpponentWorkers() {
        return divinity.hasSetEffectOnOpponentWorkers();
    }

    @Override
    public void setEffectOnOpponentWorkers(Player opponentPlayer) {
        divinity.setEffectOnOpponentWorkers(opponentPlayer);
    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }

    public void setDivinity(Divinity decoratedDivinity) {
        this.divinity = decoratedDivinity;
    }

    public String getDescription() {
        return divinity.getDescription();
    }

    public String getName() {
        return divinity.getName();
    }
}
