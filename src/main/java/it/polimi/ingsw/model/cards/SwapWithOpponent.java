package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Phase;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.List;

public class SwapWithOpponent extends MoveOnOpponent {

    public SwapWithOpponent() {
        super();
    }

    public SwapWithOpponent(Divinity decoratedDivinity) {
        super(decoratedDivinity);
    }

    @Override
    public void move(Worker selectedWorker, Tile selectedTile) {
        if (selectedTile.getWorker() != null) {
            selectedWorker.getPositionOnBoard().free();
            selectedWorker.getPositionOnBoard().setWorker(selectedTile.getWorker());
            selectedTile.getWorker().setPositionOnBoard(selectedWorker.getPositionOnBoard());
            selectedTile.free();
            selectedWorker.move(selectedTile);
        }else{
            super.move(selectedWorker, selectedTile);
        }
    }

    @Override
    public boolean legalMove(Worker selectedWorker, Tile selectedTile) {
        return super.legalMove(selectedWorker, selectedTile);
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
    public List<Phase> updatePossibleActions(List<Phase> possibleActions) {
        return super.updatePossibleActions(possibleActions);
    }

    @Override
    public void setupDivinity(List<Phase> possibleActions) {
        super.setupDivinity(possibleActions);
    }

    @Override
    public Divinity getDivinity() {
        return super.getDivinity();
    }
}
