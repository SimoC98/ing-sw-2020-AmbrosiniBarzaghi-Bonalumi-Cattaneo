package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Phase;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;

public class DivinityDecoratorWithEffects implements Divinity{
    /**
     * instance that has to be decorated
     */
    private Divinity divinity;

    public DivinityDecoratorWithEffects() {
        divinity = null;
    }

    public DivinityDecoratorWithEffects(Divinity decoratedDivinity) {
        this.divinity = decoratedDivinity;
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
    public List<Phase> updatePossibleActions(List<Phase> possibleActions) {
        return divinity.updatePossibleActions(possibleActions);
    }

    @Override
    public void setupDivinity(List<Phase> possibleActions) {
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