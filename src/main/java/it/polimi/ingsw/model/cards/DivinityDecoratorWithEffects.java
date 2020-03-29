package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

public class DivinityDecoratorWithEffects implements Divinity{
    /**
     * instance that has to be decorated
     */
    private Divinity decoratedDivinity;

    public DivinityDecoratorWithEffects() {
        decoratedDivinity = null;
    }

    public DivinityDecoratorWithEffects(Divinity decoratedDivinity) {
        this.decoratedDivinity = decoratedDivinity;
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        decoratedDivinity.move(selectedWorker,selectedTile);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        decoratedDivinity.build(selectedWorker,selectedTile);
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return decoratedDivinity.legalMove(selectedWorker,selectedTile);
    }

    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        return decoratedDivinity.legalBuild(selectedWorker,selectedTile);
    }

    /* @Override
    public boolean isWinner() {
        return decoratedDivinity.isWinner();
    } */


    @Override
    public Divinity getDivinity() {
        return this.decoratedDivinity;
    }

}
