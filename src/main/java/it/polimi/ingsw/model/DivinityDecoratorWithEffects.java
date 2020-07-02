package it.polimi.ingsw.model;

import it.polimi.ingsw.model.update.ModelUpdate;

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


    /**
     * See {@link Divinity}
     * @param board current {@link Board}
     * @param selectedWorker Selected worker from the {@link Match}
     * @param selectedTile {@link Tile} to move onto
     * @return list of tiles to be modified on the View
     */
    @Override
    public List<ModelUpdate> move(Board board, Worker selectedWorker, Tile selectedTile) {
        return divinity.move(board,selectedWorker,selectedTile);
    }

    /**
     * See {@link Divinity}
     * @param board current {@link Board}
     * @param selectedWorker Selected worker from the {@link Match}
     * @param selectedTile {@link Tile} to build on
     * @return list of tiles to be modified on the View
     */
    @Override
    public List<ModelUpdate> build(Board board, Worker selectedWorker, Tile selectedTile) {
        return divinity.build(board,selectedWorker,selectedTile);
    }

    /**
     * See {@link Divinity}
     * @param board current {@link Board}
     * @param selectedWorker {@link Worker} whose move is verified
     * @param selectedTile {@link Tile} to check
     * @return true if the move is legal
     */
    @Override
    public boolean legalMove(Board board,Worker selectedWorker, Tile selectedTile) {
        return divinity.legalMove(board,selectedWorker,selectedTile);
    }

    /**
     * See {@link Divinity}
     * @param selectedWorker current worker performing an action
     * @param selectedTile {@link Tile} the worker is going to perform the action on
     * @return the player is performing a winning condition
     */
    @Override
    public boolean isWinner(Worker selectedWorker, Tile selectedTile){
        return divinity.isWinner(selectedWorker,selectedTile);
    }

    /**
     * See {@link Divinity}
     * @param board current {@link Board}
     * @param selectedWorker {@link Worker} whose build is verified
     * @param selectedTile {@link Tile} to check
     * @return true if the build is legal
     */
    @Override
    public boolean legalBuild(Board board,Worker selectedWorker, Tile selectedTile) {
        return divinity.legalBuild(board,selectedWorker,selectedTile);
    }

    /**
     * See {@link Divinity}
     * @param possibleActions List of actions to modify
     */
    @Override
    public void updatePossibleActions(List<Action> possibleActions) {
        divinity.updatePossibleActions(possibleActions);
    }

    /**
     * See {@link Divinity}
     * @param possibleActions List of actions to be modified
     */
    @Override
    public void setupDivinity(List<Action> possibleActions) {
        divinity.setupDivinity(possibleActions);
    }

    /**
     * See {@link Divinity}
     * @return true if the divinity has to apply an effect to the opponents
     */
    @Override
    public boolean hasSetEffectOnOpponentWorkers() {
        return divinity.hasSetEffectOnOpponentWorkers();
    }

    /**
     * See {@link Divinity}
     * @param opponentPlayer player affected by the divinity
     */
    @Override
    public void setEffectOnOpponentWorkers(Player opponentPlayer) {
        divinity.setEffectOnOpponentWorkers(opponentPlayer);
    }

    /**
     * See {@link Divinity}
     * @return instance of the divinity
     */
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
