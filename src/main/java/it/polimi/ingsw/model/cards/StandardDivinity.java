package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

public class StandardDivinity implements Divinity {
    /**
     * name and description of the divinity card
     */
    private final String name;
    private final String description;

    public StandardDivinity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * call the method on the worker passed as param
     * @param selectedWorker
     * @param selectedTile
     */
    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        selectedWorker.move(selectedTile);
    }

    /**
     * call the method on the worker passed as param
     * @param selectedWorker
     * @param selectedTile
     */
    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        selectedWorker.build(selectedTile);
    }

    /**
     * call the method on the worker passed as param
     * @param selectedWorker
     * @param selectedTile
     * @return
     */
    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return selectedWorker.legalMove(selectedTile);
    }

    /**
     * call the method on the worker passed as param
     * @param selectedWorker
     * @param selectedTile
     * @return
     */
    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        return selectedWorker.legalBuild(selectedTile);
    }

    /*@Override
    public boolean isWinner(Worker ) {
        return
    } */

    /**
     *
     * @return this instance of StandardDivinity
     */
    @Override
    public Divinity getDivinity() {
        return this;
    }
}
