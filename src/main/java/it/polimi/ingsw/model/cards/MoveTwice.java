package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

public class MoveTwice extends DivinityDecoratorWithEffects {
    private Tile firstMovedTile;
    private int moveCount;

    public MoveTwice(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        if(moveCount==0) {
            moveCount++;
            firstMovedTile = selectedTile;
        }
        super.move(selectedWorker,selectedTile);
    }

    @Override
    public void build(Worker selectedWorker, Tile selectedTile) {
        super.build(selectedWorker, selectedTile);
    }

    @Override
    public boolean legalBuild(Worker selectedWorker, Tile selectedTile) {
        return super.legalBuild(selectedWorker, selectedTile);
    }

    @Override
    public boolean isWinner() {
        return super.isWinner();
    }

    @Override
    public Divinity getDivinity() {
        return super.getDivinity();
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return super.legalMove(selectedWorker, selectedTile);
    }

    protected int getMoveCount() {
        return moveCount;
    }

    protected Tile getFirstMovedTile() {
        return firstMovedTile;
    }
}
