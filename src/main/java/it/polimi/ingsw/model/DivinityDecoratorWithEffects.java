package it.polimi.ingsw.model;

import it.polimi.ingsw.model.update.ModelUpdate;
import it.polimi.ingsw.model.update.MoveUpdate;

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
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        return divinity.move(board,selectedWorker,selectedTile);
    }

    @Override
    public List<ModelUpdate> build(Board board, Worker selectedWorker, Tile selectedTile) {
        return divinity.build(board,selectedWorker,selectedTile);
    }

    @Override
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile) {
        return divinity.legalMove(board,selectedWorker,selectedTile);
    }

    @Override
    public boolean isWinner(Worker selectedWorker, Tile selectedTile){
        return divinity.isWinner(selectedWorker,selectedTile);
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
